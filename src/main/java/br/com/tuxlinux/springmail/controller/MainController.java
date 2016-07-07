package br.com.tuxlinux.springmail.controller;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.thymeleaf.util.ClassLoaderUtils;

@Controller
public class MainController {

	private static final String EDITABLE_TEMPLATE = "mail/email-editable.html";

	/* Home page. */
	@RequestMapping(value = { "/", "/index.html" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	/* Plain text email. */
	@RequestMapping(value = "/text.html", method = RequestMethod.GET)
	public String text() {
		return "text";
	}

	/* Simple HTML email. */
	@RequestMapping(value = "/simple.html", method = RequestMethod.GET)
	public String simple() {
		return "simple";
	}

	/* HTML email with attachment. */
	@RequestMapping(value = "/attachment.html", method = RequestMethod.GET)
	public String attachment() {
		return "attachment";
	}

	/* HTML email with inline image. */
	@RequestMapping(value = "/inline.html", method = RequestMethod.GET)
	public String inline() {
		return "inline";
	}

	/* Editable HTML email. */
	@RequestMapping(value = "/editable.html", method = RequestMethod.GET)
	public String editable(Model model) throws IOException {
		final ClassLoader classLoader = ClassLoaderUtils.getClassLoader(MainController.class);
		InputStream inputStream = classLoader.getResourceAsStream(EDITABLE_TEMPLATE);
		String baseTemplate = IOUtils.toString(inputStream, "UTF-8");
		model.addAttribute("baseTemplate", baseTemplate);
		return "editable";
	}

	/* Sending confirmation page. */
	@RequestMapping(value = "/sent.html", method = RequestMethod.GET)
	public String sent() {
		return "sent";
	}

}
