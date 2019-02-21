package cn.itcast.erp.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
public class MailUtil {
private JavaMailSender sender;//邮件发送类
private String from;//发件人
public void setSender(JavaMailSender sender) {
	this.sender = sender;
}
public void setFrom(String from) {
	this.from = from;
}
/**
 * 
 * @param to 接收人
 * @param subject 主题
 * @param text 邮件正文
 * @throws MessagingException
 */
public void sendMail(String to,String subject,String text)throws MessagingException{
	//创建邮件
	MimeMessage mimeMessage = sender.createMimeMessage();
	//邮件包装工具
	MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
	//发件人
	helper.setFrom(from);
	//收件人
	helper.setTo(to);
	//邮件标题
	helper.setSubject(subject);
	//邮件正文
	helper.setText(text);
	//发送邮件
	sender.send(mimeMessage);
}
}
