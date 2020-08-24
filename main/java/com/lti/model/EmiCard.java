package com.lti.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EmiCard {
	@Id
	@SequenceGenerator(name="emi_card_number",initialValue=30012551 ,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emi_card_number")
	private int cardNumber;
	
	@Column
	private String cardNumberStart;
	
	@Column
	private LocalDate cardExpiry;
	
	@Column
	private int cardCvv;
	
	@Column
	private double cardBalance;
	
	@Column
	private double cardLimit;
	
	@Column
	private double emiPerMonth;
	
	@Column
	private boolean cardStatus;
	
	@Column
	private double joiningFees;
	
	@Column
	private LocalDate joiningFeesDate;
	
	@Column
	private double amountToBePaid;

	@OneToOne
	@JoinColumn(name="customerid")
	@JsonIgnore
	private CustomerInfo customerInfo;
	
	@OneToMany(mappedBy="emiCard",cascade=CascadeType.ALL)
	private List<EmiTransaction> transactions;

	public List<EmiTransaction> getTransactions() {
		return transactions;
	}
	
	public double getAmountToBePaid() {
		return amountToBePaid;
	}

	public void setAmountToBePaid(double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}

	public void setTransactions(List<EmiTransaction> transactions) {
		this.transactions = transactions;
	}

	public LocalDate getJoiningFeesDate() {
		return joiningFeesDate;
	}

	public void setJoiningFeesDate(LocalDate joiningFeesDate) {
		this.joiningFeesDate = joiningFeesDate;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public double getJoiningFees() {
		return joiningFees;
	}


	public void setJoiningFees(double joiningFees) {
		this.joiningFees = joiningFees;
	}


	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardNumberStart() {
		return cardNumberStart;
	}

	public void setCardNumberStart(String cardNumberStart) {
		this.cardNumberStart = cardNumberStart;
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

	public double getEmiPerMonth() {
		return emiPerMonth;
	}

	public void setEmiPerMonth(double emiPerMonth) {
		this.emiPerMonth = emiPerMonth;
	}

	public boolean getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(boolean cardStatus) {
		this.cardStatus = cardStatus;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	@Override
	public String toString() {
		return "EmiCard [cardNumber=" + cardNumber + ", cardNumberStart=" + cardNumberStart + ", cardExpiry="
				+ cardExpiry + ", cardCvv=" + cardCvv + ", cardBalance=" + cardBalance + ", cardLimit=" + cardLimit
				+ ", emiPerMonth=" + emiPerMonth + ", cardStatus=" + cardStatus + ", joiningFees=" + joiningFees
				+ ", joiningFeesDate=" + joiningFeesDate + ", customerInfo=" + customerInfo + "]";
	}

	
	
	

}
