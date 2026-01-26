package com.tcs.Library.dto;

import com.tcs.Library.enums.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {
    private Long id;
    private String donorName;
    private String bookTitle;
    private String author;
    private int quantityOffered;
    private int quantityApproved;
    private DonationStatus status;
    private String adminNotes;
    private LocalDate createdAt;
    private LocalDate processedAt;
}