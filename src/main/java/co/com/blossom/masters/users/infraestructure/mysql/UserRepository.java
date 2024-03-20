package co.com.blossom.masters.users.infraestructure.mysql;

import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserName(String username);
}
