package org.dars.job_portal.controller;

import org.dars.job_portal.dto.JobSeeker;
import org.dars.job_portal.service.JobSeekerService;
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
@RequestMapping("/jobseeker")
public class JobSeekerController {

	@Autowired
	JobSeekerService seekerService;

	@GetMapping("/register")
	public String loadRegister(JobSeeker jobSeeker, ModelMap map) {
		return seekerService.register(jobSeeker, map);
	}

	@PostMapping("/register")
	public String register(@Valid JobSeeker jobSeeker, BindingResult result, HttpSession session) {
		return seekerService.register(jobSeeker, result, session);
	}

	@GetMapping("/otp/{id}")
	public String otp(@PathVariable("id") Integer id, ModelMap map) {
		map.put("id", id);
		return "seeker-otp.html";
	}

	@PostMapping("/otp")
	public String otp(@RequestParam("otp") int otp, @RequestParam("id") int id, HttpSession session) {
		return seekerService.otp(otp, id, session);
	}

	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable Integer id, HttpSession session) {
		return seekerService.resendOtp(id, session);
	}

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		if (session.getAttribute("jobSeeker") != null) {
			return "jobseeker-home.html";
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

}
