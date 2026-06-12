package com.mobileshop.controller;

import com.mobileshop.dto.MobileRequestDTO;
import com.mobileshop.dto.MobileResponseDTO;
import com.mobileshop.service.MobileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mobiles")
@CrossOrigin(origins = "*")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    @PostMapping
    public ResponseEntity<MobileResponseDTO> addMobile(
            @Valid @RequestBody MobileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mobileService.addMobile(dto));
    }

    @GetMapping
    public ResponseEntity<List<MobileResponseDTO>> getAllMobiles() {
        return ResponseEntity.ok(mobileService.getAllMobiles());
    }

    @GetMapping("/available")
    public ResponseEntity<List<MobileResponseDTO>> getAvailableMobiles() {
        return ResponseEntity.ok(mobileService.getAvailableMobiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileResponseDTO> getMobileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(mobileService.getMobileById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MobileResponseDTO> updateMobile(
            @PathVariable Long id,
            @Valid @RequestBody MobileRequestDTO dto) {
        return ResponseEntity.ok(mobileService.updateMobile(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMobile(@PathVariable Long id) {
        mobileService.deleteMobile(id);
        return ResponseEntity.ok("Mobile deleted successfully!");
    }

    @GetMapping("/search")
    public ResponseEntity<List<MobileResponseDTO>> searchMobiles(
            @RequestParam String keyword) {
        return ResponseEntity.ok(mobileService.searchMobiles(keyword));
    }
}
