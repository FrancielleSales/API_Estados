package com.github.FrancielleSales.api.model;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatesRepository extends JpaRepository <States, Long> {
	
   Page<States> findByRegions(String regions, Pageable sort);

}
