package com.github.FrancielleSales.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.github.FrancielleSales.api.model.States;
import com.github.FrancielleSales.api.model.StatesDto;
import com.github.FrancielleSales.api.model.StatesRepository;

@Service
public class StatesService {
	
	private static final Logger log = LoggerFactory.getLogger(StatesService.class);
	
	@Autowired
	private StatesRepository repository;
	
	// Adicionar estado
	public StatesDto addNewStates(StatesDto states) {

		log.info("addNewStates() - INICIO - salvando o estado {}", states.getName());
		States savedStates = repository.save(new States(states));
		log.info("addNewStates() - FIM - estado salvo com o ID {}", states.getId());
		return new StatesDto(savedStates);
	}
	
	// Localizar estado pelo Id
    public StatesDto getStatesById(Long statesId) {
        log.info("getStatesById() - INICIO - Buscando o estado pelo ID: {}", statesId);
        States states = getValidStatesById(statesId);
        log.info("getStatesById() - FIM - Estado {} encontrado", states.getName());
        return new StatesDto(states);
    }
    
    // Validar procura do estado pelo Id
    private States getValidStatesById(long id) {
        Optional<States> optionalStates = repository.findById(id);
        if (optionalStates.isPresent()) {
            return optionalStates.get();
        } else {
            log.warn("getValidStatesById() - AVISO - Estado com ID: {} não existe na base", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID informado não localizado");
        }
    }
    
    // Listar estados presentes no banco de dados
    public List<StatesDto> getAllStates(){
    	List<States> statesEntityList = repository.findAll();
    	List<StatesDto> statesDto = new ArrayList<>();
    	
    	for (States s : statesEntityList) {
    		StatesDto state = new StatesDto(s);
    		statesDto.add(state);
    	}
    	
    	return statesDto;
    }
    
    // Atualizar o estado pelo Id
    public StatesDto updateStatesById(Long statesId, StatesDto states) {
        log.info("updateStatesById() - INICIO - Atualizando o estado pelo ID: {}", statesId);
        States statesEntity = getValidStatesById(statesId);
        statesEntity.setName(states.getName() != null ? states.getName() : statesEntity.getName());
        statesEntity.setRegions(states.getRegions() != null ? states.getRegions() : statesEntity.getRegions());
        statesEntity.setPopulation(states.getPopulation() != 0 ? states.getPopulation() : statesEntity.getPopulation());
        statesEntity.setCapital(states.getCapital() != null ? states.getCapital() : statesEntity.getCapital());
        statesEntity.setArea(states.getArea() != 0 ? states.getArea() : statesEntity.getArea());
        repository.save(statesEntity);
        log.info("updateStatesById() - FIM - Informações atualizadas");
        return new StatesDto(statesEntity);
    }
    
    // Deletar estado pelo Id
    public void deleteStatesById(Long statesId) {
        log.info("deleteStatesById() - INICIO - Excluindo o estado pelo ID: {}", statesId);
        States states = getValidStatesById(statesId);
        repository.delete(states);
        log.info("deletestatesById() - FIM - Estado excluido com sucesso");
    }
    
    private Page<StatesDto> convertToDto(Page<States> states) {
    	return states.map(StatesDto::new);
    }
    
    // Filtros
    public Page<StatesDto> getAllStates(@RequestParam(required = false) String regions, 
    		@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        if (regions == null) {
            Page<States> states = repository.findAll(pageable);
            return convertToDto(states);
        } else {
            Page<States> states = repository.findByRegions(regions, pageable);
            return convertToDto(states);
        }
    }
    
    

}
