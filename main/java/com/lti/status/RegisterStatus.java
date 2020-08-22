package com.lti.status;

import com.lti.enums.StatusType;

public class RegisterStatus {
	
	private StatusType status;
	private String message;
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
	@Override
	public String toString() {
		return "RegisterStatus [status=" + status + ", message=" + message + "]";
	}

	
}
