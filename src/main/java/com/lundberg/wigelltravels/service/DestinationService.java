package com.lundberg.wigelltravels.service;

import com.lundberg.wigelltravels.dto.DestinationDto;
import com.lundberg.wigelltravels.entity.Destination;
import com.lundberg.wigelltravels.exception.DestinationNotFoundExcepption;
import com.lundberg.wigelltravels.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public DestinationDto createDestination(DestinationDto dto){
        Destination destination = new Destination();
        destination.setCity(dto.city());
        destination.setCountry(dto.country());
        destination.setHotelName(dto.hotelName());
        destination.setPricePerWeek(dto.pricePerWeek());

        Destination saved = destinationRepository.save(destination);
        System.out.println("Create destination " + saved.getId());

        return new DestinationDto(
                saved.getId(),
                saved.getCity(),
                saved.getCountry(),
                saved.getHotelName(),
                saved.getPricePerWeek()
        );
    }

    public List<DestinationDto> getAllDestinations() {

        return destinationRepository.findAll().stream()
                .map(destination -> new DestinationDto(
                        destination.getId(),
                        destination.getCity(),
                        destination.getCountry(),
                        destination.getHotelName(),
                        destination.getPricePerWeek()
                ))
                .toList();
    }


    public DestinationDto updateDestination(Long id, DestinationDto dto) {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundExcepption("Destination not found"));

        destination.setCity(dto.city());
        destination.setCountry(dto.country());
        destination.setHotelName(dto.hotelName());
        destination.setPricePerWeek(dto.pricePerWeek());
        Destination updated = destinationRepository.save(destination);

        return new DestinationDto(
                updated.getId(),
                updated.getCity(),
                updated.getCountry(),
                updated.getHotelName(),
                updated.getPricePerWeek()
        );
    }


    public void deleteDestination(Long id) {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundExcepption("Destination not found"));

        destinationRepository.delete(destination);
        System.out.println("Deleted destination " + id);
    }
}
