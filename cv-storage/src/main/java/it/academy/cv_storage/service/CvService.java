package it.academy.cv_storage.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.academy.cv_storage.data.dao.CvDao;
import it.academy.cv_storage.model.entity.Candidate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@ToString
@Service("cvService")
public class CvService {
	
	
	@Autowired
	CvDao dao;
	
	@Transactional
	public List<Candidate> getCandidateBySql(String sqlQuery) {
		return dao.getAllCandidateBySql(sqlQuery);
		
	}

}
