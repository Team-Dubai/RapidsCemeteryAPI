package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.MailRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.MailService;
/*
MailController
*/
@RestController
@RequestMapping(value = "/api/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

	@Autowired
	private MailService mailService;
	
	//sendContactMail POST method that will send mail to users
	@RequestMapping(value = "/sendContactMail", method = RequestMethod.POST, produces = { "application/json" })
	public Boolean sendContactMail(@RequestBody MailRequest mailRequest, HttpServletResponse response) {
		try {
			mailService.sendMail(mailRequest.getFromMail(), "bep4144@rit.edu", "Rapids Cemetery",
					mailRequest.getMessage(), false);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
