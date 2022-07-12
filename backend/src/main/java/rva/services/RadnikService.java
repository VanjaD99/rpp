package rva.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import rva.jpa.Radnik;
import rva.jpa.Sektor;
import rva.repository.RadnikRepository;

public class RadnikService {
	@Autowired
	private RadnikRepository radnikRepository;
	
	public List<Radnik> getAll(){
		return radnikRepository.findAll();
	}
	
	public Optional<Radnik> findById(Integer id){
		return radnikRepository.findById(id);
	}
	
	public Collection<Radnik> findByNazivContainingIgnoreCase(Integer broj_lk){
		return radnikRepository.findByBrojLkLessThanOrderById(broj_lk);
	}
	
	public Radnik save(Radnik radnik) {
		return radnikRepository.save(radnik);
	}
	
	public boolean existsById(Integer id) {
		return radnikRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		radnikRepository.deleteById(id);
	}

	public Collection<Radnik> findBySektor(Optional<Sektor> s) {
		return radnikRepository.findBySektor(s);
	}

	public Collection<Radnik> findByBrojLkLessThanOrderById(Integer brojLkId) {
		return radnikRepository.findByBrojLkLessThanOrderById(brojLkId);
	}

	public Integer nextLicnaKarta(Integer sektorId) {
		return radnikRepository.nextLicnaKarta(sektorId);
	}
}
