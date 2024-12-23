package org.dars.job_portal.repository;

import org.dars.job_portal.dto.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {

}
