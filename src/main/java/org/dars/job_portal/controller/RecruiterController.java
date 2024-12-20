package org.dars.job_portal.controller;

import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

	@Autowired
	RecruiterService service;

	@GetMapping("/register")
	public String loadRecruiter(Recruiter recruiter, ModelMap map) {
		map.put("recruiter", recruiter);
		return "recruiter-register.html";
	}

	@PostMapping("/register")
	public String register(@Valid Recruiter recruiter, BindingResult result, HttpSession session) {
		return service.register(recruiter, result, session);
	}
}
