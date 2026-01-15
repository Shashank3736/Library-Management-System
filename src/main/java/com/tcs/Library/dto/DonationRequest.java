package com.tcs.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationRequest {
    private String bookTitle;
    private String author;
    private int quantityOffered;
}
