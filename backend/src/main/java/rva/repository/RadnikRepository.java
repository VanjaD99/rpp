package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rva.jpa.Radnik;
import rva.jpa.Sektor;

public interface RadnikRepository extends JpaRepository<Radnik,Integer>{
	Collection<Radnik> findBySektor(Sektor s);
	Collection<Radnik> findByBrojLkLessThanOrderById(Integer broj_lk);
	
	@Query(value = "select coalesce(max(broj_lk)+1,1) from radnik where sektor=?1", nativeQuery=true)
	Integer nextLicnaKarta(Integer sektorid);
}
