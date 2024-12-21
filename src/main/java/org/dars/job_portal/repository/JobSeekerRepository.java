package org.dars.job_portal.repository;

import org.dars.job_portal.dto.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(Long mobile);

	JobSeeker findByMobile(Long mobile);

	JobSeeker findByEmail(String email);

}
