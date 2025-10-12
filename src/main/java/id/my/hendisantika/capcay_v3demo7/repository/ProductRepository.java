package id.my.hendisantika.capcay_v3demo7.repository;

import id.my.hendisantika.capcay_v3demo7.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryIgnoreCase(String category);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchProducts(@Param("keyword") String keyword);
}