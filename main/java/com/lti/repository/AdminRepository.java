package com.lti.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.model.Admin;
import com.lti.model.CustomerInfo;
import com.lti.model.EmiCard;
import com.lti.model.Product;

@Repository
public class AdminRepository implements AdminRepositoryInterface {

	@Autowired // an example of field injection but here field is default access modifier, it
				// can be private also
	CustomerRepositoryInterface customerRepo; // no issues with private as well

	@PersistenceContext
	EntityManager em;

	@Transactional
	public int addNewAdmin(Admin admin) {
		try {
			admin = em.merge(admin);
			return admin.getAdminId();
		} catch (DataAccessException e) {
//			e.printStackTrace();
//			System.out.println("Some Exception Occur");

		}
		return 0;
	}

	@Transactional
	public Admin updateAdmin(Admin admin) {
//		System.out.println(admin);
		try {
			admin = em.merge(admin);
			return admin;
		} catch (DataAccessException e) {
//			e.printStackTrace();
//			System.out.println("Some Exception Occur in updation ");
		}
		return null;
	}

	public Admin findAdminById(int adminId) {
		return em.find(Admin.class, adminId);
	}

	@Transactional
	public Product addProduct(Product product) {
		try {
			product = em.merge(product);
			return product;
		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Some Exception Occur");
		}
		return null;
	}

	public List<Product> getAllProducts() {
		try {
			Query query = em.createQuery("select p from Product p", Product.class);
			return query.getResultList();
		} catch (DataAccessException e) {
//			e.printStackTrace();
		}
		return null;
	}

	public List<CustomerInfo> viewAllCustomers() {
		try {
			Query query = em.createQuery("select c from CustomerInfo c where c.isValidCustomer!=:v order by c.registrationDate",
					CustomerInfo.class);
			query.setParameter("v", 0);
			return query.getResultList();
		} catch (DataAccessException e) {
//			e.printStackTrace();
		}
		return null;
	}

	public List<CustomerInfo> viewAllCustomerPendingForVerfication() {
		try {
			String sql = "select c from CustomerInfo c where c.isValidCustomer=:a1 order by c.registrationDate";
			TypedQuery<CustomerInfo> query = (TypedQuery<CustomerInfo>) em.createQuery(sql);
			query.setParameter("a1", 0);
			return query.getResultList();
		} catch (DataAccessException e) {
//			e.printStackTrace();
		}
		return null;
	}

	public EmiCard findEmiCardByCardNumber(int cardNumber) {
		return em.find(EmiCard.class, cardNumber);
	}

	@Transactional
	public EmiCard updateEmiCard(EmiCard emiCard) {
		try {
			emiCard = em.merge(emiCard);
			return emiCard;
		} catch (DataAccessException e) {
//			e.printStackTrace();
//			System.out.println("Some Exception Occur in updation ");

		}
		return null;
	}

// this function is a triggered function which will execute on every first date of the month
	// to generate the bill of all customers
	@Transactional
	public boolean generateBill() {
		try {
			String sql = "update EmiCard e set e.amountToBePaid=e.amountToBePaid*1.02 + e.emiPerMonth where e.cardStatus=:t";
			Query query = em.createQuery(sql);
			query.setParameter("t", true);
			int noOfRowsAffected = query.executeUpdate();
			if (noOfRowsAffected > 0)
				return true;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Long calculateTotalNumberOfRegistrationsBetween(LocalDate from, LocalDate to) {
		try {
			String sql="select count(c) from CustomerInfo c where c.registrationDate between :from and :to";
			Query query= em.createQuery(sql);
			query.setParameter("from", from);
			query.setParameter("to", to);
			return (long) query.getSingleResult();
		} catch (DataAccessException e) {
//			e.printStackTrace();
		}
		return (long) 0;
	}

	@Override
	public Double calculateJoiningFeesBetween(LocalDate from, LocalDate to) {
		try {
			String sql="select sum(card.joiningFees) from EmiCard card where card.joiningFeesDate between :from and :to";
			Query query = em.createQuery(sql);
			query.setParameter("from", from);
			query.setParameter("to", to);
			return (double) query.getSingleResult();			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return (double) 0;		
	}

	@Override
	public Double calculateProcessingFeesBetween(LocalDate from, LocalDate to) {
		try {
			String sql="select sum(trans.processingFee) from EmiTransaction trans where trans.transactionDate between : from and :to";
			Query query = em.createQuery(sql);
			query.setParameter("from", from);
			query.setParameter("to", to);
			return (double) query.getSingleResult();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return (double) 0;
	}

	@Override
	public List<EmiCard> getAllEmiCards() {
		try {
			Query query = em.createQuery("select e from EmiCard e where e.cardStatus=:t",
					EmiCard.class);
			query.setParameter("t", true);
			return query.getResultList();
		} catch (DataAccessException e) {
//			e.printStackTrace();
		}
		return null;
	}

}
