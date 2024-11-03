package com.project.SamServices.auth.service.client;

import com.project.SamServices.dto.AdDetailsClientDTO;
import com.project.SamServices.dto.AdDto;
import com.project.SamServices.dto.ReservationDTO;
import com.project.SamServices.dto.ReviewDTO;
import com.project.SamServices.entities.*;
import com.project.SamServices.exceptions.BookingFailedException;
import com.project.SamServices.exceptions.ReviewSubmissionException;
import com.project.SamServices.repository.AdRepository;
import com.project.SamServices.repository.ReservationRepository;
import com.project.SamServices.repository.ReviewRepository;
import com.project.SamServices.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    public ClientService(AdRepository adRepository, UserRepository userRepository, ReservationRepository reservationRepository, ReviewRepository reviewRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<AdDto> getAllAds(){
        return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDto> searchAdByName(String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());

    }

    public boolean bookService(ReservationDTO reservationDTO) {
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if (optionalAd.isPresent() && optionalUser.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());
            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getCompany());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);
            return true;
        } else {
            throw new BookingFailedException("Failed to book the service for the provided details.");
        }
    }

    public AdDetailsClientDTO getAdDetailsByAdId(Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsClientDTO adDetailsClientDTO = new AdDetailsClientDTO();

        if (optionalAd.isPresent() ){
            adDetailsClientDTO.setAdDto(optionalAd.get().getAdDto());

            List<Review> reviewList = reviewRepository.findAllAdByAdId(adId);
            adDetailsClientDTO.setReviewDTOList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
        }
        return adDetailsClientDTO;
    }

    public List<ReservationDTO> getAllBookingsByUserId(Long userId){
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    public Boolean giveReview(ReviewDTO reviewDTO) {
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());

        if (optionalUser.isPresent() && optionalBooking.isPresent()) {
            Review review = new Review();
            review.setReviewDate(new Date());
            review.setReview(reviewDTO.getReview());
            review.setRating(reviewDTO.getRating());
            review.setUser(optionalUser.get());
            review.setAd(optionalBooking.get().getAd());

            reviewRepository.save(review);

            Reservation booking = optionalBooking.get();
            booking.setReviewStatus(ReviewStatus.TRUE);
            reservationRepository.save(booking);

            return true;
        } else {
            throw new ReviewSubmissionException("Failed to submit the review for the provided details.");
        }
    }
}
