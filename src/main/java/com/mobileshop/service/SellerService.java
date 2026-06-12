package com.mobileshop.service;

import com.mobileshop.dto.SellerRequestDTO;
import com.mobileshop.dto.SellerResponseDTO;
import com.mobileshop.entity.Seller;
import com.mobileshop.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    // add a new seller method
    public SellerResponseDTO addSeller(SellerRequestDTO dto) {

        // Check if phone number already exists
        if (sellerRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        // create a new seller
        Seller seller = new Seller();
        seller.setName(dto.getName());
        seller.setPhone(dto.getPhone());
        seller.setAddress(dto.getAddress());
        seller.setIdProof(dto.getIdProof());
        seller.setIdProofType(dto.getIdProofType());

        // save into database
        Seller saveSeller = sellerRepository.save(seller);

        // return Response
        return mapToResponse(saveSeller);
    }

    // Get All sellers
    public List<SellerResponseDTO> getAllSellers() {
        return sellerRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get Seller by id
    public SellerResponseDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));

        return mapToResponse(seller);
    }

    // Update Seller

    public SellerResponseDTO updateSeller(Long id, SellerRequestDTO dto) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));

        seller.setName(dto.getName());
        seller.setPhone(dto.getPhone());
        seller.setAddress(dto.getAddress());
        seller.setIdProof(dto.getIdProof());
        seller.setIdProofType(dto.getIdProofType());

        Seller updatedSeller = sellerRepository.save(seller);
        return mapToResponse(updatedSeller);
    }

    // Delete Seller By id
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
        sellerRepository.delete(seller);
    }

    // Helper method - convert Entity to DTO
    private SellerResponseDTO mapToResponse(Seller seller) {
        SellerResponseDTO response = new SellerResponseDTO();
        response.setId(seller.getId());
        response.setName(seller.getName());
        response.setPhone(seller.getPhone());
        response.setAddress(seller.getAddress());
        response.setIdProof(seller.getIdProof());
        response.setIdProofType(seller.getIdProofType());
        response.setTotalPhoneSold(seller.getTotalPhoneSold());
        response.setCreatedAt(seller.getCreatedAt());
        return response;
    }
}