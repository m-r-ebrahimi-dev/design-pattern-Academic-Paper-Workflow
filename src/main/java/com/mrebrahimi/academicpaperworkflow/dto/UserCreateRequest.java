package com.mrebrahimi.academicpaperworkflow.dto;

import com.mrebrahimi.academicpaperworkflow.domain.Role;

public record UserCreateRequest(String username, String fullName, Role role) {
}