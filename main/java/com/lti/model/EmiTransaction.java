package com.lti.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EmiTransaction {
	@Id
	@SequenceGenerator(name="transaction_id",initialValue=4203 ,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transaction_id")
	private int transactionId;
	
	@Column
	private double amount;
	
	@Column
	private String shippingAddress;
	
	@Column
	private LocalDate transactionDate;
	
	@Column
	private int emiTenure; // months for which that product is being monthly installements
	
	@Column
	private int productQuantity;
	
	@Column
	private double processingFee;
	
	@Column
	private int noOfEmisLeft;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="card_number")
	private EmiCard emiCard;
	
	@ManyToOne
	//@JsonIgnore
	@JoinColumn(name="productid")
	private Product product;
		
	@ManyToOne
	@JoinColumn(name="customerid")
	@JsonIgnore
	private CustomerInfo customerInfo;
	
	public int getNoOfEmisLeft() {
		return noOfEmisLeft;
	}

	public void setNoOfEmisLeft(int noOfEmisLeft) {
		this.noOfEmisLeft = noOfEmisLeft;
	}

	public EmiCard getEmiCard() {
		return emiCard;
	}

	public void setEmiCard(EmiCard emiCard) {
		this.emiCard = emiCard;
	}

	public double getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getEmiTenure() {
		return emiTenure;
	}

	public void setEmiTenure(int emiTenure) {
		this.emiTenure = emiTenure;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	@Override
	public String toString() {
		return "EmiTransaction [transactionId=" + transactionId + ", amount=" + amount + ", shippingAddress="
				+ shippingAddress + ", transactionDate=" + transactionDate + ", emiTenure=" + emiTenure
				+ ", productQuantity=" + productQuantity + ", product=" + product + ", customerInfo=" + customerInfo
				+ "]";
	}
	

	
	
	
	
	
	

}
