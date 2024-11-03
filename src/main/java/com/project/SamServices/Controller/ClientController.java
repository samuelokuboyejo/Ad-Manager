package com.project.SamServices.Controller;

import com.project.SamServices.auth.service.client.ClientService;
import com.project.SamServices.dto.ReservationDTO;
import com.project.SamServices.dto.ReviewDTO;
import com.project.SamServices.entities.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/ads")
    public ResponseEntity<?> getAllAds(){
        return ResponseEntity.ok(clientService.getAllAds());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchAdByService(@PathVariable String name){
        return ResponseEntity.ok(clientService.searchAdByName(name));
    }

    @PostMapping("/book/service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO){
        clientService.bookService(reservationDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getDetailsByAdId(@PathVariable Long adId){
        return ResponseEntity.ok(clientService.getAdDetailsByAdId(adId));
    }

    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
    }


    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO){
        clientService.giveReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
        }


}
