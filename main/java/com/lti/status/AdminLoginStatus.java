package com.lti.status;

public class AdminLoginStatus extends RegisterStatus{
	private int adminId;
	private String adminName;
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	@Override
	public String toString() {
		return "AdminLoginStatus [adminId=" + adminId + ", adminName=" + adminName + "]";
	}
	
	

}
