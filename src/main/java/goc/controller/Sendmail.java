package goc.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import goc.pojo.User;

public class Sendmail extends Thread {

	//用于给用户发送邮件的邮箱
	private String from = "maccoli@163.com";
	//邮箱的用户名
	private String username = "maccoli";
	//邮箱的密码
	private String password = "bq42779!";
	//发送邮件的服务器地址
	private String host = "smtp.163.com";
	
	private User user;
	public Sendmail(User user){
		this.user = user;
	}
	
	public void run(){
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", host);
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.auth", "true");
			Session session = Session.getInstance(prop);
			Transport ts = session.getTransport();
			ts.connect(host, username, password);
			ts.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Message createEmail(Session session,User user) throws Exception{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
	//	message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
		message.setSubject("用户注册邮件");
		
		String info = "恭喜您注册成功，您的用户名：" + user.getUsername() + ",您的密码：" + user + "，!";
		message.setContent(info, "text/html;charset=UTF-8");
	    message.saveChanges();
		return message;
	}
	
}
