package com.lti.dto;

import java.time.LocalDate;

import javax.persistence.Column;

public class EmiTransactionDto {
	private int transactionId;

	private double amount;

	private String shippingAddress;

	private LocalDate transactionDate;

	private int emiTenure; // months for which that product is being monthly installements

	private int productQuantity;

	private int noOfEmisLeft;

	private String productName;

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

	public int getNoOfEmisLeft() {
		return noOfEmisLeft;
	}

	public void setNoOfEmisLeft(int noOfEmisLeft) {
		this.noOfEmisLeft = noOfEmisLeft;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "EmiTransactionDto [transactionId=" + transactionId + ", amount=" + amount + ", shippingAddress="
				+ shippingAddress + ", transactionDate=" + transactionDate + ", emiTenure=" + emiTenure
				+ ", productQuantity=" + productQuantity + ", noOfEmisLeft=" + noOfEmisLeft + ", productName="
				+ productName + "]";
	}
	
		

}
