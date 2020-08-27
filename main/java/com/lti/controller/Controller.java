package com.lti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AdminLoginDto;
import com.lti.dto.EmiCardDto;
import com.lti.dto.EmiTransactionDto;
import com.lti.dto.LoginDto;
import com.lti.dto.StatisticsDate;
import com.lti.dto.ValidateAnOtp;
import com.lti.enums.StatusType;
import com.lti.exception.AdminException;
import com.lti.exception.CustomerServiceException;
import com.lti.exception.ViewCardTransactionsException;
import com.lti.model.Admin;
import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;
import com.lti.service.AdminServiceInterface;
import com.lti.service.CustomerServiceInterface;
import com.lti.status.AdminLoginStatus;
import com.lti.status.LoginStatus;
import com.lti.status.RegisterStatus;
import com.lti.status.Statistics;
import com.lti.status.ViewCardTransactionsStatus;

@RestController
@CrossOrigin
public class Controller {

	@Autowired
	private CustomerServiceInterface customerService;

	@Autowired
	private AdminServiceInterface adminService;

	public int addNewAdmin(Admin admin) {
		return adminService.addNewAdmin(admin);
	}

	public Admin updateAdmin(Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@RequestMapping(path = "/viewAdminProfile", method = RequestMethod.POST)
	public Admin findAdminById(@RequestBody int adminId) {
		return adminService.findAdminById(adminId);
	}

	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) {
		return adminService.addProduct(product);
	}

	@RequestMapping(path = "/validateCustomerAndIssueEmiCard", method = RequestMethod.POST)
	public EmiCard validateCustomerAndIssueEmiCard(@RequestBody int customerId) {
		return adminService.validateCustomerAndIssueEmiCard(customerId);
	}

	@RequestMapping(path = "/viewAllCustomers", method = RequestMethod.GET)
	public List<CustomerInfo> viewAllCustomers() {
		return adminService.viewAllCustomers();
	}

	@PostMapping("/rejectACustomer")
	public CustomerInfo rejectACustomer(@RequestBody int customerId) {
		return adminService.rejectACustomer(customerId);
	}

	@RequestMapping(path = "/pendingCustomers", method = RequestMethod.GET)
	public List<CustomerInfo> viewAllCustomerPendingForVerfication() {
		return adminService.viewAllCustomerPendingForVerfication();
	}

