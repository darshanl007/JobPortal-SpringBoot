package org.dars.job_portal.service;

import java.util.Random;

import org.dars.job_portal.dto.Recruiter;
import org.dars.job_portal.helper.MyEmailSender;
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

	@Autowired
	MyEmailSender emailSender;

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
			recruiter.setOtp(new Random().nextInt(1000, 10000));
			recruiter.setVerified(false);
			recruiterRepository.save(recruiter);
			System.err.println(recruiter.getOtp());
			emailSender.sendOtp(recruiter);
			session.setAttribute("success", "OTP Sent Success");
			return "redirect:/recruiter/otp/" + recruiter.getId();
		}
	}

	public String otp(int id, int otp, HttpSession session) {
		Recruiter recruiter = recruiterRepository.findById(id).orElseThrow();
		if (recruiter.getOtp() == otp) {
			recruiter.setVerified(true);
			recruiter.setPassword(recruiter.getPassword());
			recruiterRepository.save(recruiter);
			session.setAttribute("success", "Account Created Success");
			return "redirect:/";
		} else {
			session.setAttribute("failure", "OTP Missmatch");
			return "redirect:/recruiter/otp/" + recruiter.getId();
		}
	}

	public String resendOtp(Integer id, HttpSession session) {
		Recruiter recruiter = recruiterRepository.findById(id).orElseThrow();
		recruiter.setOtp(new Random().nextInt(1000, 10000));
		recruiter.setVerified(false);
		recruiterRepository.save(recruiter);
		System.err.println(recruiter.getOtp());
		emailSender.sendOtp(recruiter);
		session.setAttribute("success", "OTP Resent Success");
		return "redirect:/recruiter/otp/" + recruiter.getId();
	}

}