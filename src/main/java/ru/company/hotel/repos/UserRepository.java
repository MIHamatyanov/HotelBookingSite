package ru.company.hotel.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.hotel.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
