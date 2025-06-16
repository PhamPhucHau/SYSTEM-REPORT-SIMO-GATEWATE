package SHINHAN_PORTAL.REPORT_SIMO.domain.repository;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  UserRepository extends MongoRepository<User,ObjectId> {
    Optional<User> findByUsername(String username);
    Optional<String> findPasswordHashByUsername(String username);
    // Phương thức để lấy tất cả người dùng
    List<User> findAll();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findById(ObjectId id);
    void deleteById(String id);
}
