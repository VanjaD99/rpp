package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

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
import rva.jpa.Sektor;
import rva.repository.SektorRepository;

@CrossOrigin
@RestController
@Api(tags = {"Sektor CRUD operacije"})
public class SektorRestController {
	
	@Autowired
	private SektorRepository sektorRepository;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("sektor")
	@ApiOperation(value = "Vraca kolekciju svih sektora")
	public Collection<Sektor> getSektori(){
		return sektorRepository.findAll();
	}
	
	@GetMapping("sektor/{id}")
	@ApiOperation(value = "Vraca sektor po id-ju")
	public Sektor getSektor(@PathVariable ("id") Integer id) {
		return sektorRepository.getOne(id);
	}
	
	@GetMapping("sektorNaziv/{naziv}")
	@ApiOperation(value = "Vraca sektor po nazivu")
	public Collection<Sektor> getSektorByNaziv(@PathVariable ("naziv") String naziv){
		return sektorRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("sektor")
	@ApiOperation(value = "Dodaje novi sektor")
	public ResponseEntity<Sektor> insertSektor(@RequestBody Sektor sektor){
		if(!sektorRepository.existsById(sektor.getId())) {
			sektorRepository.save(sektor);
			return new ResponseEntity<Sektor>(HttpStatus.OK);
		}
		return new ResponseEntity<Sektor>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("sektor")
	@ApiOperation(value = "Izmena postojeceg sektora")
	public ResponseEntity<Sektor> updateSektor(@RequestBody Sektor sektor){
		if(!sektorRepository.existsById(sektor.getId())) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
			
		}
		sektorRepository.save(sektor);
		return new ResponseEntity<Sektor>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("sektor/{id}")
	@ApiOperation(value = "Brisanje sektora po id-ju")
	public ResponseEntity<Sektor> deleteSektor(@PathVariable ("id") Integer id){
		if(!sektorRepository.existsById(id)) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("DELETE FROM radnik WHERE sektor=" + id);
		sektorRepository.deleteById(id);
		
		if(id == -100) {
			jdbcTemplate.execute("INSERT INTO \"sektor\" (\"id\", \"naziv\", \"oznaka\", \"preduzece\")"
					+ "VALUES (-100,'TEST','TEST',3)");
		}
		return new ResponseEntity<Sektor>(HttpStatus.OK);
	}

}
