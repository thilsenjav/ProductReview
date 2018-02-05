package com.demo.product.review.integration;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.demo.product.review.ProductReviewApplication;


@ContextConfiguration
@SpringBootTest(classes = ProductReviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
public abstract class SpringIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringIntegrationTest.class);
	static ResponseResults latestResponse = null;
	
	@LocalServerPort
	private int port;

    @Autowired
    protected RestTemplate restTemplate;
    
    void executeGet(String url) throws IOException {
    	executeGet(url,null);
    }

    void executeGet(String url, String authToken) throws IOException {
    	LOGGER.debug("In executeGet url: " + url + " authToken: " + authToken);
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        if (!StringUtils.isEmpty(authToken)) {
        	headers.put("X-AUTH-TOKEN", authToken);
        }
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        
        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute("http://127.0.0.1:" + port + url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }
    
    void executePost(String url, String postData) throws IOException{
    	executePost(url, postData, null);
    }

    void executePost(String url, String postData, String authToken) throws IOException {
    	LOGGER.debug("In executePost url: " + url + " postData: " + postData + " authToken: " + authToken );
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "application/json");    
        if (!StringUtils.isEmpty(authToken)) {
        	headers.put("X-AUTH-TOKEN", authToken);
        }
        UUID uuid = UUID.randomUUID();
        headers.put("X-CSRF-TOKEN", uuid.toString());
        headers.put("Cookie", "CSRF-TOKEN=" + uuid.toString());
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        requestCallback.setBody(postData);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        
        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
          .execute("http://127.0.0.1:" + port + url, HttpMethod.POST, requestCallback, response -> {
              if (errorHandler.hadError) {
                  return (errorHandler.getResults());
              } else {
                  return (new ResponseResults(response));
              }
          });
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response);
        }
    }

}
