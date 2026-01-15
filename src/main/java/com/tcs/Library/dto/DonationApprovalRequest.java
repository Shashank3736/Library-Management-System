package com.tcs.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationApprovalRequest {
    private int quantityApproved;
    private String adminNotes;
}
