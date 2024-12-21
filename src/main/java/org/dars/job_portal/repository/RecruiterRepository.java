package org.dars.job_portal.repository;

import org.dars.job_portal.dto.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(Long mobile);

	Recruiter findByMobile(Long mobile);

	Recruiter findByEmail(String email);

}
