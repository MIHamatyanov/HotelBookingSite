package ru.company.hotel.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.hotel.entities.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
