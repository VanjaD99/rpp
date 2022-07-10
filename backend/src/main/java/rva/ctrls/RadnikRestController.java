package rva.ctrls;

import java.util.Collection;

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
import rva.jpa.Radnik;
import rva.jpa.Sektor;
import rva.repository.RadnikRepository;
import rva.repository.SektorRepository;

@CrossOrigin
@RestController
@Api(tags = {"Radnik CRUD operacije"})
public class RadnikRestController {
	@Autowired
	private RadnikRepository radnikRepository;
	
	@Autowired
	private SektorRepository sektorRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("radnik")
	@ApiOperation(value = "Vraca kolekciju svih radnik")
	public Collection<Radnik> getRadnici(){
		return radnikRepository.findAll();
	}
	
	@GetMapping("radnik/{id}")
	@ApiOperation(value = "Vraca radnika po id-ju")
	public Radnik getRadnik(@PathVariable ("id") Integer id) {
		return radnikRepository.getOne(id);
	}
	
	@GetMapping("radnikBySektor/{id}")
	@ApiOperation(value = "Vraca radnike po sektoru")
	public Collection<Radnik> getRadnikBySektor(@PathVariable ("id") Integer id){
		Sektor s = sektorRepository.getOne(id);
		return radnikRepository.findBySektor(s);
	}
	
	@GetMapping("radnikByLk/{broj_lk}")
	@ApiOperation(value = "Vraca radnika po vrednosti manjoj od unete licne karte")
	public Collection<Radnik> getRadnikByLk(@PathVariable ("broj_lk") Integer broj_lk){
		return radnikRepository.findByBrojLkLessThanOrderById(broj_lk);
	}
	
	@PostMapping("radnik")
	@ApiOperation(value = "Dodavanje novog radnika")
	public ResponseEntity<Radnik> insertRadnik(@RequestBody Radnik radnik){
		if(!radnikRepository.existsById(radnik.getId())) {
			radnik.setBrojLk(radnikRepository.nextLicnaKarta(radnik.getSektor().getId()));
			radnikRepository.save(radnik);
			return new ResponseEntity<Radnik>(HttpStatus.OK);
		}
		return new ResponseEntity<Radnik>(HttpStatus.CONFLICT);
		
	}
	
	@PutMapping("radnik")
	@ApiOperation(value = "Izmena postojeceg radnika")
	public ResponseEntity<Radnik> updateRadnik(@RequestBody Radnik radnik){
		if(!radnikRepository.existsById(radnik.getId())) {
			return new ResponseEntity<Radnik>(HttpStatus.CONFLICT);
			
		}
		radnikRepository.save(radnik);
		return new ResponseEntity<Radnik>(HttpStatus.OK);
	}
	
	@DeleteMapping("radnik/{id}")
	@ApiOperation(value = "Brisanje radnika po id-ju")
	public ResponseEntity<Radnik> deleteRadnik(@PathVariable Integer id){
		if(!radnikRepository.existsById(id)) {
			return new ResponseEntity<Radnik>(HttpStatus.NO_CONTENT);
		}
		radnikRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute("INSERT INTO \"radnik\" (\"id\", \"ime\", \"prezime\", \"broj_lk\", \"obrazovanje\" ,\"sektor\" )"
					+ "VALUES (-100,'izmenjeno','TEST',223,1,1)");
		}
		return new ResponseEntity<Radnik>(HttpStatus.OK);
	}

}
