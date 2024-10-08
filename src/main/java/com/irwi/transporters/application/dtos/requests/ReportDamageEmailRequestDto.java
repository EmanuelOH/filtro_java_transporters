package com.irwi.transporters.application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDamageEmailRequestDto {
    private String userEmail;
    private String reportName;
    private String reportDescription;
}
