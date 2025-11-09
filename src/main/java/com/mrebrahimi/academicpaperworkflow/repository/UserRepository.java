package com.mrebrahimi.academicpaperworkflow.repository;

import com.mrebrahimi.academicpaperworkflow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}