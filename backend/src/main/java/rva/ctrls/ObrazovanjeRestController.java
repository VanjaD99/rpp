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
import rva.jpa.Obrazovanje;
import rva.services.ObrazovanjeService;

@CrossOrigin
@RestController
@Api(tags = {"Obrazovanje CRUD operacije"})
public class ObrazovanjeRestController {
	@Autowired
	private ObrazovanjeService obrazovanjeService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("obrazovanje")
	@ApiOperation(value = "Vraca kolekciju svih obrazovanja")
	public Collection<Obrazovanje> getObrazovanja(){
		return obrazovanjeService.getAll();
	}
	
	@GetMapping("obrazovanje/{id}")
	@ApiOperation(value = "Vraca obrazovanje po id-ju")
	public Optional<Obrazovanje> getObrazovanje(@PathVariable ("id") Integer id) {
		return obrazovanjeService.findById(id);
	}
	
	@GetMapping("obrazovanjeNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju obrazovanja po nazivu")
	public Collection<Obrazovanje> getObrazovanjeByNaziv(@PathVariable ("naziv") String naziv){
		return obrazovanjeService.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("obrazovanje")
	@ApiOperation(value = "Dodaje novo obrazovanje")
	public ResponseEntity<Obrazovanje> insertObrazovanje(@RequestBody Obrazovanje obrazovanje){
		if(!obrazovanjeService.existsById(obrazovanje.getId())) {
			obrazovanjeService.save(obrazovanje);
			return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
		}
		return new ResponseEntity<Obrazovanje>(HttpStatus.CONFLICT);
		
	}
	
	@PutMapping("obrazovanje")
	@ApiOperation(value = "Izmena postojeceg obrazovanja")
	public ResponseEntity<Obrazovanje> updateObrazovanje(@RequestBody Obrazovanje obrazovanje){
		if(!obrazovanjeService.existsById(obrazovanje.getId())) {
			return new ResponseEntity<Obrazovanje>(HttpStatus.CONFLICT);
			
		}
		obrazovanjeService.save(obrazovanje);
		return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
	}
	
	@DeleteMapping("obrazovanje/{id}")
	@ApiOperation(value = "Brisanje obrazovanja po id-ju")
	public ResponseEntity<Obrazovanje> deleteObrazovanje(@PathVariable Integer id){
		if(!obrazovanjeService.existsById(id)) {
			return new ResponseEntity<Obrazovanje>(HttpStatus.NO_CONTENT);
		}
		obrazovanjeService.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute("INSERT INTO \"obrazovanje\" (\"id\", \"naziv\", \"stepen_strucne_spreme\", \"opis\")"
					+ "VALUES (-100,'TEST','TEST','TEST')");
		}
		return new ResponseEntity<Obrazovanje>(HttpStatus.OK);
	}
}
