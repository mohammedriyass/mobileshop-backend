package com.mobileshop.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mobileshop.entity.Invoice;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    private static final String INVOICE_DIR = "invoices/";

    public String generateInvoicePdf(Invoice invoice) throws IOException {

        // Create directory if it doesn't exist
        java.io.File dir = new java.io.File(INVOICE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = INVOICE_DIR + invoice.getInvoiceNumber() + ".pdf";

        PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        Paragraph shopName = new Paragraph("MOBILEZONE")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(shopName);

        Paragraph tagline = new Paragraph("Second Hand Mobile Shop")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY);
        document.add(tagline);

        document.add(new Paragraph("\n"));

        Paragraph invoiceTitle = new Paragraph("TAX INVOICE")
                .setFontSize(16)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(invoiceTitle);

        document.add(new Paragraph("\n"));

        Table infoTable = new Table(UnitValue.createPercentArray(new float[] { 1, 1 }))
                .useAllAvailableWidth();

        infoTable.addCell(createCell("Invoice Number:", true));
        infoTable.addCell(createCell(invoice.getInvoiceNumber(), false));

        infoTable.addCell(createCell("Date:", true));
        infoTable.addCell(createCell(invoice.getCreatedAt().toLocalDate().toString(), false));

        infoTable.addCell(createCell("Customer Name:", true));
        infoTable.addCell(createCell(invoice.getCustomerName(), false));

        infoTable.addCell(createCell("Customer Phone:", true));
        infoTable.addCell(createCell(invoice.getCustomerPhone(), false));

        document.add(infoTable);
        document.add(new Paragraph("\n"));

        Table itemTable = new Table(UnitValue.createPercentArray(new float[] { 3, 1 }))
                .useAllAvailableWidth();

        itemTable.addHeaderCell(createHeaderCell("Description"));
        itemTable.addHeaderCell(createHeaderCell("Amount (₹)"));

        itemTable.addCell(createCell(invoice.getMobileDetails(), false));
        itemTable.addCell(createCell(invoice.getTotalAmount().toString(), false));

        document.add(itemTable);
        document.add(new Paragraph("\n"));

        Paragraph total = new Paragraph("Total Amount: ₹" + invoice.getTotalAmount())
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(total);

        document.add(new Paragraph("\n\n"));

        Paragraph footer = new Paragraph("Thank you for your business!")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY);
        document.add(footer);

        document.close();

        return fileName;
    }

    private Cell createCell(String content, boolean isBold) {
        Paragraph p = new Paragraph(content);
        if (isBold) {
            p.setBold();
        }
        return new Cell().add(p).setBorder(Border.NO_BORDER);
    }

    private Cell createHeaderCell(String content) {
        return new Cell()
                .add(new Paragraph(content).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }
}