package com.lti.email;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.lti.model.CustomerInfo;
import com.lti.service.CustomerService;
import com.lti.service.CustomerServiceInterface;

@Component
public class EmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private CustomerServiceInterface customerService;
	
	private HashMap<String, String> otpMap=new HashMap<>();

	private String senderEmail = "easy_credit101@outlook.com";
	SimpleMailMessage message = new SimpleMailMessage();

	public int sendOtpEmail( String email) {
		int otp = (int) (Math.random() * 89999 + 10000);
		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("Forgot Password");
			message.setText("Please use OTP " + otp + " to reset your Password with 15 minutes\n"
					+ "Thanks for using Easy Credit");
			mailSender.send(message);
			otpMap.put(email, Integer.toString(otp));
			return otp;
		} catch (Exception e) {

		}

		return 0;
	}

	public boolean sendRegisterEmail(String customerFirstName, String email) {

		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("New Registration");
			message.setText("Thank You " + customerFirstName + "" + "\nFor Registering with Easy Credit");
			mailSender.send(message);
			return true;
		} catch (Exception e) {

		}

		return false;
	}

	public boolean validateAnOtp(String email, String otp) {
		try {
		String originalOtp=otpMap.get(email);
		if(originalOtp.equals(otp)) {
			otpMap.remove(email);
			return true;
		}
		}catch (Exception e) {

		}
		return false;
	}
	
	public boolean sendUpdateProfileEmail(String customerFirstName, String email){
		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("Profile Updation");
			message.setText("Thank You " + customerFirstName + "" + "\nYour Profile is Updated");
			mailSender.send(message);
			return true;
		} catch (Exception e) {

		}

		return false;
	}

	public boolean sendTransactionEmail(String customerFirstName, String email, String productName, int transactionId) {
		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("Transaction Complete");
			message.setText("Thank You " + customerFirstName + "" + 
			"\nYour Product "+productName+" has been succcessfully placed"
					+ "\nYour Transaction Id is "+transactionId);
			mailSender.send(message);
			return true;
		} catch (Exception e) {

		}

		return false;
	}
	
	public boolean sendEmiPayMail(String name, String email, double balance) {
		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("Emi Paid");
			message.setText("ThankYou "+name+" for paying your EMI "
					+ "\nYour remaining card Balance is "+balance+""
							+ "\nThanks For using Easy Credit");
			mailSender.send(message);
			return true;
		} catch (Exception e) {

		}

		return false;
	} 
	
	public boolean sendBillEmail(String name, String email, double amount) {
		try {
			message.setFrom(this.senderEmail);
			message.setTo(email);
			message.setSubject("Bill Generated");
			message.setText("Hey "+name+" your bill of Rs. "+amount+" has been generated for current Month"
					+ "\nPlease visit Easy Credit to pay your bill");
			mailSender.send(message);
			return true;
		} catch (Exception e) {

		}

		return false;
	}
}
