package com.webapp.neo.serviceImpl;

import javax.net.ssl.*;

import com.webapp.neo.service.QuoteService;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteServiceImpl implements QuoteService {
    private final RestTemplate restTemplate;

    public QuoteServiceImpl() throws Exception {
        this.restTemplate = createRestTemplateWithDisabledSslValidation();
    }

    protected RestTemplate createRestTemplateWithDisabledSslValidation() throws Exception {
        // Create a TrustManager that trusts all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting TrustManager
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            throw new Exception("Error Creating RestTemplateWithDisabledSslValidation: " + e.getMessage(), e);
        }

        // Create a RestTemplate with a SimpleClientHttpRequestFactory
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }

    public String quote() {
        String apiUrl = "https://api.quotable.io/random";
        return restTemplate.getForObject(apiUrl, String.class);
    }
}



