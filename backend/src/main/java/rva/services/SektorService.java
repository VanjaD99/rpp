package rva.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.jpa.Sektor;
import rva.repository.SektorRepository;

@Service
public class SektorService {
	@Autowired
	private SektorRepository sektorRepository;
	
	public List<Sektor> getAll(){
		return sektorRepository.findAll();
	}
	
	public Optional<Sektor> findById(Integer id){
		return sektorRepository.findById(id);
	}
	
	public Collection<Sektor> findByNazivContainingIgnoreCase(String naziv){
		return sektorRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	public Sektor save(Sektor sektor) {
		return sektorRepository.save(sektor);
	}
	
	public boolean existsById(Integer id) {
		return sektorRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		sektorRepository.deleteById(id);
	}
}
