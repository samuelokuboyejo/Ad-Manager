package com.project.SamServices.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
public class AdDto {
    private Long id;

    private String serviceName;

    private String description;

    private Double price;

    private byte[] returnedImg;

    private MultipartFile img;

    private Long userId;

    private String companyName;
}
