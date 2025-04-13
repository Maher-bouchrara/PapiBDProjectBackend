package com.example.cFormation.services;

import com.example.cFormation.dto.CertificateBatchRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class certifService {

    public static void generateCertificatesForParticipants(CertificateBatchRequest request) throws Exception {
        String desktop = System.getProperty("user.home") + "/Desktop/certificats";
        File folder = new File(desktop);
        if (!folder.exists()) folder.mkdirs();

        List<String> participants = request.getParticipants();
        String certTitle = request.getCertTitle();
        String date = request.getDate();
        
        // Chemins vers les images
        String signatureImagePath = "src/main/resources/images/signature.png";
        String logoImagePath = "src/main/resources/images/logoCF.png";

        for (String name : participants) {
            String filename = desktop + "/" + name.replaceAll(" ", "_") + "_certificat.pdf";
            Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            
            // Add colored border
            PdfContentByte canvas = writer.getDirectContentUnder();
            
            // Outer border (orange)
            canvas.setColorFill(new BaseColor(255, 159, 64)); // Light orange
            canvas.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
            canvas.fill();
            
            // Inner border (red/orange)
            canvas.setColorFill(new BaseColor(231, 76, 60)); // Red-orange
            canvas.rectangle(30, 30, document.getPageSize().getWidth() - 60, document.getPageSize().getHeight() - 60);
            canvas.fill();
            
            // White certificate background
            canvas.setColorFill(BaseColor.WHITE);
            canvas.rectangle(50, 50, document.getPageSize().getWidth() - 100, document.getPageSize().getHeight() - 100);
            canvas.fill();
            
            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 36, Font.BOLD, BaseColor.BLACK);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, BaseColor.BLACK);
            Font nameFont = new Font(Font.FontFamily.HELVETICA, 42, Font.BOLD, BaseColor.BLACK);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, BaseColor.BLACK);
            Font signatureFont = new Font(Font.FontFamily.HELVETICA, 14, Font.ITALIC, BaseColor.BLACK);
            
            // Title
            Paragraph title = new Paragraph("CERTIFICATE OF ACHIEVEMENT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(80);
            title.setSpacingAfter(60);
            document.add(title);
            
            // This acknowledges that
            Paragraph subtitle = new Paragraph("This acknowledges that", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(30);
            document.add(subtitle);
            
            // Name
            Paragraph namePara = new Paragraph(name.toUpperCase(), nameFont);
            namePara.setAlignment(Element.ALIGN_CENTER);
            namePara.setSpacingAfter(30);
            document.add(namePara);
            
            // Completion text
            Paragraph completionText = new Paragraph("has successfully completed " + certTitle, contentFont);
            completionText.setAlignment(Element.ALIGN_CENTER);
            completionText.setSpacingAfter(60);
            document.add(completionText);
            
            // Signature line and date
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            
            // Signature Cell avec l'image
            PdfPCell signatureCell = new PdfPCell();
            
            // Vérifier si le fichier de signature existe
            File signatureFile = new File(signatureImagePath);
            if (signatureFile.exists()) {
                // Chargement de l'image de signature
                Image signatureImage = Image.getInstance(signatureImagePath);
                // Redimensionner l'image si nécessaire (largeur max 150)
                if (signatureImage.getWidth() > 150) {
                    float ratio = signatureImage.getWidth() / 150f;
                    signatureImage.scaleAbsolute(150, signatureImage.getHeight() / ratio);
                }
                
                // Ajouter l'image de signature
                Paragraph signParag = new Paragraph();
                signParag.add(new Chunk(signatureImage, 0, 0, true));
                signParag.add(Chunk.NEWLINE);
                signParag.add(new Chunk("____________________________\n", new Font(Font.FontFamily.HELVETICA, 12)));
                signParag.add(new Chunk("Imen Miladi, CEO of CFM", signatureFont));
                signatureCell.addElement(signParag);
            } else {
                // Fallback si l'image n'existe pas
                Phrase signaturePhrase = new Phrase();
                signaturePhrase.add(new Chunk("\n\n\n"));
                signaturePhrase.add(new Chunk("____________________________\n", new Font(Font.FontFamily.HELVETICA, 12)));
                signaturePhrase.add(new Chunk("Imen Miladi, CEO of CFM", signatureFont));
                signatureCell.addElement(new Paragraph(signaturePhrase));
            }
            
            signatureCell.setBorder(Rectangle.NO_BORDER);
            signatureCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(signatureCell);
            
            // Date Cell avec logo
            PdfPCell dateCell = new PdfPCell();
            
            // Vérifier si le fichier logo existe
            File logoFile = new File(logoImagePath);
            if (logoFile.exists()) {
                // Chargement de l'image du logo
                Image logoImage = Image.getInstance(logoImagePath);
                // Redimensionner l'image si nécessaire (largeur max 100)
                if (logoImage.getWidth() > 60) {
                    float ratio = logoImage.getWidth() / 60f;
                    logoImage.scaleAbsolute(60, logoImage.getHeight() / ratio);
                }
                
                // Ajouter le logo
                Paragraph dateParagraph = new Paragraph();
                dateParagraph.setAlignment(Element.ALIGN_RIGHT);
                
                // Création d'un chunk avec l'image et ajout au paragraphe
                Chunk logoChunk = new Chunk(logoImage, 0, 0, true);
                dateParagraph.add(logoChunk);
                dateParagraph.add(Chunk.NEWLINE);
                dateParagraph.add(Chunk.NEWLINE);
                dateParagraph.add(new Chunk("____________________________\n", new Font(Font.FontFamily.HELVETICA, 12)));
                dateParagraph.add(new Chunk(date, signatureFont));
                dateParagraph.add(Chunk.NEWLINE);
                dateParagraph.add(new Chunk("Date", signatureFont));
                
                dateCell.addElement(dateParagraph);
            } else {
                // Fallback si l'image n'existe pas
                Phrase datePhrase = new Phrase();
                datePhrase.add(new Chunk("\n\n\n"));
                datePhrase.add(new Chunk("____________________________\n", new Font(Font.FontFamily.HELVETICA, 12)));
                datePhrase.add(new Chunk(date, signatureFont));
                datePhrase.add(new Chunk("\nDate", signatureFont));
                dateCell.addElement(new Paragraph(datePhrase));
            }
            
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(dateCell);
            
            document.add(table);
            
            document.close();
        }
    }
}