package com.lti.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.enums.StatusType;
import com.lti.model.Admin;
import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;
import com.lti.service.AdminServiceInterface;
import com.lti.service.CustomerServiceInterface;
import com.lti.status.RegisterStatus;

@RestController
@CrossOrigin
public class Controller {
	
	@Autowired
	private CustomerServiceInterface customerService;
	
	@Autowired
	private AdminServiceInterface adminService;
		
	public int addNewAdmin(Admin admin){
		return adminService.addNewAdmin(admin); 
	}
	public Admin updateAdmin(Admin admin){
		return adminService.updateAdmin(admin);
	}
	public Admin findAdminById(int adminId){
		return adminService.findAdminById(adminId);
	}
	public Product addProduct(Product product){
		return adminService.addProduct(product);		
	}
	
	public EmiCard validateCustomerAndIssueEmiCard(int customerId){
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
	
	@RequestMapping(path = "/register",method = RequestMethod.POST)
	public RegisterStatus addnewCustomer(@RequestBody CustomerInfo customerInfo){
		int id=customerService.addNewCustomer(customerInfo);
		RegisterStatus registerStatus=new RegisterStatus();
		if(id>0) {
			registerStatus.setStatus(StatusType.SUCCESS);
			registerStatus.setMessage("Registration Successful\nProceed To Login");
		}
		else {
			registerStatus.setStatus(StatusType.FAILURE);
			registerStatus.setMessage("Sorry Try with Different Details");
		}
		return registerStatus;		
	}

	public CustomerInfo updateCustomer(CustomerInfo customerInfo){
		return customerService.updateCustomer(customerInfo);
	}
	
	public CustomerInfo findCustomerById(int customerId){
		return customerService.findCustomerById(customerId);
	}
	
	public boolean activateEmiCard(int customerId){
		return customerService.activateEmiCard(customerId);
	}
	
	public int buyAProductOnEmi(Checkout checkout){
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
	
	@RequestMapping(path = "/getAllProducts",method = RequestMethod.GET)
		public List<Product> getAllProducts(){
			return adminService.getAllProducts();
	}
	
	@RequestMapping(path="/getListOfTransactionsOfCustomer",method =RequestMethod.POST)
	public List<EmiTransaction> getListOfTransactionsOfCustomer(@RequestBody int customerId){
		return customerService.getListOfTransactionsOfCustomer(customerId);
	}
}

