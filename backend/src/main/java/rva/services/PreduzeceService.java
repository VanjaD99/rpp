package rva.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.jpa.Preduzece;
import rva.repository.PreduzeceRepository;
@Service
public class PreduzeceService {
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	public List<Preduzece> getAll(){
		return preduzeceRepository.findAll();
	}
	
	public Optional<Preduzece> findById(Integer id){
		return preduzeceRepository.findById(id);
	}
	
	public Collection<Preduzece> findByNazivContainingIgnoreCase(String naziv){
		return preduzeceRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	public Preduzece save(Preduzece preduzece) {
		return preduzeceRepository.save(preduzece);
	}
	
	public boolean existsById(Integer id) {
		return preduzeceRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		preduzeceRepository.deleteById(id);
	}
}
