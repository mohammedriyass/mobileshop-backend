package com.mobileshop.controller;

import com.mobileshop.dto.LedgerRequestDTO;
import com.mobileshop.dto.LedgerResponseDTO;
import com.mobileshop.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@CrossOrigin(origins = "*")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    // POST /api/ledger → Add entry
    @PostMapping
    public ResponseEntity<LedgerResponseDTO> addEntry(
            @Valid @RequestBody LedgerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ledgerService.addEntry(dto));
    }

    // GET /api/ledger → Get all entries
    @GetMapping
    public ResponseEntity<List<LedgerResponseDTO>> getAllEntries() {
        return ResponseEntity.ok(ledgerService.getAllEntries());
    }

    // GET /api/ledger/1 → Get entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<LedgerResponseDTO> getEntryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(ledgerService.getEntryById(id));
    }

    // PUT /api/ledger/1 → Update entry
    @PutMapping("/{id}")
    public ResponseEntity<LedgerResponseDTO> updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody LedgerRequestDTO dto) {
        return ResponseEntity.ok(ledgerService.updateEntry(id, dto));
    }

    // DELETE /api/ledger/1 → Delete entry
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long id) {
        ledgerService.deleteEntry(id);
        return ResponseEntity.ok("Entry deleted successfully!");
    }
}