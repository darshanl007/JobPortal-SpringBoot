package org.dars.job_portal.repository;

import java.util.List;

import org.dars.job_portal.dto.Job;
import org.dars.job_portal.dto.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {

	List<Job> findByRecruiter(Recruiter recruiter);

}
