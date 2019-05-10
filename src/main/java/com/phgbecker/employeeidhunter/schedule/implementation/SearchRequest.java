package com.phgbecker.employeeidhunter.schedule.implementation;

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
    private static SearchRequest searchRequestInstance;
    private final HttpClientBuilder httpClientBuilder;

    private static final String API_URL = "";
    private static final String AUTH_USERNAME = "";
    private static final String AUTH_PASSWORD = "";
    private static final String AUTH_DOMAIN = "";

    private SearchRequest() {
        Registry<AuthSchemeProvider> authSchemeRegistry = setupAuthSchemeProviderRegistry();
        BasicCredentialsProvider credentialsProvider = setupCredentialsProvider();

        httpClientBuilder = setupHttpClientBuilder(authSchemeRegistry, credentialsProvider);
    }

    public static SearchRequest getInstance() {
        if (searchRequestInstance == null) {
            searchRequestInstance = new SearchRequest();
        }

        return searchRequestInstance;
    }

    public String search(String search) throws IOException {
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
                new NTCredentials(AUTH_USERNAME, AUTH_PASSWORD, null, AUTH_DOMAIN)
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
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setConfig(RequestConfig.custom().setTargetPreferredAuthSchemes(Collections.singletonList("NTLM")).build());
        httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpPost.setEntity(new StringEntity(search, StandardCharsets.UTF_8));

        CloseableHttpClient httpclient = httpClientBuilder.build();

        return httpclient.execute(httpPost);
    }

}
