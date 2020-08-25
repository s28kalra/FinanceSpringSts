package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dto.EmiCardDto;
import com.lti.exception.CustomerServiceException;
import com.lti.exception.ViewCardTransactionsException;
import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;
import com.lti.repository.AdminRepositoryInterface;
import com.lti.repository.CustomerRepositoryInterface;
import com.lti.sms.SmsService;

@Service
public class CustomerService implements CustomerServiceInterface {

	@Autowired
	CustomerRepositoryInterface customerRepo;

	@Autowired
	AdminRepositoryInterface adminRepo;

	@Autowired
	SmsService smsService;

	public int addNewCustomer(CustomerInfo customerInfo) {
		if (customerRepo.isNewCustomerUnique(customerInfo.getCustomerEmail(), customerInfo.getCustomerMobile(),
				customerInfo.getCustomerAadharCard())) {
			customerInfo.setRegistrationDate(LocalDate.now()); // always now
			customerInfo.setIsValidCustomer(0); // initially 0
			int id=0;
//			if(smsService.sendRegisterSms(customerInfo.getCustomerFirstName(), customerInfo.getCustomerMobile()))
				id = customerRepo.addNewCustomer(customerInfo);
			return id;
		}
		return 0;
	}

	public CustomerInfo loginCustomer(String email, String password) {

		CustomerInfo customerInfo = customerRepo.isCustomerAvailable(email);
		if (customerInfo == null) {
			throw new CustomerServiceException("Customer not registered");
		} else if (!customerInfo.getCustomerPassword().equals(password)) {
			throw new CustomerServiceException("Incorrect email or password");
		}
		return customerInfo;

	}

	public CustomerInfo updateCustomer(CustomerInfo customerInfo) {
		return customerRepo.updateCustomer(customerInfo);
	}

	public CustomerInfo findCustomerById(int customerId) {
		return customerRepo.findCustomerById(customerId);
	}

	@Transactional
	public boolean activateEmiCard(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null && customerInfo.getIsValidCustomer() == 1) {
			customerInfo.getEmiCard().setCardStatus(true);
			customerInfo.getEmiCard().setJoiningFeesDate(LocalDate.now());
			if (customerInfo.getEmiCard().getCardNumberStart().equals("30034004"))
				customerInfo.getEmiCard().setJoiningFees(500);
			else
				customerInfo.getEmiCard().setJoiningFees(1000);

			customerRepo.updateCustomer(customerInfo);

			return true;
		}

