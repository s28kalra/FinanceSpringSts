package com.lti.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CustomerInfo {
	@Id
	@SequenceGenerator(name="customer_id",initialValue=10100 ,allocationSize=2)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_id")
	private int customerId;
	
	@Column(nullable=false)
	private String customerFirstName;
	
	@Column
	private String customerLastName;
	
	@Column(nullable=false)
	private LocalDate dateOfBirth;
	
	@Column(nullable=false, unique=true)
	private String customerEmail;
	
	@Column(nullable=false, unique=true)
	private String customerMobile;
	
	@Column(nullable=false)
	private String customerPassword;
	
	@Column
	private String cardType;
	
	@Column(nullable=false)
	private String accountNumber;
	
	@Column(nullable=false)
	private String ifsc;
	
	@Column(nullable=false, unique= true)
	private String customerAadharCard;
	
	@Column
	private int isValidCustomer; // initially on registration it will be false, only admin can make this true;
	// and after when it is true an emi card will be issued to this customer 
	
	@Column
	private LocalDate registrationDate;
	
	@OneToOne(mappedBy="customerInfo", cascade=CascadeType.ALL)
	@JsonIgnore
	private EmiCard emiCard;
	
	@OneToMany(mappedBy="customerInfo", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<EmiTransaction> transactions;

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

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getCustomerAadharCard() {
		return customerAadharCard;
	}

	public void setCustomerAadharCard(String customerAadharCard) {
		this.customerAadharCard = customerAadharCard;
	}

	public int getIsValidCustomer() {
		return isValidCustomer;
	}

	public void setIsValidCustomer(int isValidCustomer) {
		this.isValidCustomer = isValidCustomer;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public EmiCard getEmiCard() {
		return emiCard;
	}

	public void setEmiCard(EmiCard emiCard) {
		this.emiCard = emiCard;
	}

	public List<EmiTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<EmiTransaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "CustomerInfo [customerId=" + customerId + ", customerFirstName=" + customerFirstName
				+ ", customerLastName=" + customerLastName + ", dateOfBirth=" + dateOfBirth + ", customerEmail="
				+ customerEmail + ", customerMobile=" + customerMobile + ", customerPassword=" + customerPassword
				+ ", cardType=" + cardType + ", accountNumber=" + accountNumber + ", ifsc=" + ifsc
				+ ", customerAadharCard=" + customerAadharCard + ", isValidCustomer=" + isValidCustomer
				+ ", registrationDate=" + registrationDate + "]";
	}
	
	
	
	

}
