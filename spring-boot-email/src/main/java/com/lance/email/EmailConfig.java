package com.lance.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {

	private String defaultSendFormAddress;

	public String getDefaultSendFormAddress() {
		return defaultSendFormAddress;
	}

	public void setDefaultSendFormAddress(String defaultSendFormAddress) {
		this.defaultSendFormAddress = defaultSendFormAddress;
	}
	
}
