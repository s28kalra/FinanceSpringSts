package com.lti.service;

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
}
