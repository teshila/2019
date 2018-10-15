package com.ly.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.EmailSettingDao;
import com.ly.pojo.EmailSetting;

//https://blog.csdn.net/qq_28663043/article/details/70153890
//https://www.cnblogs.com/lzxianren/p/4754168.html
//https://blog.csdn.net/mrlin6688/article/details/53496479

/**
 * 项目名称：springmvc_hibernate 类名称：EmailUtil 类描述：发送邮件工具类 创建人：Cherish
 * 
 * @version 1.0
 */

//https://www.cnblogs.com/xdp-gacl/p/4216311.html
//https://www.jb51.net/article/78647.htm

@Component
public class EmailUtil {

	private static Logger logger = Logger.getLogger(EmailUtil.class); // 获取logger实例

	@Autowired
	private EmailSettingDao emailSettingDao;

	public EmailSetting getEmailSetting(){
		EmailSetting email = emailSettingDao.getEmailSetting();;
		
		return email;
	}
	
	public Session getSession() {
		Session session = null;
		Properties props = new Properties();
		//String HOST = "smtp.qq.com";
		String HOST = this.getEmailSetting().getHost();
		
		//int PORT = 587;
		int PORT  = this.getEmailSetting().getPort();
		
		//String isAUTH = "true";
		String isAUTH = this.getEmailSetting().getIsAuth();
		//String FROM = "1136000328@qq.com";
		
		String FROM = this.getEmailSetting().getFromer();
		
	/*	final String USERNAME = "1136000328@qq.com";
		final String PASSWORD = "uyabxjofenclhdfg";*/

		final String USERNAME = FROM;
		
		final String PASSWORD = this.getEmailSetting().getPwd();
		
		// String TIMEOUT = "25000";
		String TIMEOUT = "6000";
		//String DEBUG = "true";
		String DEBUG = "false";

		// 初始化session
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth", isAUTH);
		props.put("fromer", FROM);
		props.put("username", USERNAME);
		props.put("password", PASSWORD);
		props.put("mail.smtp.timeout", TIMEOUT);
		props.put("mail.debug", DEBUG);

		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		return session;
	}

	/**
	 * 
	 * @Title sendEmail
	 * @Description 通过isHtml判断发送的邮件的内容
	 * @param to
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param isHtml
	 *            是否发送html
	 * @throws MessagingException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws EmailException
	 */
	public  void sendEmail(String to, String title, String content, boolean isHtml)
			throws FileNotFoundException, IOException, MessagingException {
		//String fromer = props.getProperty("fromer");
		String fromer = getEmailSetting().getFromer();
		if (isHtml) {
			sendHtmlEmail(fromer, to, title, content);
		} else {
			sendTextEmail(fromer, to, title, content);
		}
	}

	// 发送纯文字邮件
	public  void sendTextEmail(String from, String to, String subject, String content) {
		try {
			Message message = new MimeMessage(this.getSession());
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(content);
			message.setSentDate(new Date());
			Transport.send(message);
			logger.debug("发送成功");
		} catch (Exception e) {
			logger.debug("=======> 邮件发送异常====  > " + e.getMessage());
		}

	}

	// 发送有HTML格式邮件
	public  void sendHtmlEmail(String from, String to, String subject, String htmlConent) {
		Message message = new MimeMessage(this.getSession());
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setSentDate(new Date());
			Multipart multi = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(htmlConent, "text/html; charset=utf-8");
			multi.addBodyPart(html);
			message.setContent(multi);
			Transport.send(message);
			logger.debug("发送成功");
		} catch (Exception e) {
			logger.debug("=======> 邮件发送异常====  > " + e.getMessage());
		}

	}

	// 发送带附件的邮件
	public  void sendFileEmail(String to, String subject, String htmlConent, File attachment) {
		try {
			//Message message = new MimeMessage(session);
			Message message = new MimeMessage(getSession());
			String fromer = getEmailSetting().getFromer();
			message.setFrom(new InternetAddress(fromer));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setSentDate(new Date());
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(htmlConent, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
			// 添加附件的内容
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");
				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			message.setContent(multipart);
			Transport.send(message);
			logger.debug("发送成功");
		} catch (Exception e) {
			logger.debug("=======> 邮件发送异常====  > " + e.getMessage());

		}

	}

}
