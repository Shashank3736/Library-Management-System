package com.tcs.Library.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class IssueBookRequest {
    private String bookPublicId;
    private UUID userPublicId;
}
