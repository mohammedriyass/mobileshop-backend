package com.mobileshop.service;

import com.mobileshop.dto.MobileRequestDTO;
import com.mobileshop.dto.MobileResponseDTO;
import com.mobileshop.entity.Mobile;
import com.mobileshop.entity.Seller;
import com.mobileshop.repository.MobileRepository;
import com.mobileshop.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileService {

    @Autowired
    private MobileRepository mobileRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public MobileResponseDTO addMobile(MobileRequestDTO dto) {

        if (mobileRepository.existsByImeiNumber1(dto.getImeiNumber1())) {
            throw new RuntimeException("Mobile with IMEI Number 1 already exists");
        }

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found!"));

        Mobile mobile = new Mobile();
        mobile.setBrand(dto.getBrand());
        mobile.setModel(dto.getModel());
        mobile.setColor(dto.getColor());
        mobile.setStorage(dto.getStorage());
        mobile.setRam(dto.getRam());
        mobile.setCondition(dto.getCondition());
        mobile.setBuyingPrice(dto.getBuyingPrice());
        mobile.setSellingPrice(dto.getSellingPrice());
        mobile.setImeiNumber1(dto.getImeiNumber1());
        mobile.setImeiNumber2(dto.getImeiNumber2());
        mobile.setImageUrl(dto.getImageUrl());
        mobile.setDescription(dto.getDescription());
        mobile.setSeller(seller);

        Mobile saved = mobileRepository.save(mobile);
        return mapToResponse(saved);
    }

    public List<MobileResponseDTO> getAllMobiles() {
        return mobileRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MobileResponseDTO> getAvailableMobiles() {
        return mobileRepository.findByStatusOrderByCreatedAtDesc("AVAILABLE").stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MobileResponseDTO getMobileById(Long id) {
        Mobile mobile = mobileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobile not found!"));
        return mapToResponse(mobile);
    }

    public MobileResponseDTO updateMobile(Long id, MobileRequestDTO dto) {

        Mobile mobile = mobileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobile not found!"));
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found!"));

        mobile.setBrand(dto.getBrand());
        mobile.setModel(dto.getModel());
        mobile.setColor(dto.getColor());
        mobile.setStorage(dto.getStorage());
        mobile.setRam(dto.getRam());
        mobile.setCondition(dto.getCondition());
        mobile.setBuyingPrice(dto.getBuyingPrice());
        mobile.setSellingPrice(dto.getSellingPrice());
        mobile.setImeiNumber1(dto.getImeiNumber1());
        mobile.setImeiNumber2(dto.getImeiNumber2());
        mobile.setImageUrl(dto.getImageUrl());
        mobile.setDescription(dto.getDescription());
        mobile.setSeller(seller);

        Mobile updated = mobileRepository.save(mobile);
        return mapToResponse(updated);
    }

    public void deleteMobile(Long id) {
        if (!mobileRepository.existsById(id)) {
            throw new RuntimeException("Mobile not found!");
        }
        mobileRepository.deleteById(id);
    }

    public List<MobileResponseDTO> searchMobiles(String keyword) {
        return mobileRepository
                .findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    private MobileResponseDTO mapToResponse(Mobile mobile) {
        MobileResponseDTO dto = new MobileResponseDTO();
        dto.setId(mobile.getId());
        dto.setBrand(mobile.getBrand());
        dto.setModel(mobile.getModel());
        dto.setColor(mobile.getColor());
        dto.setStorage(mobile.getStorage());
        dto.setRam(mobile.getRam());
        dto.setCondition(mobile.getCondition());
        dto.setBuyingPrice(mobile.getBuyingPrice());
        dto.setSellingPrice(mobile.getSellingPrice());
        dto.setProfit(mobile.getProfit());
        dto.setImeiNumber1(mobile.getImeiNumber1());
        dto.setImeiNumber2(mobile.getImeiNumber2());
        dto.setImageUrl(mobile.getImageUrl());
        dto.setDescription(mobile.getDescription());
        dto.setStatus(mobile.getStatus());
        dto.setSellerId(mobile.getSeller().getId());
        dto.setCreatedAt(mobile.getCreatedAt());
        return dto;
    }

}
