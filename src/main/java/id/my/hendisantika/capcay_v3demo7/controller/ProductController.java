package id.my.hendisantika.capcay_v3demo7.controller;

import id.my.hendisantika.capcay_v3demo7.service.ProductService;
import id.my.hendisantika.capcay_v3demo7.service.RecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : capcay_v3-demo7
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/20
 * Time: 08.38
 */

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final RecaptchaService recaptchaService;

    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;


}