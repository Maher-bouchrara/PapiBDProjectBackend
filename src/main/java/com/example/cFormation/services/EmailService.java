package com.example.cFormation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmailAuxParticipants(String objet, String message, List<String> destinataires) {
        for (String mail : destinataires) {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

                //adresse e-mail  de l'expéditeur + changement du nom 
                helper.setFrom(new InternetAddress("imenmiladi21@gmail.com", "Akwa centre de formation"));

                helper.setTo(mail);
                helper.setSubject(objet);
                helper.setText(message, false); // false = texte brut (pas HTML)

                mailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