	@GetMapping("/generateBill")
	public boolean generateBill() {
		return adminService.generateBill();
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public RegisterStatus addNewCustomer(@RequestBody CustomerInfo customerInfo) {
		int id = customerService.addNewCustomer(customerInfo);
		RegisterStatus registerStatus = new RegisterStatus();
		if (id > 0) {
			registerStatus.setStatus(StatusType.SUCCESS);
			registerStatus.setMessage("Registration Successful\nProceed To Login");
		} else {
			registerStatus.setStatus(StatusType.FAILURE);
			registerStatus.setMessage("Sorry Try with Different Details");
		}
		return registerStatus;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public LoginStatus loginCustomer(@RequestBody LoginDto loginDto) {
		try {

			CustomerInfo customerInfo = customerService.loginCustomer(loginDto.getCustomerEmail(),
					loginDto.getCustomerPassword());
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login successful");
			loginStatus.setCustomerId(customerInfo.getCustomerId());
			loginStatus.setCustomerFirstName(customerInfo.getCustomerFirstName());
			return loginStatus;
		} catch (CustomerServiceException e) {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;

		}
	}
	@RequestMapping(path = "/updateCustomer", method = RequestMethod.POST)
	public CustomerInfo updateCustomer(@RequestBody CustomerInfo customerInfo) {
		return customerService.updateCustomer(customerInfo);
	}

	@RequestMapping(path = "/viewProfile", method = RequestMethod.POST)
	public CustomerInfo findCustomerById(@RequestBody int customerId) {
		return customerService.findCustomerById(customerId);
	}

	@RequestMapping(path = "/activateEmiCard", method = RequestMethod.POST)
	public boolean activateEmiCard(@RequestBody int customerId) {
		return customerService.activateEmiCard(customerId);
	}


	@RequestMapping(path = "/payMyEmi", method = RequestMethod.POST)
	public boolean payMyEmi(@RequestBody int customerId) {
		return customerService.payMyEmi(customerId);
	}

	@PostMapping("/calculateStatistics")
	public Statistics calculateStatistics(@RequestBody StatisticsDate statisticsDate) {
		return adminService.calculateStatistics(statisticsDate);
	}

	@RequestMapping(path = "/getAllProducts", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return adminService.getAllProducts();
	}

	@RequestMapping(path = "/getListOfTransactionsOfCustomer", method = RequestMethod.POST)
	public List<EmiTransaction> getListOfTransactionsOfCustomer(@RequestBody int customerId) {
		return customerService.getListOfTransactionsOfCustomer(customerId);
	}

	@RequestMapping(path = "/getCardDetails", method = RequestMethod.POST)
	public EmiCardDto getCardDetails(@RequestBody int customerId) {
		EmiCardDto cardDto = new EmiCardDto();
		CustomerInfo customerInfo = customerService.findCustomerById(customerId);
		if (customerInfo == null) {
			return null;
		}
		EmiCard emiCard = customerInfo.getEmiCard();
		if(emiCard==null)
			return null;
		BeanUtils.copyProperties(emiCard, cardDto);
		if (customerInfo.getCustomerLastName() == null) {
			cardDto.setCustomerName(customerInfo.getCustomerFirstName());
		} else {
			cardDto.setCustomerName(customerInfo.getCustomerFirstName() + " " + customerInfo.getCustomerLastName());
		}
		cardDto.setCardType(customerInfo.getCardType());
		cardDto.setCardNumber(
				emiCard.getCardNumberStart().substring(0, 4) + " " + emiCard.getCardNumberStart().substring(4, 8) + " "
						+ String.valueOf(emiCard.getCardNumber()).substring(0, 4) + " "
						+ String.valueOf(emiCard.getCardNumber()).substring(4, 8));
		return cardDto;
	}

	@RequestMapping(path = "/viewCardTransactions", method = RequestMethod.POST)
	public ViewCardTransactionsStatus viewCardTransactions(@RequestBody int customerId) {
		ViewCardTransactionsStatus status = new ViewCardTransactionsStatus();
		try {
			List<EmiTransaction> transactions = customerService.viewCardTransactions(customerId);
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("List Reterived");
			List<EmiTransactionDto> list = new ArrayList<>();
			for (EmiTransaction trans : transactions) {
				EmiTransactionDto dto = new EmiTransactionDto();
				BeanUtils.copyProperties(trans, dto);
				dto.setProductName(trans.getProduct().getProductName());
				list.add(dto);
			}
			status.setTransactions(list);

		} catch (ViewCardTransactionsException e) {
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			status.setTransactions(null);
		}
		return status;
	}

	@RequestMapping(path = "/loginAdmin", method = RequestMethod.POST)
	public AdminLoginStatus loginAdmin(@RequestBody AdminLoginDto adminLoginDto) {
		AdminLoginStatus adminLoginStatus = new AdminLoginStatus();
		int adminId = 0;
		try {
			adminId = Integer.valueOf(adminLoginDto.getAdminId());
		} catch (NumberFormatException e) {
			adminLoginStatus.setStatus(StatusType.FAILURE);
			adminLoginStatus.setMessage("Invalid AdminId");
			return adminLoginStatus;
		}
		try {
			Admin admin = adminService.loginAdmin(adminId, adminLoginDto.getAdminPassword());
			adminLoginStatus.setStatus(StatusType.SUCCESS);
			adminLoginStatus.setMessage("Login successful");
			adminLoginStatus.setAdminId(admin.getAdminId());
			adminLoginStatus.setAdminName(admin.getAdminName());
			return adminLoginStatus;
		} catch (AdminException e) {
			adminLoginStatus.setStatus(StatusType.FAILURE);
			adminLoginStatus.setMessage(e.getMessage());
			return adminLoginStatus;
		}
	}

	@RequestMapping(path = "/buyAProductOnEmi", method = RequestMethod.POST)
	public int buyAProductOnEmi(@RequestBody Checkout checkout) {
		return customerService.buyAProductOnEmi(checkout);
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestBody String email) {
		String status = "";
		CustomerInfo customerInfo = customerService.forgotPassword(email);
		if (customerInfo == null)
			status = "Invalid_Email";
		else
			status = "Hi " + customerInfo.getCustomerFirstName();
		return status;
	}

	@PostMapping("/validateAnOtp")
	public int validateAnOtp(@RequestBody ValidateAnOtp validateAnOtp) {
		int id=customerService.validateAnOtp(validateAnOtp.getEmail(), validateAnOtp.getOtp());
		if(id>0)
			return id;
		return 0;
			
	}


}
