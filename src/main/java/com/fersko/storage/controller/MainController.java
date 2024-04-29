package com.fersko.storage.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String home() {
		return "redirect:/signup.html";
	}

	@GetMapping("/signup.html")
	public String signupPage() {
		return "signup";
	}

	@GetMapping("/signin.html")
	public String signinPage() {
		return "signin";
	}

	@GetMapping("/index.html")
	public String imagesPage() {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			System.out.println("User is authenticated");
			return "index";
		} else {
			System.out.println("User is not authenticated, redirecting to sign in page.");
			return "redirect:/signin.html";
		}
	}
}