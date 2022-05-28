package com.iasri.uda.api.upload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CandidateService {

	@Autowired
	private CandidateRepository repo;
	
	  public List<Candidate> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(Candidate candi) {
	        repo.save(candi);
	    }
	     
	    public Candidate get(Integer id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(Integer id) {
	        repo.deleteById(id);
	    }
}
