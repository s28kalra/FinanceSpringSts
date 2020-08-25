package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import com.lti.dto.StatisticsDate;
import com.lti.model.Admin;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.Product;
import com.lti.status.Statistics;

public interface AdminServiceInterface {
	
	int addNewAdmin(Admin admin);
	Admin updateAdmin(Admin admin);
	Admin findAdminById(int adminId);
	Product addProduct(Product product);
	EmiCard validateCustomerAndIssueEmiCard(int customerId);
	CustomerInfo deactivateACustomer(int customerId);
	CustomerInfo activateExistingCustomerEmiCard(int customerId);
	CustomerInfo rejectACustomer(int customerId);
	List<CustomerInfo> viewAllCustomers();
	List<CustomerInfo> viewAllCustomerPendingForVerfication();	
	boolean generateBill();
	long calculateTotalNumberOfRegistrationsBetween(LocalDate from, LocalDate to);
	double calculateJoiningFeesBetween(LocalDate from, LocalDate to);
	double calculateProcessingFeesBetween(LocalDate from, LocalDate to);
	double calculateProfitBetween(LocalDate from, LocalDate to);
	List<Product> getAllProducts();
	Admin loginAdmin(int adminId, String adminPassword);
	Statistics calculateStatistics(StatisticsDate statisticsDate);
}
