package id.my.hendisantika.capcay_v3demo7.controller;

import id.my.hendisantika.capcay_v3demo7.entity.Product;
import id.my.hendisantika.capcay_v3demo7.service.ProductService;
import id.my.hendisantika.capcay_v3demo7.service.RecaptchaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @GetMapping
    public String listProducts(@RequestParam(required = false) String search, Model model) {
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProducts(search);
            model.addAttribute("search", search);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        return "products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        return "products/create";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product,
                                BindingResult result,
                                @RequestParam("g-recaptcha-response") String recaptchaToken,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (!recaptchaService.verifyRecaptcha(recaptchaToken)) {
            result.rejectValue(null, "recaptcha.invalid", "reCAPTCHA verification failed");
        }

        if (result.hasErrors()) {
            model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
            return "products/create";
        }

        try {
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
            return "redirect:/products";
        } catch (Exception e) {
            log.error("Error creating product", e);
            model.addAttribute("errorMessage", "Error creating product: " + e.getMessage());
            model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
            return "products/create";
        }
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
                    return "products/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
                    return "redirect:/products";
                });
    }
}