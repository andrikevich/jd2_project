package it.academy.cv_storage.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.academy.cv_storage.dao.CvDao;
import it.academy.cv_storage.model.entity.Candidate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
