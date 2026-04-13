package com.lundberg.wigelltravels.controller;

import com.lundberg.wigelltravels.dto.DestinationCreateDto;
import com.lundberg.wigelltravels.dto.DestinationDto;
import com.lundberg.wigelltravels.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<DestinationDto>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    @PostMapping
    public ResponseEntity<DestinationDto> createDestination(
            @RequestBody DestinationCreateDto dto) {

        return ResponseEntity.status(201)
                .body(destinationService.createDestination(dto));
    }

    @DeleteMapping("/{destinationId}")
    public ResponseEntity<Void> deleteDestination(
            @PathVariable Long destinationId) {

        destinationService.deleteDestination(destinationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{destinationId}")
    public ResponseEntity<DestinationDto> updateDestination(
            @PathVariable Long destinationId,
            @RequestBody DestinationDto dto) {

        return ResponseEntity.ok(
                destinationService.updateDestination(destinationId, dto)
        );
    }
}