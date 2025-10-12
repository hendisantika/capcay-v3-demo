package id.my.hendisantika.capcay_v3demo7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }
}