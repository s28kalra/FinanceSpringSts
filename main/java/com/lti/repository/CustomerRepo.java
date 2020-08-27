package com.lti.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.model.Checkout;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.EmiTransaction;
import com.lti.model.Product;

@Repository
public class CustomerRepo implements CustomerRepositoryInterface {

	@PersistenceContext
	EntityManager em;

	public boolean isNewCustomerUnique(String email, String mobile, String aadhar) {
		try {
			String sql = "select count(c) from CustomerInfo c where c.customerEmail=:email or c.customerMobile=:mobile or c.customerAadharCard=:aadhar";
			Query query = em.createQuery(sql);
			query.setParameter("email", email);
			query.setParameter("mobile", mobile);
			query.setParameter("aadhar", aadhar);
			Long count = (Long) query.getSingleResult();
			if (count == 0)
				return true;
		} catch (DataAccessException e) {
			// e.printStackTrace();
		}
		return false;
	}
	
	public CustomerInfo isCustomerAvailable(String email) {
		String sql="select c from CustomerInfo c where c.customerEmail=:email ";
		Query query=em.createQuery(sql);
		query.setParameter("email", email);
		List<CustomerInfo> list=query.getResultList();
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}
	
	public int findEmailAndPassword(String email,String password) {
		String sql="select c.customerId from CustomerInfo c where c.customerEmail=:email  and c.customerPassword=:password";
		Query query = em.createQuery(sql);
		query.setParameter("email", email);
		query.setParameter("password", password);
		return (int) query.getSingleResult();
	}
	
	@Transactional
	public int addNewCustomer(CustomerInfo customerInfo) {
		try {
			customerInfo = em.merge(customerInfo);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			return 0;
		}
		return customerInfo.getCustomerId();
	}

	@Transactional
	public CustomerInfo updateCustomer(CustomerInfo customerInfo) {
		CustomerInfo c = findCustomerById(customerInfo.getCustomerId());
		customerInfo.setRegistrationDate(c.getRegistrationDate());
		try {
			customerInfo = em.merge(customerInfo);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			System.out.println("Some Exception Occur");
			return null;
		}
		return customerInfo;
	}

	public CustomerInfo findCustomerById(int customerId) {
		return em.find(CustomerInfo.class, customerId);
	}

	public Product findProductById(int productId) {
		return em.find(Product.class, productId);
	}

	@Transactional
	public int buyAProductOnEmi(EmiTransaction transaction, EmiCard emiCard) {
		try {
			transaction = em.merge(transaction);
			emiCard.setEmiPerMonth(emiCard.getEmiPerMonth() + transaction.getAmount() / transaction.getEmiTenure());
			emiCard.setCardBalance(emiCard.getCardBalance() - transaction.getAmount());
			emiCard.setAmountToBePaid(0);
			emiCard = em.merge(emiCard);
			return transaction.getTransactionId();
		} catch (DataAccessException e) {
			// TODO: handle exception
		}
		return 0;

	}

	public List<EmiTransaction> getListOfTransactionsOfEmiCard(int cardNumber) {
		try {
			String sql = "select t from EmiTransaction t where t.emiCard.cardNumber=:cardNumber";
			Query query = em.createQuery(sql);
			query.setParameter("cardNumber", cardNumber);
			return query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public List<EmiTransaction> getListOfTransactionsOfCustomer(int customerId) {
		try {
			String sql = "select t from EmiTransaction t where t.customerInfo.customerId=:customerId";
			Query query = em.createQuery(sql);
			query.setParameter("customerId", customerId);
			return query.getResultList();
		} catch (DataAccessException e) {
			 //e.printStackTrace();
		}
		return null;
	}


	public List<EmiTransaction> getListOfTransactionsOfEmiCardPayPending(int cardNumber) {
		try {
			String sql = "select t from EmiTransaction t where t.emiCard.cardNumber=:cardNumber and t.noOfEmisLeft>:none";
			Query query = em.createQuery(sql);
			query.setParameter("cardNumber", cardNumber);
			query.setParameter("none", 0);
			return query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public CustomerInfo findCustomerByEmail(String email) {
		String sql="select c from CustomerInfo c where c.customerEmail=:email";
		Query query = em.createQuery(sql);
		query.setParameter("email", email);
		List<CustomerInfo> list= query.getResultList();
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
}