		return false;
	}

	public int buyAProductOnEmi(Checkout checkout) {

		String s1 = checkout.getCardNumber().substring(0, 8);

		// if not matching with the start key of gold or titanium then return
		// false;
		if (!(s1.equals("10012002") || s1.equals("30034004"))) {
			System.out.println("Card starting number doesn't match with gold or titanium");
			return 0;
		}

		String s2 = checkout.getCardNumber().substring(8, 16);
		int cardNumber = 0;
		try {
			cardNumber = Integer.valueOf(s2);
		} catch (NumberFormatException e) {
			System.out.println("Number format Exception " + s2);
			return 0;
		}
		EmiCard emiCard = new EmiCard();
		emiCard = adminRepo.findEmiCardByCardNumber(cardNumber);

		if (emiCard == null || emiCard.getCardStatus() == false) {
			System.out.println("either card is not available or card is not active");
			return 0;
		}

		// if cvv or expiry month or expiry year doesn't match then return false
		if (emiCard.getCardCvv() != checkout.getCvv()
				|| emiCard.getCardExpiry().getMonthValue() != checkout.getExpiryMonth()
				|| emiCard.getCardExpiry().getYear() != checkout.getExpiryYear()) {
			System.out.println("cvv or expiry month or expiry year doesn't match");
			return 0;
		}

		CustomerInfo customerInfo = emiCard.getCustomerInfo();
		if (customerInfo.getIsValidCustomer() <= 0) {
			System.out.println("Not a valid customer");
			return 0;
		}

		String name = customerInfo.getCustomerFirstName() + customerInfo.getCustomerLastName();
		name = name.replaceAll(" ", "").toLowerCase();

		String match = checkout.getCardHolderName();
		match = match.toLowerCase();

		String a[] = match.split(" ");
		int i = 0;
		for (i = 0; i < a.length; i++) {
			if (name.indexOf(a[i]) >= 0)
				break;
		}
		if (i >= a.length) {
			System.out.println("Not a valid Card Holder Name");
			return 0;
		}

		double processingPercentage = 0; // processing Percentage
		if (s1.equals("10012002"))
			processingPercentage = 0.03; // processing Percentage 3 % for
											// titanium
		else
			processingPercentage = 0.05; // processing Percentage 5 % for gold

		Product product = customerRepo.findProductById(checkout.getProductId());
		double amoutWithoutCharge = 0;
		amoutWithoutCharge = product.getProductPrice() * checkout.getProductQuantity();

		if (amoutWithoutCharge * (1 + processingPercentage) > emiCard.getCardBalance()) {
			System.out.println("Not Sufficient Balance");
			return 0;
		}

		EmiTransaction transaction = new EmiTransaction();
		transaction.setCustomerInfo(findCustomerById(checkout.getCustomerId()));
		transaction.setEmiCard(emiCard);
		transaction.setEmiTenure(checkout.getEmiTenure());
		transaction.setTransactionDate(LocalDate.now());
		transaction.setShippingAddress(checkout.getShippingAddress());
		transaction.setProductQuantity(checkout.getProductQuantity());
		transaction.setProduct(product);
		transaction.setProcessingFee(amoutWithoutCharge * processingPercentage);
		transaction.setAmount(amoutWithoutCharge * (1 + processingPercentage));
		transaction.setNoOfEmisLeft(transaction.getEmiTenure());

		return customerRepo.buyAProductOnEmi(transaction, emiCard);
	}

	@Transactional
	public boolean payMyEmi(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null) {
			EmiCard emiCard = customerInfo.getEmiCard();
			if (emiCard != null && emiCard.getCardStatus() && emiCard.getAmountToBePaid() != 0) {
				List<EmiTransaction> transactions = customerRepo
						.getListOfTransactionsOfEmiCardPayPending(emiCard.getCardNumber());

				double sum = 0;
				for (EmiTransaction transaction : transactions) {
					sum += transaction.getAmount() / transaction.getEmiTenure();
				}

				int payingEmis = (int) (emiCard.getAmountToBePaid() / sum);
				System.out.println("Paying emis " + payingEmis);
				for (EmiTransaction transaction : transactions) {
					transaction.setNoOfEmisLeft(transaction.getNoOfEmisLeft() - payingEmis);
					if (transaction.getNoOfEmisLeft() == 0) {
						emiCard.setEmiPerMonth(
								emiCard.getEmiPerMonth() - transaction.getAmount() / transaction.getEmiTenure());
					}

				}
				emiCard.setCardBalance(emiCard.getCardBalance() + sum * payingEmis);
				emiCard.setAmountToBePaid(0);
				return true;
			}
		}

		return false;
	}

	@Override
	public List<EmiTransaction> getListOfTransactionsOfCustomer(int customerId) {
		List<EmiTransaction> transactions=customerRepo.getListOfTransactionsOfCustomer(customerId);
		return transactions;
	}	

	public List<EmiTransaction> viewCardTransactions(int customerId) {
		EmiCard emiCard = customerRepo.findCustomerById(customerId).getEmiCard();
		if (emiCard == null) {
			throw new ViewCardTransactionsException("You Do not have Any EmiCard");
		} else if (emiCard.getCardStatus() == false) {
			throw new ViewCardTransactionsException("Your Card Is not Active Please Active It");
		} else {
			return customerRepo.getListOfTransactionsOfEmiCard(emiCard.getCardNumber());
		}

	}

}
