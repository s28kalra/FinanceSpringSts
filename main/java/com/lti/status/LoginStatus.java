package com.lti.status;

public class LoginStatus extends RegisterStatus{
	private int customerId;
	private String customerFirstName;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	@Override
	public String toString() {
		return "LoginStatus [getStatus()=" + getStatus() + ", getMessage()=" + getMessage() + "]";
	}
	
	
}
