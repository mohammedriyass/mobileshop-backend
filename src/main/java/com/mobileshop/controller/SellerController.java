package com.mobileshop.controller;

import com.mobileshop.dto.SellerRequestDTO;
import com.mobileshop.dto.SellerResponseDTO;
import com.mobileshop.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin(origins = "*")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // POST /api/sellers - Add a new seller
    @PostMapping
    public ResponseEntity<SellerResponseDTO> addSeller(
            @Valid @RequestBody SellerRequestDTO dto) {
        SellerResponseDTO response = sellerService.addSeller(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // GET /api/sellers - Get all sellers
    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getAllSellers() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    // GET /api/sellers/{id} - Get seller by ID
    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> getSellerById(
            @PathVariable Long id,
            @Valid @RequestBody SellerRequestDTO dto) {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    // PUT /api/sellers/{id} - Update seller
    @PutMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> updateSeller(
            @PathVariable Long id,
            @Valid @RequestBody SellerRequestDTO dto) {
        return ResponseEntity.ok(sellerService.updateSeller(id, dto));

    }

    // DELETE /api/sellers/{id} - Delete seller
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.ok("Seller deleted successfully");
    }

}
