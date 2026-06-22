package com.mobileshop.service;

import com.mobileshop.dto.InvoiceRequestDTO;
import com.mobileshop.dto.InvoiceResponseDTO;
import com.mobileshop.entity.Invoice;
import com.mobileshop.entity.Order;
import com.mobileshop.repository.InvoiceRepository;
import com.mobileshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    public InvoiceResponseDTO generateInvoice(InvoiceRequestDTO dto) {

        if (invoiceRepository.findByOrderId(dto.getOrderId()).isPresent()) {
            throw new RuntimeException("Invoice already generated for this order!");
        }

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setCustomerName(order.getCustomerName());
        invoice.setCustomerPhone(order.getCustomerPhone());
        invoice.setMobileDetails(order.getMobile().getBrand() + " " + order.getMobile().getModel() +
                " (" + order.getMobile().getStorage() + ", " + order.getMobile().getColor() + ")");

        invoice.setTotalAmount(order.getTotalAmount());

        Invoice saved = invoiceRepository.save(invoice);

        try {
            String pdfPath = pdfGeneratorService.generateInvoicePdf(saved);
            saved.setPdfPath(pdfPath);
            saved = invoiceRepository.save(saved);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF:" + e.getMessage());
        }

        return mapToResponse(saved);
    }

    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InvoiceResponseDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found!"));

        return mapToResponse(invoice);
    }

    public Invoice getInvoiceEntityById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not Found!"));

    }

    private String generateInvoiceNumber() {
        String datePart = LocalDate.now().toString().replace("-", "");
        long count = invoiceRepository.count() + 1;
        String countFormatted = String.format("%04d", count);
        return "INV-" + datePart + "-" + countFormatted;
    }

    private InvoiceResponseDTO mapToResponse(Invoice invoice) {
        InvoiceResponseDTO response = new InvoiceResponseDTO();
        response.setId(invoice.getId());
        response.setOrderId(invoice.getOrder().getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setCustomerName(invoice.getCustomerName());
        response.setCustomerPhone(invoice.getCustomerPhone());
        response.setMobileDetails(invoice.getMobileDetails());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setCreatedAt(invoice.getCreatedAt());

        return response;
    }

}
