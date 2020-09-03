package com.lti.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dto.StatisticsDate;
import com.lti.email.EmailService;
import com.lti.exception.AdminException;
import com.lti.exception.CustomerServiceException;
import com.lti.model.Admin;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.Product;
import com.lti.repository.AdminRepositoryInterface;
import com.lti.repository.CustomerRepositoryInterface;
import com.lti.status.Statistics;

@Service
public class AdminService implements AdminServiceInterface {

	@Autowired
	private AdminRepositoryInterface adminRepo;

	@Autowired
	private CustomerRepositoryInterface customerRepo;
	
	@Autowired
	private EmailService emailService;

	public int addNewAdmin(Admin admin) {
		return adminRepo.addNewAdmin(admin);
	}

	public Admin updateAdmin(Admin admin) {
		return adminRepo.updateAdmin(admin);
	}

	public Admin findAdminById(int adminId) {
		return adminRepo.findAdminById(adminId);
	}

	@Transactional
	public Product addProduct(Product product) {
		return adminRepo.addProduct(product);
	}

	public EmiCard validateCustomerAndIssueEmiCard(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null) {

			EmiCard emiCard = new EmiCard();
			customerInfo.setIsValidCustomer(1);
			if (customerInfo.getCardType().equals("Gold")) {
				emiCard.setCardNumberStart("30034004");
				emiCard.setCardLimit(100000);
			} else {
				emiCard.setCardNumberStart("10012002");
				emiCard.setCardLimit(200000);
			}
			emiCard.setEmiPerMonth(0);
			emiCard.setCardStatus(false);
			emiCard.setCardExpiry((YearMonth.now().plusYears(5)).atEndOfMonth());
			emiCard.setCardCvv((int) Math.round(Math.random() * 899) + 100);
			emiCard.setCardBalance(emiCard.getCardLimit());
			emiCard.setJoiningFees(0);
			emiCard.setAmountToBePaid(0);

			emiCard.setCustomerInfo(customerInfo);
			customerInfo.setEmiCard(emiCard);

			customerInfo = customerRepo.updateCustomer(customerInfo);
			return customerInfo.getEmiCard();
		}
		return null;
	}

	@Transactional
	public CustomerInfo deactivateACustomer(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null) {
			if (customerInfo.getEmiCard() != null) {
				customerInfo.getEmiCard().setCardStatus(false);
			}
			return customerInfo;
		}
		return null;
	}

	@Transactional
	public CustomerInfo activateExistingCustomerEmiCard(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null && customerInfo.getIsValidCustomer() == 1) {
			customerInfo.getEmiCard().setCardStatus(true);
			return customerInfo;
		}
		return null;
	}

	@Transactional
	public CustomerInfo rejectACustomer(int customerId) {
		CustomerInfo customerInfo = customerRepo.findCustomerById(customerId);
		if (customerInfo != null) {
			customerInfo.setIsValidCustomer(-1);
			return customerInfo;
		}
		return null;
	}

	public List<CustomerInfo> viewAllCustomers() {
		return adminRepo.viewAllCustomers();
	}

	public List<CustomerInfo> viewAllCustomerPendingForVerfication() {
		return adminRepo.viewAllCustomerPendingForVerfication();
	}

	public boolean generateBill() {
		boolean bill= adminRepo.generateBill();
		List<EmiCard> cards=adminRepo.getAllEmiCards();
		for(EmiCard e: cards) {
			CustomerInfo c= e.getCustomerInfo();
			emailService.sendBillEmail(c.getCustomerFirstName(), c.getCustomerEmail(), Math.ceil(e.getAmountToBePaid()));
		}
		return bill;
	}

	@Override
	public long calculateTotalNumberOfRegistrationsBetween(LocalDate from, LocalDate to) {
		return adminRepo.calculateTotalNumberOfRegistrationsBetween(from, to);
	}

	@Override
	public double calculateJoiningFeesBetween(LocalDate from, LocalDate to) {
		return adminRepo.calculateJoiningFeesBetween(from, to);
	}

	@Override
	public double calculateProcessingFeesBetween(LocalDate from, LocalDate to) {
		return adminRepo.calculateProcessingFeesBetween(from, to);
	}

	@Override
	public double calculateProfitBetween(LocalDate from, LocalDate to) {
		return adminRepo.calculateJoiningFeesBetween(from, to) + adminRepo.calculateProcessingFeesBetween(from, to);
	}

	@Override
	public List<Product> getAllProducts() {
		return adminRepo.getAllProducts();
	}

	@Override
	public Admin loginAdmin(int adminId, String adminPassword) {
		Admin admin = adminRepo.findAdminById(adminId);
		if (admin == null) {
			throw new AdminException("Admin not registered");
		} else if (!admin.getAdminPassword().equals(adminPassword)) {
			throw new AdminException("Incorrect Admin Id or password");
		}
		return admin;
	}

	@Override
	public Statistics calculateStatistics(StatisticsDate statisticsDate) {
		LocalDate from = statisticsDate.getFrom();
		LocalDate to = statisticsDate.getTo().plusDays(1);
		Statistics statistics = new Statistics();
		statistics.setRegistrations(adminRepo.calculateTotalNumberOfRegistrationsBetween(from, to));
		statistics.setProcessingFees(adminRepo.calculateProcessingFeesBetween(from, to));
		statistics.setJoiningFees(adminRepo.calculateJoiningFeesBetween(from, to));
		statistics.setTotalProfit(statistics.getJoiningFees() + statistics.getProcessingFees());
		return statistics;
	}

}
