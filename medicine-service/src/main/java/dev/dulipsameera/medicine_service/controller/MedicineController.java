package dev.dulipsameera.medicine_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.dulipsameera.medicine_service.model.Medicine;
import dev.dulipsameera.medicine_service.service.MedicineService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    /**
     * Priority Search: Tries RegNo -> Brand Name -> Generic Name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Medicine>> search(@RequestParam String query) {
        return ResponseEntity.ok(medicineService.searchByNamePriority(query));
    }

    /**
     * Exact lookup by NMRA Registration Number.
     */
    @GetMapping("/reg/{regNo}")
    public ResponseEntity<Medicine> getByRegNo(@PathVariable String regNo) {
        return medicineService.getByRegNo(regNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Specific search by Brand Name only.
     */
    @GetMapping("/brand")
    public ResponseEntity<List<Medicine>> getByBrand(@RequestParam String name) {
        List<Medicine> results = medicineService.searchByBrandName(name);
        return ResponseEntity.ok(results);
    }

    /**
     * Specific search by Generic Name only.
     * Returns all brands associated with that generic compound.
     */
    @GetMapping("/generic")
    public ResponseEntity<List<Medicine>> getByGeneric(@RequestParam String name) {
        List<Medicine> results = medicineService.searchByGenericName(name);
        return ResponseEntity.ok(results);
    }
}
