package com.github.FrancielleSales.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class States {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String regions;
	private long population;
	private String capital;
	private double area;
	
	
	public States() {
	}
	
	public States(long id, String name, String regions, long population, String capital, double area) {
		this.id = id;
		this.name = name;
		this.regions = regions;
		this.population = population;
		this.capital = capital;
		this.area = area;
	}
	
	public States(StatesDto states) {
		this.id = states.getId();
		this.name = states.getName();
		this.regions = states.getRegions();
		this.population = states.getPopulation();
		this.capital = states.getCapital();
		this.area = states.getArea();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegions() {
		return regions;
	}
	public void setRegions(String regions) {
		this.regions = regions;
	}
	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	
}
