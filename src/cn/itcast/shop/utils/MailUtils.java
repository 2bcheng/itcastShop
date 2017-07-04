package cn.itcast.shop.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email, String userName,String validateCode )
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "smtp.126.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("haohao_itcast", "hao12345");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("haohao_itcast@126.com")); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
		   StringBuffer sf=new StringBuffer();  
           sf.append("<a href=\"http://localhost:8080/itcastShop/user?method=active&username=");  
           sf.append(userName);  
           sf.append("&validateCode=");  
           sf.append(validateCode);  
           sf.append("\">");  
           sf.append(" <FONT   face=\"MS   UI   Gothic\"   size=\"3\"><b>点击这里</b></FONT>");  
           sf.append("</a>");  
           sf.append("激活账号，24小时生效，否则重新验证，请尽快激活！<br>");  
		
		
		message.setSubject("用户激活");
		message.setContent(sf.toString(), "text/html;charset=utf-8");
		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
