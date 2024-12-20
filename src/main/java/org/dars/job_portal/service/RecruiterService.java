package org.dars.job_portal.service;

import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.repository.JobSeekerRepository;
import org.dars.job_portal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class RecruiterService {

	@Autowired
	JobSeekerRepository seekerRepository;

	@Autowired
	RecruiterRepository recruiterRepository;

	public String register(@Valid Recruiter recruiter, BindingResult result, HttpSession session) {

		if (!recruiter.getPassword().equals(recruiter.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword", "* Password Maissmatch");

		if (seekerRepository.existsByEmail(recruiter.getEmail())
				|| recruiterRepository.existsByEmail(recruiter.getEmail()))
			result.rejectValue("email", "error.email", "* Email Should be Unique");

		if (seekerRepository.existsByMobile(recruiter.getMobile())
				|| recruiterRepository.existsByMobile(recruiter.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique");

		if (result.hasErrors()) {
			return "recruiter-register.html";
		}

		else {
			recruiter.setVerified(false);
			recruiterRepository.save(recruiter);
			return "home.html";
		}
	}

}
