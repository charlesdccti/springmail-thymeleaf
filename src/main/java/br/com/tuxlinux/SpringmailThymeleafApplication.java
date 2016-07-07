package br.com.tuxlinux;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class SpringmailThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmailThymeleafApplication.class, args);
	}
	
	// Bean utilizado para definir o idioma
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    // getDefault obtem o idioma atual da JVM
	    slr.setDefaultLocale(Locale.getDefault());
	    return slr;
	}
}
