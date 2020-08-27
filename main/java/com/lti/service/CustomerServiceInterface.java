package com.lti.service;

import java.util.List;

import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiTransaction;

public interface CustomerServiceInterface {

	int addNewCustomer(CustomerInfo customerInfo);

	CustomerInfo updateCustomer(CustomerInfo customerInfo);

	CustomerInfo findCustomerById(int customerId);

	boolean activateEmiCard(int customerId);

	int buyAProductOnEmi(Checkout checkout);

	boolean payMyEmi(int customerId);

	List<EmiTransaction> getListOfTransactionsOfCustomer(int customerId);

	CustomerInfo loginCustomer(String customerEmail, String customerPassword);

	List<EmiTransaction> viewCardTransactions(int customerId);
	
	CustomerInfo forgotPassword(String email);
	
	int validateAnOtp(String email, String otp);


}
