package br.com.tuxlinux.springmail.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

@Service
public class EmailService {
	private static final String BACKGROUND_IMAGE = "mail/images/background.png";
	private static final String LOGO_BACKGROUND_IMAGE = "mail/images/logo-background.png";
	private static final String THYMELEAF_BANNER_IMAGE = "mail/images/thymeleaf-banner.png";
	private static final String THYMELEAF_LOGO_IMAGE = "mail/images/thymeleaf-logo.png";

	private static final String PNG_MIME = "image/png";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine htmlTemplateEngine;

	@Autowired
	private TemplateEngine textTemplateEngine;

	@Autowired
	private TemplateEngine stringTemplateEngine;

	/*
	 * Send plain TEXT mail
	 */
	public void sendTextMail(final String recipientName, final String recipientEmail, final Locale locale)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject("Example plain TEXT email");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		// Create the plain TEXT body using Thymeleaf
		final String context = this.textTemplateEngine.process("email-text", ctx);
		message.setText(context);

		// Send email
		this.mailSender.send(mimeMessage);
	}

	/*
	 * Send HTML mail (simple)
	 */
	public void sendSimpleMail(final String recipientName, final String recipientEmail, final Locale locale)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject("Example HTML email (simple)");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("mail/email-simple", ctx);
		message.setText(htmlContent, true /* isHtml */);

		// Send email
		this.mailSender.send(mimeMessage);
	}

	/*
	 * Send HTML mail with attachment.
	 */
	public void sendMailWithAttachment(final String recipientName, final String recipientEmail,
			final String attachmentFileName, final byte[] attachmentBytes, final String attachmentContentType,
			final Locale locale) throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject("Example HTML email with attachment");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("email-withattachment", ctx);
		message.setText(htmlContent, true /* isHtml */);

		// Add the attachment
		final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
		message.addAttachment(attachmentFileName, attachmentSource, attachmentContentType);

		// Send mail
		this.mailSender.send(mimeMessage);
	}

	/*
	 * Send HTML mail with inline image
	 */
	public void sendMailWithInline(final String recipientName, final String recipientEmail,
			final String imageResourceName, final byte[] imageBytes, final String imageContentType, final Locale locale)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
		ctx.setVariable("imageResourceName", imageResourceName); // so that we
																	// can
																	// reference
																	// it from
																	// HTML

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject("Example HTML email with inline image");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.htmlTemplateEngine.process("email-inlineimage", ctx);
		message.setText(htmlContent, true /* isHtml */);

		// Add the inline image, referenced from the HTML code as
		// "cid:${imageResourceName}"
		final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
		message.addInline(imageResourceName, imageSource, imageContentType);

		// Send mail
		this.mailSender.send(mimeMessage);
	}

	/*
	 * Send HTML mail with inline image
	 */
	public void sendEditableMail(final String recipientName, final String recipientEmail, final String htmlContent,
			final Locale locale) throws MessagingException {

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
		message.setSubject("Example editable HTML email");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		// FIXME: duplicated images in src/main/resources and src/main/webapp
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

		// Create the HTML body using Thymeleaf
		final String output = stringTemplateEngine.process(htmlContent, ctx);
		message.setText(output, true /* isHtml */);

		// Add the inline images, referenced from the HTML code as
		// "cid:image-name"
		message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
		message.addInline("logo-background", new ClassPathResource(LOGO_BACKGROUND_IMAGE), PNG_MIME);
		message.addInline("thymeleaf-banner", new ClassPathResource(THYMELEAF_BANNER_IMAGE), PNG_MIME);
		message.addInline("thymeleaf-logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

		// Send mail
		this.mailSender.send(mimeMessage);
	}

}
