package com.lti.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.transaction.Transactional;

@Entity
public class Product {
	@Id
	@SequenceGenerator(name="product_id",initialValue=5200 ,allocationSize=50)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_id")
	private int productId;
	
	@Column(nullable=false)
	private String productImageSource;
	
	@Column
	private String productName;
	
	@Column
	private double productPrice;
	
	@Column
	private String productDescription;
	
	@OneToMany(mappedBy="product")
	private List<EmiTransaction> transaction;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductImageSource() {
		return productImageSource;
	}

	public void setProductImageSource(String productImageSource) {
		this.productImageSource = productImageSource;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productImageSource=" + productImageSource + ", productName="
				+ productName + ", productPrice=" + productPrice + ", productDescription=" + productDescription + "]";
	}
	
	
}
