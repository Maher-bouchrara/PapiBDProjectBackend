package com.example.cFormation.dto;

import java.util.List;

public class CertificateBatchRequest {
	private String certTitle;
    private String date;
    private List<String> participants;

    // Getters & Setters
    public String getCertTitle() {
        return certTitle;
    }

    public void setCertTitle(String certTitle) {
        this.certTitle = certTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}