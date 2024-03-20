package co.com.blossom.inventorys.orders.infraestructure.mysql;

import co.com.blossom.inventorys.orders.infraestructure.mysql.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("select o " +
           "from OrderEntity o " +
           "join o.user u " +
           "where u.userName = ?1 " +
           "and o.dateOrder between ?2 and ?3 " +
           "order by o.dateOrder desc")
    Page<OrderEntity> findByFilter(String username, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
}
