package br.com.tuxlinux.springmail.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(Throwable.class)
	public ModelAndView exception(Throwable throwable) {
		String errorMessage = throwable != null ? throwable.getMessage() : "Unknown error";
		ModelAndView mav = new ModelAndView();
		mav.getModel().put("errorMessage", errorMessage);
		mav.setViewName("error");
		return mav;
	}

}
