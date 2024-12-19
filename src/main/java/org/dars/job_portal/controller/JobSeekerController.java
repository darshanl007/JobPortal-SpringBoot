package org.dars.job_portal.controller;

import org.dars.job_portal.dto.JobSeeker;
import org.dars.job_portal.service.JobSeekerService;
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

}
