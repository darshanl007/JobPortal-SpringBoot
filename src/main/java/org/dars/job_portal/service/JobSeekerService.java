package org.dars.job_portal.service;

import java.util.Random;

import org.dars.job_portal.dto.JobSeeker;
import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.helper.MyEmailSender;
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

	@Autowired
	MyEmailSender emailSender;

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
			jobSeeker.setOtp(new Random().nextInt(1000, 10000));
			jobSeeker.setVerified(false);
			seekerRepository.save(jobSeeker);
			System.err.println(jobSeeker.getOtp());
			emailSender.sendOtp(jobSeeker);
			return "redirect:/jobseeker/otp/" + jobSeeker.getId();
		}
	}

	public String otp(int otp, int id) {
		JobSeeker jobSeeker = seekerRepository.findById(id).orElseThrow();
		if (jobSeeker.getOtp() == otp) {
			jobSeeker.setVerified(true);
			seekerRepository.save(jobSeeker);
			return "redirect:/";
		} else {
			return "redirect:/jobseeker/otp/" + jobSeeker.getId();
		}
	}

	public String resendOtp(Integer id) {
		JobSeeker jobSeeker = seekerRepository.findById(id).orElseThrow();
		jobSeeker.setOtp(new Random().nextInt(1000, 10000));
		jobSeeker.setVerified(false);
		seekerRepository.save(jobSeeker);
		System.err.println(jobSeeker.getOtp());
		emailSender.sendOtp(jobSeeker);
		return "redirect:/jobseeker/otp/" + jobSeeker.getId();
	}

}