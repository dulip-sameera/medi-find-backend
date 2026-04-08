package dev.dulipsameera.medicine_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.dulipsameera.medicine_service.model.Medicine;
import dev.dulipsameera.medicine_service.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public Optional<Medicine> getByRegNo(String regNo) {
        return medicineRepository.findByRegNo(regNo);
    }

    public List<Medicine> searchByBrandName(String brandName) {
        return medicineRepository.findByBrandNameContainingIgnoreCase(brandName);
    }

    public List<Medicine> searchByGenericName(String genericName) {
        return medicineRepository.findByGenericNameContainingIgnoreCase(genericName);
    }

    // Priority Search: RegNo -> Brand -> Generic
    public List<Medicine> searchByNamePriority(String query) {
        // 1. Check if it's a Reg No first
        Optional<Medicine> byRegNo = medicineRepository.findByRegNo(query);
        if (byRegNo.isPresent()) {
            return Collections.singletonList(byRegNo.get());
        }

        // 2. Check Brand Name
        List<Medicine> brandResults = medicineRepository.findByBrandNameContainingIgnoreCase(query);
        if (!brandResults.isEmpty()) {
            return brandResults;
        }

        // 3. Fallback to Generic Name
        return medicineRepository.findByGenericNameContainingIgnoreCase(query);
    }
}