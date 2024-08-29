package team05.integrated_feed_backend.module.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String account;
	private String password;
}