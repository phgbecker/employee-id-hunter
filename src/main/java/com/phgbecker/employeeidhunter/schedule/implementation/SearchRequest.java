package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.SearchConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class SearchRequest {
    private final SearchConfiguration searchConfiguration;

    public SearchRequest(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    public String search(String search) throws IOException {
        Registry<AuthSchemeProvider> authSchemeRegistry = setupAuthSchemeProviderRegistry();
        BasicCredentialsProvider credentialsProvider = setupCredentialsProvider();

        HttpClientBuilder httpClientBuilder = setupHttpClientBuilder(authSchemeRegistry, credentialsProvider);
        HttpResponse response = postSearch(search, httpClientBuilder);

        return EntityUtils.toString(response.getEntity());
    }

    private Registry<AuthSchemeProvider> setupAuthSchemeProviderRegistry() {
        return RegistryBuilder.<AuthSchemeProvider>create()
                .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                .build();
    }

    private BasicCredentialsProvider setupCredentialsProvider() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new NTCredentials(
                        searchConfiguration.getApiNtlmUsername(),
                        searchConfiguration.getApiNtlmPassword(),
                        null,
                        searchConfiguration.getApiNtlmDomain()
                )
        );

        return credentialsProvider;
    }

    private HttpClientBuilder setupHttpClientBuilder(Registry<AuthSchemeProvider> authSchemeRegistry, BasicCredentialsProvider credentialsProvider) {
        return HttpClientBuilder.create()
                .useSystemProperties()
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setDefaultCredentialsProvider(credentialsProvider);
    }

    private HttpResponse postSearch(String search, HttpClientBuilder httpClientBuilder) throws IOException {
        HttpPost httpPost = new HttpPost(searchConfiguration.getApiUrl());
        httpPost.setConfig(RequestConfig.custom().setTargetPreferredAuthSchemes(Collections.singletonList("NTLM")).build());
        httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpPost.setEntity(new StringEntity(search, StandardCharsets.UTF_8));

        CloseableHttpClient httpclient = httpClientBuilder.build();

        return httpclient.execute(httpPost);
    }

}
