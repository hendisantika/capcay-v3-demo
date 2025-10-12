package id.my.hendisantika.capcay_v3demo7.service;

import id.my.hendisantika.capcay_v3demo7.dto.RecaptchaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : capcay_v3-demo7
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/20
 * Time: 08.38
 */

@Service
@Slf4j
public class RecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final double MINIMUM_SCORE = 0.5;
    private final RestTemplate restTemplate;
    @Value("${recaptcha.secret-key}")
    private String secretKey;
    @Value("${recaptcha.enabled:true}")
    private boolean recaptchaEnabled;

    public RecaptchaService() {
        this.restTemplate = new RestTemplate();
    }

    public boolean verifyRecaptcha(String token) {
        if (!recaptchaEnabled) {
            log.info("reCAPTCHA verification is disabled");
            return true;
        }

        if (token == null || token.isEmpty()) {
            log.warn("reCAPTCHA token is empty");
            return false;
        }

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("secret", secretKey);
            params.add("response", token);

            ResponseEntity<RecaptchaResponse> response = restTemplate.postForEntity(
                    RECAPTCHA_VERIFY_URL, params, RecaptchaResponse.class);

            RecaptchaResponse recaptchaResponse = response.getBody();

            if (recaptchaResponse == null) {
                log.error("reCAPTCHA response is null");
                return false;
            }

            log.info("reCAPTCHA verification result: success={}, score={}",
                    recaptchaResponse.isSuccess(), recaptchaResponse.getScore());

            return recaptchaResponse.isSuccess() && recaptchaResponse.getScore() >= MINIMUM_SCORE;

        } catch (Exception e) {
            log.error("Error verifying reCAPTCHA: {}", e.getMessage());
            return false;
        }
    }
}