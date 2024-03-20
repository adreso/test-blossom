package co.com.blossom.masters.products.infraestructure.mysql;

import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findByCode(String code);

    @Query("select p " +
        "from ProductEntity p " +
        "where p.name like %?1% " +
            "and p.category like %?2% " +
            "and p.price between ?3 and ?4")
    Page<ProductEntity> findByFilter(String name, String category,
                                     BigDecimal priceMinor, BigDecimal priceMajor,
                                     Pageable pageable);

    List<ProductEntity> findAllByIdIn(Set<Integer> ids);
}
