package com.lti.repository;

import java.time.LocalDate;
import java.util.List;

import com.lti.model.Admin;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.Product;

public interface AdminRepositoryInterface {
	
	int addNewAdmin(Admin admin);
	Admin updateAdmin(Admin admin);
	Admin findAdminById(int adminId);
	Product addProduct(Product product);
	List<Product> getAllProducts(); 
	List<CustomerInfo> viewAllCustomers();
	List<CustomerInfo> viewAllCustomerPendingForVerfication();
	EmiCard findEmiCardByCardNumber(int cardNumber);
	EmiCard updateEmiCard(EmiCard emiCard); 
	boolean generateBill();
	List<EmiCard> getAllEmiCards();
	Long calculateTotalNumberOfRegistrationsBetween(LocalDate from, LocalDate to);
	Double calculateJoiningFeesBetween(LocalDate from, LocalDate to);
	Double calculateProcessingFeesBetween(LocalDate from, LocalDate to);
}
