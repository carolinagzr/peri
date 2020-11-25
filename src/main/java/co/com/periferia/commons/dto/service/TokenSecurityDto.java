package co.com.periferia.commons.dto.service;

import lombok.Data;

@Data
public class TokenSecurityDto {
	private String timestamp;
	private String email;
	private String user;
	private String role;
}
