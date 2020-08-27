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
import com.lti.dto.EmiCardDto;
import com.lti.dto.LoginDto;
import com.lti.dto.StatisticsDate;
import com.lti.email.EmailService;
import com.lti.model.Admin;
import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;
import com.lti.repository.AdminRepository;
import com.lti.repository.AdminRepositoryInterface;

import net.bytebuddy.asm.Advice.Local;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class EasyCreditApplicationTests {
	
	@Autowired
	private Controller controller;
	
	@Autowired
	AdminRepositoryInterface adminRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Test
	public void addNewAdmin() {
		Admin admin= new Admin();

		admin.setAdminId(10670502);
		admin.setAdminName("Sakshi");
		admin.setAdminPassword("Sakshi@1234");

		admin.setAdminId(10670454);
		admin.setAdminName("Shivam");
		admin.setAdminPassword("Shivam@1234");

		System.out.println(controller.addNewAdmin(admin));
	}
	
	@Test
	public void updateAdmin(){
		Admin admin= new Admin();

		admin.setAdminId(10670514);
		admin.setAdminName("Krishna Vatsa");
		admin.setAdminPassword("Krish@123");

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
		product.setProductName("Fossil Gen 5 Smartwatch");
		product.setProductImageSource("assets/fossilwatch.jpg");
		product.setProductPrice(23000);
		product.setProductDescription("Fossil Gen 5 Garrett Touchscreen Smartwatch with Speaker. Heart Rate & Activity Tracking using Google Fit; Built-in GPS for distance tracking; Swimproof design 5ATM; responses from Google Assistant");

		System.out.println(controller.addProduct(product));
	}
	
	@Test
	public void validateCustomerAndIssueEmiCard(){
		System.out.println(controller.validateCustomerAndIssueEmiCard(10105));



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
		System.out.println(controller.activateExistingCustomerEmiCard(10103));
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
	public void getCardDetails() {
		System.out.println(controller.getCardDetails(10103));
	}

	@Test
	public void addNewCustomer() {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerFirstName("Riya");
		customerInfo.setCustomerLastName("Sharma");
		customerInfo.setCustomerEmail("riya@lti.com");
		customerInfo.setCustomerMobile("9860912456");
		customerInfo.setAccountNumber("1212121289");
		customerInfo.setCardType("Gold");
		customerInfo.setCustomerAadharCard("982845129511");
		customerInfo.setCustomerPassword("riya@123");
		customerInfo.setDateOfBirth(LocalDate.of(1990, 03, 23));
		customerInfo.setIfsc("CBIN69203401");
		System.out.println(controller.addNewCustomer(customerInfo));
	}
	

	@Test
	public void updateCustomer() {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setCustomerId(10137);
		customerInfo.setCustomerFirstName("Jackson");
		customerInfo.setCustomerLastName("Action");
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
		System.out.println(controller.activateEmiCard(10139));
	}

	@Test
	public void findCustomerById() {
		System.out.println(controller.findCustomerById(10187));
	}

	@Test
	public void buyAProductOnEmi() {
		Checkout checkout = new Checkout();

		checkout.setCustomerId(10105);
		checkout.setProductId(5200); 
		checkout.setProductQuantity(1);
		checkout.setEmiTenure(8);
		checkout.setShippingAddress("Kaithal");
		checkout.setCardHolderName("dev");
		checkout.setCardNumber("3003400430012553");
		checkout.setExpiryMonth(8);
		checkout.setExpiryYear(2025);
		checkout.setCvv(111);

		checkout.setCustomerId(10103);
		checkout.setProductId(5251); 
		checkout.setProductQuantity(2);
		checkout.setEmiTenure(8);
		checkout.setShippingAddress("Dehradun");
		checkout.setCardHolderName("Ram Kumar");
		checkout.setCardNumber("3003400430012551");
		checkout.setExpiryMonth(8);
		checkout.setExpiryYear(2025);
		checkout.setCvv(584);

		System.out.println(controller.buyAProductOnEmi(checkout));
	}
	

	@Test
	public void generateBill(){
		System.out.println(controller.generateBill());
	}
	
	@Test
	public void payMyEmi() {
		System.out.println(controller.payMyEmi(10103));
	}
	
	@Test
	public void calculateStatistics() {
		int x=90;
		StatisticsDate statisticsDate= new StatisticsDate();
		statisticsDate.setFrom(LocalDate.now().minusDays(x));
		statisticsDate.setTo(LocalDate.now());
		System.out.println(controller.calculateStatistics(statisticsDate));
	}
	
	@Test
	public void joining() {
		LocalDate from=LocalDate.now().minusDays(90);
		LocalDate to=LocalDate.now();
		System.out.println(adminRepo.calculateJoiningFeesBetween(from, to));
		System.out.println(adminRepo.calculateTotalNumberOfRegistrationsBetween(from, to));
		System.out.println(adminRepo.calculateProcessingFeesBetween(from, to));
	}
	
	
	@Test
	public void getAllProducts(){
		List<Product> allProducts= controller.getAllProducts();
		for(Product p: allProducts)
			System.out.println(p);
	}
	
	@Test
	public void getListOfTransactionsOfCustomer() {
		List<EmiTransaction> transactions=controller.getListOfTransactionsOfCustomer(10103);
		for (EmiTransaction emiTransaction : transactions) {
			System.out.println(emiTransaction);
		}
	}
	
	@Test
	public void loginCustomer() {
		LoginDto loginDto=new LoginDto();
		loginDto.setCustomerEmail("johnkalra@lti.com");
		loginDto.setCustomerPassword("John@1234@1234");
		System.out.println(controller.loginCustomer(loginDto));
		
	}
	
	@Test
	public void viewCardTransactions(){
		System.out.println(controller.viewCardTransactions(10103));
	}
	
	@Test
	public void sendRegisterEmail(){
		System.out.println(emailService.sendRegisterEmail("Sagar", "sagarkalra03@gmail.com"));
	}
	
	@Test 
	public void sendOtpEmail() {
		int otp=emailService.sendOtpEmail("sagarkalra03@gmail.com");
		System.out.println(otp);
	}
	
	
	
	
	
	
	
	
}
