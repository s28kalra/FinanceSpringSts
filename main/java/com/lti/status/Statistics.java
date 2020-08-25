package com.lti.status;

public class Statistics {

	private long registrations;
	private double joiningFees;
	private double processingFees;
	private double totalProfit;

	public long getRegistrations() {
		return registrations;
	}

	public void setRegistrations(long registrations) {
		this.registrations = registrations;
	}

	public double getJoiningFees() {
		return joiningFees;
	}

	public void setJoiningFees(double joiningFees) {
		this.joiningFees = joiningFees;
	}

	public double getProcessingFees() {
		return processingFees;
	}

	public void setProcessingFees(double processingFees) {
		this.processingFees = processingFees;
	}

	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	@Override
	public String toString() {
		return "Statistics [registrations=" + registrations + ", joiningFees=" + joiningFees + ", processingFees="
				+ processingFees + ", totalProfit=" + totalProfit + "]";
	}

}
