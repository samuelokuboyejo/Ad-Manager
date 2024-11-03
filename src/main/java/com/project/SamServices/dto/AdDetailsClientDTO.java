package com.project.SamServices.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdDetailsClientDTO {

    private AdDto adDto;

    private List<ReviewDTO> reviewDTOList;
}
