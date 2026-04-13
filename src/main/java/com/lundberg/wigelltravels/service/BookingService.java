package com.lundberg.wigelltravels.service;

import com.lundberg.wigelltravels.dto.BookingCreateDto;
import com.lundberg.wigelltravels.dto.BookingResponseDto;
import com.lundberg.wigelltravels.dto.BookingUpdateDto;
import com.lundberg.wigelltravels.entity.Booking;
import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.entity.Destination;
import com.lundberg.wigelltravels.exception.BookingNotFoundException;
import com.lundberg.wigelltravels.exception.CustomerNotFoundException;
import com.lundberg.wigelltravels.exception.DestinationNotFoundExcepption;
import com.lundberg.wigelltravels.repository.BookingRepository;
import com.lundberg.wigelltravels.repository.CustomerRepository;
import com.lundberg.wigelltravels.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DestinationRepository destinationRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, DestinationRepository destinationRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.destinationRepository = destinationRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public BookingResponseDto createBooking(BookingCreateDto dto){
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Destination destination = destinationRepository.findById(dto.destinationId())
                .orElseThrow(() -> new DestinationNotFoundExcepption("Destination not found"));

        double totalPriceSek = destination.getPricePerWeek() * dto.weeks();
        double totalPricePln = totalPriceSek * 0.38;

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDestination(destination);
        booking.setWeeks(dto.weeks());
        booking.setStartDate(dto.startDate());
        booking.setTotalPriceSek(totalPriceSek);
        booking.setTotalPricePln(totalPricePln);

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Created booking with id {}", savedBooking.getId());

        return new BookingResponseDto(
                savedBooking.getId(),
                destination.getCity(),
                destination.getHotelName(),
                savedBooking.getWeeks(),
                savedBooking.getTotalPriceSek(),
                savedBooking.getTotalPricePln()
        );
    }


    public void updateBooking(Long bookingId, BookingUpdateDto dto){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if (dto.weeks() !=null){
            booking.setWeeks(dto.weeks());
        }
        if (dto.destinationId() !=null){
            Destination destination = destinationRepository.findById(dto.destinationId())
                    .orElseThrow(() -> new DestinationNotFoundExcepption("Destination not found"));
            booking.setDestination(destination);
        }
        double totalPriceSek = booking.getDestination().getPricePerWeek() * booking.getWeeks();
        double totalPricePln = totalPriceSek * 0.38;

        booking.setTotalPriceSek(totalPriceSek);
        booking.setTotalPricePln(totalPricePln);

        bookingRepository.save(booking);
        logger.info("Updated booking with id {}", bookingId);
    }

    public List<BookingResponseDto> getBookingsByCustomer(Long customerId) {
        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);

        return bookings.stream()
                .map(booking -> new BookingResponseDto(
                        booking.getId(),
                        booking.getDestination().getCity(),
                        booking.getDestination().getHotelName(),
                        booking.getWeeks(),
                        booking.getTotalPriceSek(),
                        booking.getTotalPricePln()
                ))
                .toList();
    }
}


