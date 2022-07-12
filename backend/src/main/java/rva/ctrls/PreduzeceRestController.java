package rva.ctrls;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Preduzece;

import rva.services.PreduzeceService;

@CrossOrigin
@RestController
@Api(tags = {"Preduzece CRUD operacije"})
public class PreduzeceRestController {
	
	@Autowired
	private PreduzeceService preduzececeService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("preduzece")
	@ApiOperation(value = "Vraca kolekciju svih preduzeca")
	public Collection<Preduzece> getPreduzeca(){
		return preduzececeService.getAll();
	}
	
	@GetMapping("preduzece/{id}")
	@ApiOperation(value = "Vraca preduzece po id-ju")
	public Optional<Preduzece> getPreduzece(@PathVariable ("id") Integer id) {
		return preduzececeService.findById(id);
	}
	
	
	@GetMapping("preduzeceNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju preduzece po nazivu")
	public Collection<Preduzece> getPreduzeceByNaziv(@PathVariable ("naziv") String naziv){
		return preduzececeService.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("preduzece")
	@ApiOperation(value = "Dodaje novo preduzece")
	public ResponseEntity<Preduzece> insertPreduzece(@RequestBody Preduzece preduzece){
		if(!preduzececeService.existsById(preduzece.getId())) {
			preduzececeService.save(preduzece);
			return new ResponseEntity<Preduzece>(HttpStatus.OK);
		}
		return new ResponseEntity<Preduzece>(HttpStatus.CONFLICT);
		
	}
	
	@PutMapping("preduzece")
	@ApiOperation(value = "Menja postojece preduzece")
	public ResponseEntity<Preduzece> updatePreduzece(@RequestBody Preduzece preduzece){
		if(!preduzececeService.existsById(preduzece.getId())) {
			return new ResponseEntity<Preduzece>(HttpStatus.CONFLICT);
			
		}
		preduzececeService.save(preduzece);
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
	}
	
	//@Transactional
	@DeleteMapping("preduzece/{id}")
	@ApiOperation(value = "Brise preduzece po id-ju")
	public ResponseEntity<Preduzece> deletePreduzece(@PathVariable Integer id){
		if(!preduzececeService.existsById(id)) {
			return new ResponseEntity<Preduzece>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("DELETE FROM sektor WHERE preduzece=" + id);
		preduzececeService.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute("INSERT INTO \"preduzece\" (\"id\", \"naziv\", \"pib\", \"sediste\", \"opis\")"
					+ "VALUES (-100,'TEST',223,'TEST','TEST')");
		}
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
	}
	
	
}
