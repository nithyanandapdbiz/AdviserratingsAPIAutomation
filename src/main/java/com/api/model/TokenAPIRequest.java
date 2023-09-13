package com.api.model;

public class TokenAPIRequest {
	private String apiKey;
    private String apiSecret;
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getApiSecret() {
        return apiSecret;
    }
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}