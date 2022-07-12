package rva.ctrls;

import java.util.Collection;
import java.util.Optional;

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
import rva.services.SektorService;;

@CrossOrigin
@RestController
@Api(tags = {"Sektor CRUD operacije"})
public class SektorRestController {
	
	@Autowired
	private SektorService sektorService;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("sektor")
	@ApiOperation(value = "Vraca kolekciju svih sektora")
	public Collection<Sektor> getSektori(){
		return sektorService.getAll();
	}
	
	@GetMapping("sektor/{id}")
	@ApiOperation(value = "Vraca sektor po id-ju")
	public Optional<Sektor> getSektor(@PathVariable ("id") Integer id) {
		return sektorService.findById(id);
	}
	
	@GetMapping("sektorNaziv/{naziv}")
	@ApiOperation(value = "Vraca sektor po nazivu")
	public Collection<Sektor> getSektorByNaziv(@PathVariable ("naziv") String naziv){
		return sektorService.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("sektor")
	@ApiOperation(value = "Dodaje novi sektor")
	public ResponseEntity<Sektor> insertSektor(@RequestBody Sektor sektor){
		if(!sektorService.existsById(sektor.getId())) {
			sektorService.save(sektor);
			return new ResponseEntity<Sektor>(HttpStatus.OK);
		}
		return new ResponseEntity<Sektor>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("sektor")
	@ApiOperation(value = "Izmena postojeceg sektora")
	public ResponseEntity<Sektor> updateSektor(@RequestBody Sektor sektor){
		if(!sektorService.existsById(sektor.getId())) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
			
		}
		sektorService.save(sektor);
		return new ResponseEntity<Sektor>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("sektor/{id}")
	@ApiOperation(value = "Brisanje sektora po id-ju")
	public ResponseEntity<Sektor> deleteSektor(@PathVariable ("id") Integer id){
		if(!sektorService.existsById(id)) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("DELETE FROM radnik WHERE sektor=" + id);
		sektorService.deleteById(id);
		
		if(id == -100) {
			jdbcTemplate.execute("INSERT INTO \"sektor\" (\"id\", \"naziv\", \"oznaka\", \"preduzece\")"
					+ "VALUES (-100,'TEST','TEST',3)");
		}
		return new ResponseEntity<Sektor>(HttpStatus.OK);
	}

}
