package com.lti;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.lti.controller.Controller;
import com.lti.model.Admin;
import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.Product;

import net.bytebuddy.asm.Advice.Local;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class EasyCreditApplicationTests {
	
	@Autowired
	private Controller controller;
	
	@Test
	public void addNewAdmin() {
		Admin admin= new Admin();
		admin.setAdminId(10670576);
		admin.setAdminName("Sagar");
		admin.setAdminPassword("Sagar@1234");
		System.out.println(controller.addNewAdmin(admin));
	}
	
	@Test
	public void updateAdmin(){
		Admin admin= new Admin();
		admin.setAdminId(10670576);
		admin.setAdminName("Sagar Kalra");
		admin.setAdminPassword("Sagar@1234");
//		System.out.println(adminRepo.updateAdmin(admin));
		System.out.println(controller.updateAdmin(admin));
	}
	
	@Test
	public void findAdminById(){
		System.out.println(controller.findAdminById(10670454));
	}
	
	@Test
	public void addProduct(){
		Product product= new Product();
		product.setProductName("Iphone XS");
		product.setProductImageSource("assets/iphoneXS.jpg");
		product.setProductPrice(60000);
		product.setProductDescription("5.8-inch Super Retina display (OLED) with HDR1, IP68 dust and water resistant (maximum depth of 2 metres up to 30 minutes)2");
		System.out.println(controller.addProduct(product));
	}
	
	@Test
	public void validateCustomerAndIssueEmiCard(){
		System.out.println(controller.validateCustomerAndIssueEmiCard(10187));
	}
	
	@Test
	public void deactivateACustomer(){
		System.out.println(controller.deactivateACustomer(10107));
	}
	
	@Test
	public void viewAllCustomers(){
		List<CustomerInfo> allCustomers= controller.viewAllCustomers();
		for(CustomerInfo c: allCustomers)
			System.out.println(c);
	}
	
	@Test
	public void rejectACustomer(){
		System.out.println(controller.rejectACustomer(10103));
	}
	
	@Test
	public void activateExistingCustomerEmiCard(){
		System.out.println(controller.activateExistingCustomerEmiCard(10107));
	}
	
	@Test
	public void viewAllCustomerPendingForVerfication() {
		List<CustomerInfo> pendingCustomers= controller.viewAllCustomerPendingForVerfication();
		for(CustomerInfo c:pendingCustomers)
			System.out.println(c);
	}
	
	@Test
	public void trySomething(){
		
	}	

	@Test
	public void addNewCustomer() {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerFirstName("Jully");
		customerInfo.setCustomerLastName("");
		customerInfo.setCustomerEmail("jully@lti.com");
		customerInfo.setCustomerMobile("6876476546747");
		customerInfo.setAccountNumber("0081382768322");
		customerInfo.setCardType("Gold");
		customerInfo.setCustomerAadharCard("215427849673");
		customerInfo.setCustomerPassword("Jully@1234");
		customerInfo.setDateOfBirth(LocalDate.of(1980, 12, 22));
		customerInfo.setIfsc("CBUJ1234567");
		System.out.println(controller.addnewCustomer(customerInfo));
	}
	

	@Test
	public void updateCustomer() {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerId(10137);
		customerInfo.setCustomerFirstName("Jackson");
		customerInfo.setCustomerLastName("");
		customerInfo.setCustomerEmail("Jackson@lti.com");
		customerInfo.setCustomerMobile("71537678746");
		customerInfo.setAccountNumber("6487687863863812");
		customerInfo.setCardType("Titanium");
		customerInfo.setCustomerAadharCard("2139127363872");
		customerInfo.setCustomerPassword("Jackson@1234");
		customerInfo.setDateOfBirth(LocalDate.of(1975, 4, 25));
		customerInfo.setIfsc("CBUJ1234567");
		customerInfo.setIsValidCustomer(0); // initially 0
		System.out.println(controller.updateCustomer(customerInfo));
	}
	
	@Test
	public void activateEmiCard() {
		System.out.println(controller.activateEmiCard(10187));
	}

	@Test
	public void findCustomerById() {
		System.out.println(controller.findCustomerById(10187));
	}

	@Test
	public void buyAProductOnEmi() {
		Checkout checkout = new Checkout();
		checkout.setCustomerId(10187);
		checkout.setProductId(7201); 
		checkout.setProductQuantity(2);
		checkout.setEmiTenure(8);
		checkout.setShippingAddress("Kaithal");
		checkout.setCardHolderName("Jully");
		checkout.setCardNumber("3003400430012634");
		checkout.setExpiryMonth(8);
		checkout.setExpiryYear(2025);
		checkout.setCvv(726);
		System.out.println(controller.buyAProductOnEmi(checkout));
	}
	

	@Test
	public void generateBill(){
		System.out.println(controller.generateBill());
	}
	
	@Test
	public void payMyEmi() {
		System.out.println(controller.payMyEmi(10187));
	}
	
	@Test
	public void calculateTotalNumberOfRegistrationsBetween() {
		int x=90;
		LocalDate from = LocalDate.now().minusDays(x);
		LocalDate to = LocalDate.now();
		System.out.println(controller.calculateTotalNumberOfRegistrationsBetween(from, to));
	}
	
	@Test
	public void calculateJoiningFeesBetween() {
		int x=90;
		LocalDate from = LocalDate.now().minusDays(x);
		LocalDate to = LocalDate.now();
		System.out.println(controller.calculateJoiningFeesBetween(from, to));
	}
	
	@Test
	public void calculateProcessingFeesBetween() {
		int x=90;
		LocalDate from = LocalDate.now().minusDays(x);
		LocalDate to = LocalDate.now();
		System.out.println(controller.calculateProcessingFeesBetween(from, to));
	}
	
	@Test
	public void calculateProfitBetween() {
		int x=90;
		LocalDate from = LocalDate.now().minusDays(x);
		LocalDate to = LocalDate.now();
		System.out.println(controller.calculateProfitBetween(from, to));
	}
	
	
	
	
}