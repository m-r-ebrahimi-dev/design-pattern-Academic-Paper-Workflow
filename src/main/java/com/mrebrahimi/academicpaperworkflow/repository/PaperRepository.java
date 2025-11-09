package com.mrebrahimi.academicpaperworkflow.repository;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
}