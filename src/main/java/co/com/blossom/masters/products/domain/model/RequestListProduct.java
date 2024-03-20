package co.com.blossom.masters.products.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Getter
@Setter
public class RequestListProduct {
    String name;
    String category;
    BigDecimal minorPrice;
    BigDecimal majorPrice;
}
