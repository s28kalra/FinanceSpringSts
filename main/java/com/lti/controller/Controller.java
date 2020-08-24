package com.lti.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AdminLoginDto;
import com.lti.dto.EmiTransactionDto;
import com.lti.dto.LoginDto;
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

	public Admin findAdminById(int adminId) {
		return adminService.findAdminById(adminId);
	}

	public Product addProduct(Product product) {
		return adminService.addProduct(product);
	}

	public EmiCard validateCustomerAndIssueEmiCard(int customerId) {
		return adminService.validateCustomerAndIssueEmiCard(customerId);
	}

	public CustomerInfo deactivateACustomer(int customerId) {
		return adminService.deactivateACustomer(customerId);
	}

	public CustomerInfo activateExistingCustomerEmiCard(int customerId) {
		return adminService.activateExistingCustomerEmiCard(customerId);
	}

	public List<CustomerInfo> viewAllCustomers() {
		return adminService.viewAllCustomers();
	}

	public CustomerInfo rejectACustomer(int customerId) {
		return adminService.rejectACustomer(customerId);
	}

	@RequestMapping(path = "/pendingCustomers", method = RequestMethod.GET)
	public List<CustomerInfo> viewAllCustomerPendingForVerfication() {
		return adminService.viewAllCustomerPendingForVerfication();
	}

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

	public CustomerInfo updateCustomer(CustomerInfo customerInfo) {
		return customerService.updateCustomer(customerInfo);
	}

	@RequestMapping(path = "/viewProfile", method = RequestMethod.POST)
	public CustomerInfo findCustomerById(@RequestBody int customerId) {

		return customerService.findCustomerById(customerId);
	}

	public boolean activateEmiCard(int customerId) {
		return customerService.activateEmiCard(customerId);
	}

	public int buyAProductOnEmi(Checkout checkout) {
		return customerService.buyAProductOnEmi(checkout);
	}

	public boolean payMyEmi(int customerId) {
		return customerService.payMyEmi(customerId);
	}

	public long calculateTotalNumberOfRegistrationsBetween(LocalDate from, LocalDate to) {
		return adminService.calculateTotalNumberOfRegistrationsBetween(from, to);
	}

	public double calculateJoiningFeesBetween(LocalDate from, LocalDate to) {
		return adminService.calculateJoiningFeesBetween(from, to);
	}

	public double calculateProcessingFeesBetween(LocalDate from, LocalDate to) {
		return adminService.calculateProcessingFeesBetween(from, to);
	}

	public double calculateProfitBetween(LocalDate from, LocalDate to) {
		return adminService.calculateProfitBetween(from, to);
	}

	@RequestMapping(path = "/getAllProducts", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return adminService.getAllProducts();
	}

	@RequestMapping(path = "/getListOfTransactionsOfCustomer", method = RequestMethod.POST)
	public List<EmiTransaction> getListOfTransactionsOfCustomer(@RequestBody int customerId) {
		return customerService.getListOfTransactionsOfCustomer(customerId);
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

}
