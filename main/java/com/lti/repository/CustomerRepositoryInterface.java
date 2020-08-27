package com.lti.repository;

import java.util.List;

import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;

public interface CustomerRepositoryInterface {
	
	int addNewCustomer(CustomerInfo customerInfo);
	int  findEmailAndPassword(String email,String password);
	CustomerInfo isCustomerAvailable(String email);
	boolean isNewCustomerUnique(String email,String mobile, String aadhar);
	CustomerInfo updateCustomer(CustomerInfo customerInfo);
	CustomerInfo findCustomerById(int customerId);
	int buyAProductOnEmi(EmiTransaction transaction, EmiCard card);
	Product findProductById(int productId);
	List<EmiTransaction> getListOfTransactionsOfEmiCard(int cardNumber);
	List<EmiTransaction> getListOfTransactionsOfEmiCardPayPending(int cardNumber);
	List<EmiTransaction> getListOfTransactionsOfCustomer(int customerId);
	CustomerInfo findCustomerByEmail(String email);
	
}
