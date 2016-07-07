package br.com.tuxlinux.springmail.controller;

import java.io.IOException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.tuxlinux.springmail.service.EmailService;

@Controller
public class SendingController {

	@Autowired
	private EmailService emailService;

	/* Send plain TEXT mail */
	@RequestMapping(value = "/sendMailText", method = RequestMethod.POST)
	public String sendTextMail(@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail, final Locale locale)
			throws MessagingException {

		this.emailService.sendTextMail(recipientName, recipientEmail, locale);
		return "redirect:sent.html";

	}

	/* Send HTML mail (simple) */
	@RequestMapping(value = "/sendMailSimple", method = RequestMethod.POST)
	public String sendSimpleMail(@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail, final Locale locale)
			throws MessagingException {

		this.emailService.sendSimpleMail(recipientName, recipientEmail, locale);
		return "redirect:sent.html";

	}

	/* Send HTML mail with attachment. */
	@RequestMapping(value = "/sendMailWithAttachment", method = RequestMethod.POST)
	public String sendMailWithAttachment(@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail,
			@RequestParam("attachment") final MultipartFile attachment, final Locale locale)
			throws MessagingException, IOException {

		this.emailService.sendMailWithAttachment(recipientName, recipientEmail, attachment.getOriginalFilename(),
				attachment.getBytes(), attachment.getContentType(), locale);
		return "redirect:sent.html";

	}

	/* Send HTML mail with inline image */
	@RequestMapping(value = "/sendMailWithInlineImage", method = RequestMethod.POST)
	public String sendMailWithInline(@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail,
			@RequestParam("image") final MultipartFile image, final Locale locale)
			throws MessagingException, IOException {

		this.emailService.sendMailWithInline(recipientName, recipientEmail, image.getName(), image.getBytes(),
				image.getContentType(), locale);
		return "redirect:sent.html";

	}

	/* Send editable HTML mail */
	@RequestMapping(value = "/sendEditableMail", method = RequestMethod.POST)
	public String sendMailWithInline(@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail, @RequestParam("body") final String body,
			final Locale locale) throws MessagingException, IOException {

		this.emailService.sendEditableMail(recipientName, recipientEmail, body, locale);
		return "redirect:sent.html";

	}

}
