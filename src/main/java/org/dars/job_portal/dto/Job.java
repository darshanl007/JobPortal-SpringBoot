package org.dars.job_portal.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Job {
	private int id;
	private String role;
	private String description;
	private String skills;
	private String location;
	private double ctc;
	private double experience;
	private boolean approved;
	
	@ManyToOne
	private Recruiter recruiter;
}
