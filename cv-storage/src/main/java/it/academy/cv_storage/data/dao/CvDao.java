package it.academy.cv_storage.data.dao;

import java.util.List;

import it.academy.cv_storage.model.entity.Candidate;

public interface CvDao {


	List<Candidate> getAllCandidateBySql(String sqlQuery);
	
	

}
