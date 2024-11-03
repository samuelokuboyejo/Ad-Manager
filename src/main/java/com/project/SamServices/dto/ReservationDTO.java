package com.project.SamServices.dto;

import com.project.SamServices.entities.ReservationStatus;
import com.project.SamServices.entities.ReviewStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {
    private Long id;

    private Date bookDate;

    private String serviceName;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private Long userId;

    private String userName;

    private Long companyId;

    private Long adId;
}
