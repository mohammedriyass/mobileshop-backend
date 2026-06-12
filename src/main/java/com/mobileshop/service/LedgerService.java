package com.mobileshop.service;

import com.mobileshop.dto.LedgerRequestDTO;
import com.mobileshop.dto.LedgerResponseDTO;
import com.mobileshop.entity.Ledger;
import com.mobileshop.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public LedgerResponseDTO addEntry(LedgerRequestDTO dto) {
        Ledger ledger = new Ledger();
        ledger.setEntryType(dto.getEntryType());
        ledger.setCategory(dto.getCategory());
        ledger.setAmount(dto.getAmount());
        ledger.setDescription(dto.getDescription());
        ledger.setEntryDate(dto.getEntryDate());
        ledger.setReferenceId(dto.getReferenceId());
        ledger.setItemName(dto.getItemName());
        ledger.setQuantity(dto.getQuantity());
        ledger.setBuyingPrice(dto.getBuyingPrice());
        ledger.setSellingPrice(dto.getSellingPrice());

        Ledger saved = ledgerRepository.save(ledger);
        return mapToResponse(saved);
    }

    public List<LedgerResponseDTO> getAllEntries() {
        return ledgerRepository.findAllByOrderByEntryDateDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LedgerResponseDTO getEntryById(Long id) {
        Ledger ledger = ledgerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found!"));
        return mapToResponse(ledger);
    }

    public LedgerResponseDTO updateEntry(Long id, LedgerRequestDTO dto) {
        Ledger ledger = ledgerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found!"));

        ledger.setEntryType(dto.getEntryType());
        ledger.setCategory(dto.getCategory());
        ledger.setAmount(dto.getAmount());
        ledger.setDescription(dto.getDescription());
        ledger.setEntryDate(dto.getEntryDate());
        ledger.setReferenceId(dto.getReferenceId());
        ledger.setItemName(dto.getItemName());
        ledger.setQuantity(dto.getQuantity());
        ledger.setBuyingPrice(dto.getBuyingPrice());
        ledger.setSellingPrice(dto.getSellingPrice());

        if (dto.getSellingPrice() != null && dto.getBuyingPrice() != null) {
            var profit = dto.getSellingPrice().subtract(dto.getBuyingPrice());
            if (dto.getQuantity() != null) {
                profit = profit.multiply(java.math.BigDecimal.valueOf(dto.getQuantity()));
            }
            ledger.setProfit(profit);
        }

        Ledger updated = ledgerRepository.save(ledger);
        return mapToResponse(updated);
    }

    public void deleteEntry(Long id) {
        if (!ledgerRepository.existsById(id)) {
            throw new RuntimeException("Entry not found!");
        }
        ledgerRepository.deleteById(id);
    }

    private LedgerResponseDTO mapToResponse(Ledger ledger) {
        LedgerResponseDTO response = new LedgerResponseDTO();
        response.setId(ledger.getId());
        response.setEntryType(ledger.getEntryType());
        response.setCategory(ledger.getCategory());
        response.setAmount(ledger.getAmount());
        response.setDescription(ledger.getDescription());
        response.setEntryDate(ledger.getEntryDate());
        response.setReferenceId(ledger.getReferenceId());
        response.setItemName(ledger.getItemName());
        response.setQuantity(ledger.getQuantity());
        response.setBuyingPrice(ledger.getBuyingPrice());
        response.setSellingPrice(ledger.getSellingPrice());
        response.setProfit(ledger.getProfit());
        response.setCreatedAt(ledger.getCreatedAt());
        return response;
    }
}
