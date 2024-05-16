package com.wide.agus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDTO {
    private String type;
    private String value;
    private boolean temporary;

}
