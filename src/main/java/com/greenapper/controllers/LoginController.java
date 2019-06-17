package com.greenapper.controllers;

import com.greenapper.models.CampaignManager;
import com.greenapper.models.User;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@Autowired
	private SessionService sessionService;

	@GetMapping(value = {"/login"})
	public String login() {
		return "login";
	}

	@GetMapping(value = {"/login-success"})
	public String loginSuccess() {
		final User sessionUser = sessionService.getSessionUser();
		if (sessionUser instanceof CampaignManager)
			return "redirect:/campaign-manager/login-success";
		return "redirect:/home";
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}
}
