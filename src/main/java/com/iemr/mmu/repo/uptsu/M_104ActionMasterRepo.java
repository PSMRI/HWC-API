package com.iemr.mmu.repo.uptsu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.uptsu.M_104ActionMaster;

@Repository
public interface M_104ActionMasterRepo extends CrudRepository<M_104ActionMaster, Integer> {
	
	List<M_104ActionMaster> findByDeleted(Boolean deleted);

}
