package it.academy.cv_storage.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import it.academy.cv_storage.data.utilities.CustomSqlCondition;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

@Repository
public class CvDaoImpl implements CvDao {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	CustomSqlCondition sqlCreator;

	@Override
	public List<Candidate> getAllCandidate() {
		Session session = factory.getCurrentSession();
		String sqlQuery = null;
		try {
			sqlQuery = sqlCreator.selectAllFrom(Candidate.class).getQuery();
		} catch (StartSqlSentenceExeption | ClassHasNoCorrectAnnotation | NullClassEntityExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return session.createSQLQuery(sqlQuery).addEntity(Candidate.class).list();
	}


	@Override
	public List<Candidate> getAllCandidateBySql(String sqlQuery) {
		Session session = factory.getCurrentSession();
		
		return session.createSQLQuery(sqlQuery).addEntity(Candidate.class).getResultList();
	}

}
