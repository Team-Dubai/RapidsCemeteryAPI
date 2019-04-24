package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.security.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	@Value("${mail.smtp.host}")
	private String host;

	@Value("${mail.smtp.port:25}")
	private int port;

	@Value("${mail.from}")
	private String from;

	@Value("${mail.username:}")
	private String username;

	@Value("${mail.password:}")
	private String password;

	@Value("${mail.authentication:false}")
	private String authentication;

	@Value("${mail.from.exceptions}")
	private String mailFromExceptions;

	@Value("${mail.to.exceptions}")
	private String mailToExceptions;

	@Value("${mail.exceptionSubject}")
	private String mailExceptionSubject;

	@SuppressWarnings("unchecked")
	public void sendMail(String from, String to, String subject, String message, final boolean html)
			throws MailException, MessagingException {
		log.debug("MailServiceImpl, sendMail start");
		JavaMailSender mailSender = setupMailSender();

		log.debug("MailServiceImpl, to={}, subject={}, mail={}, host={}, port={}",
				new Object[] { to, subject, message, host, port });

		final MimeMessage mailMessage = createMessage(mailSender, from, to, subject, message, html,
				Collections.EMPTY_LIST);

		mailSender.send(mailMessage);

		log.debug("MailServiceImpl, sendMail end succesfully");
	}

	public void sendExceptionMail(String exception) {
		try {
			StringBuilder captionSB = new StringBuilder();
			captionSB.append(mailExceptionSubject);
			captionSB.append(" (user: ");
			captionSB.append(SpringSecurityUtil.getCurrentUsername());

			sendMail(mailFromExceptions, mailToExceptions, captionSB.toString(), exception, true);
		} catch (Exception ex) {
			log.error("Error occurred while sending exception e-mail!", ex);
		}
	}

	private MimeMessage createMessage(final JavaMailSender mailSender, final String emailFrom, final String to,
			final String subject, final String message, boolean html, final List<File> files)
			throws MessagingException {
		final MimeMessage mailMesssage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMesssage, true);

		helper.setFrom(emailFrom != null ? emailFrom : from);
		String[] receivers = to.split(",");

		helper.setTo(receivers);
		helper.setSubject(subject);
		helper.setText(message, html);

		if (!CollectionUtils.isEmpty(files)) {
			for (File file : files) {
				FileSystemResource resource = new FileSystemResource(file);
				helper.addAttachment(resource.getFilename(), resource);
			}
		}

		return mailMesssage;
	}

	private JavaMailSender setupMailSender() {
		final JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(port);
		sender.setDefaultEncoding("UTF-8");
		if (StringUtils.hasText(username)) {
			sender.setUsername(username);
		}
		if (StringUtils.hasText(password)) {
			sender.setPassword(password);
		}

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", authentication);
		sender.setJavaMailProperties(javaMailProperties);

		return sender;
	}

}
