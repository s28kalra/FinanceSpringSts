package com.lti.status;

import java.util.List;

import com.lti.dto.EmiTransactionDto;
import com.lti.enums.StatusType;
import com.lti.model.EmiTransaction;

public class ViewCardTransactionsStatus {

	private StatusType status;
	private String message;
	private List<EmiTransactionDto> transactions;

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<EmiTransactionDto> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<EmiTransactionDto> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "ViewCardTransactionsStatus [status=" + status + ", message=" + message + ", transactions="
				+ transactions + "]";
	}

}
