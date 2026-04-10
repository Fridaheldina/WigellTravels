package com.lundberg.wigelltravels.controller;

import com.lundberg.wigelltravels.dto.BookingCreateDto;
import com.lundberg.wigelltravels.dto.BookingResponseDto;
import com.lundberg.wigelltravels.dto.BookingUpdateDto;
import com.lundberg.wigelltravels.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingCreateDto dto){
        BookingResponseDto created = bookingService.createBooking(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PatchMapping("/{bookingId}")// tillåtna fält att ändra: reslängd(veckor), destination, hotell
    public ResponseEntity<Void> updateBooking(@PathVariable Long bookingId, @Valid @RequestBody BookingUpdateDto dto) {
        bookingService.updateBooking(bookingId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getBookingsByCustomer(
            @RequestParam Long customerId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByCustomer(customerId)
        );
    }

}
