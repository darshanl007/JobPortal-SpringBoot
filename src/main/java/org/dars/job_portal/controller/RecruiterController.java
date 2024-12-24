package org.dars.job_portal.controller;

import org.dars.job_portal.dto.Job;
import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/otp/{id}")
	public String loadOtp(@PathVariable Integer id, ModelMap map, HttpSession session) {
		map.put("id", id);
		return "recruiter-otp.html";
	}

	@PostMapping("/otp")
	public String otp(@RequestParam("id") int id, @RequestParam("otp") int otp, HttpSession session) {
		return service.otp(id, otp, session);
	}

	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable Integer id, HttpSession session) {
		return service.resendOtp(id, session);
	}

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		if (session.getAttribute("recruiter") != null) {
			return "recruiter-home.html";
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@GetMapping("/post-job")
	public String postJob(HttpSession session) {
		return service.loadJob(session);
	}

	@PostMapping("/post-job")
	public String postJob(Job job, HttpSession session) {
		return service.loadJob(job, session);
	}

	@GetMapping("/manage-jobs")
	public String manageJob(HttpSession session, ModelMap map) {
		return service.manageJob(session, map);
	}

	@GetMapping("/delete-job/{id}")
	public String deleteJob(@PathVariable("id") int id, HttpSession session) {
		return service.deleteJob(id, session);
	}

}
