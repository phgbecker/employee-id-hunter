package com.phgbecker.employeeidhunter.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SearchConfiguration {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.ntlm.domain}")
    private String apiNtlmDomain;

    @Value("${api.ntlm.username}")
    private String apiNtlmUsername;

    @Value("${api.ntlm.password}")
    private String apiNtlmPassword;

    @Value("${sms.service.username}")
    private String smsServiceUsername;

    @Value("${sms.service.password}")
    private String smsServicePassword;

    @Value("${sms.notification.message}")
    private String smsNotificationMessage;
}
