package com.project.SamServices.auth.service.company;

import com.project.SamServices.dto.AdDto;
import com.project.SamServices.dto.ReservationDTO;
import com.project.SamServices.entities.Ad;
import com.project.SamServices.entities.Company;
import com.project.SamServices.entities.Reservation;
import com.project.SamServices.entities.ReservationStatus;
import com.project.SamServices.exceptions.*;
import com.project.SamServices.repository.AdRepository;
import com.project.SamServices.repository.CompanyRepository;
import com.project.SamServices.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServices {
    private final CompanyRepository companyRepository;

    private final AdRepository adRepository;

    private final ReservationRepository reservationRepository;

    public CompanyServices(CompanyRepository companyRepository, AdRepository adRepository, ReservationRepository reservationRepository) {
        this.companyRepository = companyRepository;
        this.adRepository = adRepository;
        this.reservationRepository = reservationRepository;
    }

    public boolean postAd(Long userId, AdDto adDto) throws IOException {
        Optional<Company> optionalCompany = companyRepository.findById(userId);
        if (optionalCompany.isPresent()) {
            Ad ad = new Ad();
            ad.setServiceName(adDto.getServiceName());
            ad.setDescription(adDto.getDescription()); // Use adDto to set the values
            ad.setImg(ad.getImg());
            ad.setPrice(ad.getPrice());
            ad.setCompany(optionalCompany.get());

            adRepository.save(ad);
            return true;
        } else {
            throw new AdPostingException("Failed to post the ad for the provided details.");
        }
    }


    public List<AdDto> getAllAds(Long userId) {
        return adRepository.findAllByUserId(userId)
                .stream()
                .map(ad -> {
                    AdDto dto = new AdDto();
                    dto.setDescription(ad.getDescription());
                    dto.setImg(ad.getImg());
                    dto.setPrice(ad.getPrice());
                    dto.setServiceName(ad.getServiceName());
                    dto.setUserId(ad.getUserId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public AdDto getAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            return optionalAd.get().getAdDto();
        }
        else {
            throw new AdNotFoundException("Failed to find ad for the provided details.");
        }
    }

    public boolean updateAd(Long adId, AdDto adDto) throws IOException {
        Optional<Ad> optionalAd = adRepository.findById(adId);



        if (optionalAd.isPresent()) {
            Ad ad = optionalAd.get();
            ad.setServiceName(adDto.getServiceName());
            ad.setDescription(adDto.getDescription());
            ad.setPrice(adDto.getPrice());

            if (adDto.getImg() != null) {
                ad.setImg(adDto.getImg().getBytes());
            }

            adRepository.save(ad);
            return true;
        } else {
            throw new AdUpdateException("Failed to update the ad for the provided details.");
        }


    }

    public boolean deleteAd(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            adRepository.delete(optionalAd.get());
            return true;
        }
        else {
            throw new AdDeletionException("Failed to delete ad.");
        }
    }

    public List<ReservationDTO> getAllAdBookings(Long companyId) {
        return reservationRepository.findAllByCompanyId(companyId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    public boolean changeBookingService(Long bookingId, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(bookingId);
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            if (Objects.equals(status, "Approve")) {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            } else {
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(existingReservation);
            return true;
        }
        return false;
    }

    public List<ReservationDTO> getAllBookingsByUserId(Long userId) {
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }
}



