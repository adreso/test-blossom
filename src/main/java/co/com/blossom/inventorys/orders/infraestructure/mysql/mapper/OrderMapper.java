package co.com.blossom.inventorys.orders.infraestructure.mysql.mapper;

import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.domain.model.OrderDetailDTO;
import co.com.blossom.inventorys.orders.infraestructure.mysql.model.OrderDetailEntity;
import co.com.blossom.inventorys.orders.infraestructure.mysql.model.OrderEntity;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {


	@Mapping(target = "detail", source = "detail", qualifiedByName = "detailOrderDTO")
	OrderDTO mapEntityToModel(OrderEntity orderEntity);

	@Mapping(target = "detail", source = "detail", qualifiedByName = "detailOrderEntity")
	OrderEntity mapModelToEntity(OrderDTO orderDTO);

	OrderDetailDTO mapDetailEntityToDetailDTO(OrderDetailEntity orderDetailEntity);

	OrderDetailEntity mapDetailDTOToDetailEntity(OrderDetailDTO orderDetailDTO);

	@Named("detailOrderEntity")
	default List<OrderDetailEntity> getDetailOrderEntity(List<OrderDetailDTO> detail) {
		if (Objects.isNull(detail)) return null;
		return detail.stream().map(this::mapDetailDTOToDetailEntity).toList();
	}

	@Named("detailOrderDTO")
	default List<OrderDetailDTO> getDetailOrderDTO(List<OrderDetailEntity> detail) {
		if (Objects.isNull(detail)) return null;
		return detail.stream().map(this::mapDetailEntityToDetailDTO).toList();
	}

}
