package com.fersko.storage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String home() {
		return "redirect:/sign-up";
	}

	@GetMapping("/sign-up")
	public String signupPage() {
		return "signup";
	}

	@GetMapping("/sign-in")
	public String signinPage() {
		return "signin";
	}

	@GetMapping("/index")
	public String imagesPage(Authentication authentication) {
		return authentication.isAuthenticated() ? "index" : "redirect:/sign-in";
	}
}