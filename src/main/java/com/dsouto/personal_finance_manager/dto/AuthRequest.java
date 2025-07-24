package com.dsouto.personal_finance_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
	private String email;
	private String password;
}
