package com.mobileshop.controller;

import com.mobileshop.dto.InvoiceRequestDTO;
import com.mobileshop.dto.InvoiceResponseDTO;
import com.mobileshop.entity.Invoice;
import com.mobileshop.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(
            @Valid @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invoiceService.generateInvoice(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(
            @PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<FileSystemResource> downloadPdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceEntityById(id);
        FileSystemResource resource = new FileSystemResource(invoice.getPdfPath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + invoice.getInvoiceNumber() + ".pdf")
                .body(resource);
    }

}
