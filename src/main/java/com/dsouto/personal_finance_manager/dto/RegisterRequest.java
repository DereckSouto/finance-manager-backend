package com.dsouto.personal_finance_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
}
