package org.dars.job_portal.service;

import org.dars.job_portal.dto.JobSeeker;
import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.repository.JobSeekerRepository;
import org.dars.job_portal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class JobSeekerService {

	@Autowired
	JobSeekerRepository seekerRepository;

	@Autowired
	RecruiterRepository recruiterRepository;

	public String register(JobSeeker jobSeeker, ModelMap map) {
		map.put("jobSeeker", jobSeeker);
		return "jobseeker-register.html";
	}

	public String register(JobSeeker jobSeeker, BindingResult result, HttpSession session) {

		if (!jobSeeker.getPassword().equals(jobSeeker.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"* Password and Comfirm Password Should be Matching");

		if (seekerRepository.existsByEmail(jobSeeker.getEmail())
				|| recruiterRepository.existsByEmail(jobSeeker.getEmail()))
			result.rejectValue("email", "error.email", "* Email Should be Unique");

		if (seekerRepository.existsByMobile(jobSeeker.getMobile())
				|| recruiterRepository.existsByMobile(jobSeeker.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique");

		if (result.hasErrors())
			return "jobseeker-register.html";

		else {
			jobSeeker.setVerified(false);
			seekerRepository.save(jobSeeker);
			return "home.html";
		}
	}

}
