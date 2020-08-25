package com.lti.dto;

import java.time.LocalDate;

public class EmiCardDto {
	
	private String customerName;
	private String cardNumber;
	private LocalDate cardExpiry;
	private int cardCvv;
	private boolean cardStatus;
	private double amountToBePaid; 
	private double cardBalance;
	private double cardLimit;
	private String cardType;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public LocalDate getCardExpiry() {
		return cardExpiry;
	}
	public void setCardExpiry(LocalDate cardExpiry) {
		this.cardExpiry = cardExpiry;
	}
	public int getCardCvv() {
		return cardCvv;
	}
	public void setCardCvv(int cardCvv) {
		this.cardCvv = cardCvv;
	}
	public boolean isCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(boolean cardStatus) {
		this.cardStatus = cardStatus;
	}
	public double getAmountToBePaid() {
		return amountToBePaid;
	}
	public void setAmountToBePaid(double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}
	public double getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(double cardBalance) {
		this.cardBalance = cardBalance;
	}
	public double getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(double cardLimit) {
		this.cardLimit = cardLimit;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Override
	public String toString() {
		return "EmiCardDto [customerName=" + customerName + ", cardNumber=" + cardNumber + ", cardExpiry=" + cardExpiry
				+ ", cardCvv=" + cardCvv + ", cardStatus=" + cardStatus + ", amountToBePaid=" + amountToBePaid
				+ ", cardBalance=" + cardBalance + ", cardLimit=" + cardLimit + ", cardType=" + cardType + "]";
	}

	
	
}
