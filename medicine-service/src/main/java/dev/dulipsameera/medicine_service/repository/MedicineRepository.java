package dev.dulipsameera.medicine_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.dulipsameera.medicine_service.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    // Find by exact Reg No (Fastest)
    Optional<Medicine> findByRegNo(String regNo);

    // Search by Generic Name (Uses idx_generic_name)
    List<Medicine> findByGenericNameContainingIgnoreCase(String genericName);

    // Search by Brand Name (Uses idx_brand_name)
    List<Medicine> findByBrandNameContainingIgnoreCase(String brandName);

}