package com.project.SamServices.Controller;

import com.project.SamServices.auth.service.company.CompanyServices;
import com.project.SamServices.dto.AdDto;
import com.project.SamServices.dto.ReservationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {


    private final CompanyServices companyServices;


    public CompanyController(CompanyServices companyServices) {
        this.companyServices = companyServices;
    }

    @PostMapping("/post/ad/{userId}")
    public ResponseEntity<?> postAd(@PathVariable Long userId, @ModelAttribute AdDto adDto) throws IOException {
        companyServices.postAd(userId, adDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/ads/{userId}")
    public ResponseEntity<?> getAllAdsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(companyServices.getAllAds(userId));
    }


    @GetMapping("/get-ads/{adId}")
    public ResponseEntity<AdDto> getAdById(@PathVariable Long adId) {
        AdDto adDto = companyServices.getAdById(adId);
        return ResponseEntity.ok(adDto);
    }

    @PutMapping("/update/ad/{adId}")
    public ResponseEntity<Void> updateAd(@PathVariable Long adId, @ModelAttribute AdDto adDto) throws IOException {
        companyServices.updateAd(adId, adDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/ad/{adId}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long adId) {
        companyServices.deleteAd(adId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/bookings/{companyId}")
    public ResponseEntity<List<ReservationDTO>> getAllAdsBooking(@PathVariable Long companyId){
        return ResponseEntity.ok(companyServices.getAllAdBookings(companyId));
    }

    @GetMapping("/bookings/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId,@PathVariable String status){
        boolean success = companyServices.changeBookingService(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }


}
