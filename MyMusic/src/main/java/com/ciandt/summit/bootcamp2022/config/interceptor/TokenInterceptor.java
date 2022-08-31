package com.ciandt.summit.bootcamp2022.config.interceptor;

import com.ciandt.summit.bootcamp2022.config.interceptor.exceptions.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.dto.TokenData;
import com.ciandt.summit.bootcamp2022.dto.TokenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private static final String URL = "http://localhost:8081/api/v1/token/authorizer";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("TokenInterceptor - Pre Handle initialized");

        var name =  request.getHeader("name");
        var token =  request.getHeader("token");

        logger.info("Setting the name in the header = {}", name);
        logger.info("Setting the token in the header = {}", token);

        TokenData tokenData = new TokenData(name, token);
        TokenRequest tokenRequest = new TokenRequest(tokenData);

        HttpEntity<TokenRequest> requestEntity = new HttpEntity<>(tokenRequest);

        URI url = new URI(URL);

        RestTemplate restTemplate = new RestTemplate();
        try {
            logger.info("Request to the authentication endpoint");
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            if (responseEntity.getStatusCode().value() == 201 && responseEntity.getBody().contains("ok")){
                return true;
            }
        }catch (Exception e){
            logger.warn("Invalid request to authentication endpoint");
            throw new UnauthorizedException("Unauthorized!");
        }
        return false;
    }
}
