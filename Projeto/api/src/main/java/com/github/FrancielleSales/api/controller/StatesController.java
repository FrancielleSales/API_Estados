package com.github.FrancielleSales.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.FrancielleSales.api.model.StatesDto;
import com.github.FrancielleSales.api.service.StatesService;

@RestController
@RequestMapping("/api/states")
public class StatesController {
	
	private static final Logger log = LoggerFactory.getLogger(StatesController.class);
	
	@Autowired
	private StatesService service;
	
	private boolean validateRegion(String region) {
		switch(region) {
			case "Nordeste":
			case "Sul":
			case "Centro-Oeste":
			case "Norte":
			case "Sudeste":
			return true;
		}
		return false;
	}
	
	@PostMapping
	public ResponseEntity <StatesDto> createNewStates(@RequestBody StatesDto states) {
		log.info("createNewStates() - INICIO - chamando serviço");
		if (validateRegion(states.getRegions())) {
			StatesDto statesDto = service.addNewStates(states);
			return ResponseEntity.ok(statesDto);
		} else {
			return null;
		}
	}
	
    @GetMapping
    public ResponseEntity<Page<StatesDto>> findAll(String regions, Pageable pageable) {
        log.debug("findAll() - chamando serviço");
        return ResponseEntity.ok(service.getAllStates(regions, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StatesDto> findStatesById(@PathVariable Long id) {
        log.debug("findStatesById() - chamando serviço");
        return ResponseEntity.ok(service.getStatesById(id));
    }
   
    @PutMapping("/{id}")
    public ResponseEntity<StatesDto> updateStates(@PathVariable Long id, @RequestBody StatesDto states) {
        log.debug("updateStates() - chamando o serviço");
        return ResponseEntity.ok(service.updateStatesById(id, states));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStates(@PathVariable Long id) {
        log.debug("deleteStates() - chamando o serviço");
        service.deleteStatesById(id);
        return ResponseEntity.noContent().build();
    }

}
