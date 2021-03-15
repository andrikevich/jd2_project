package it.academy.cv_storage.data.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import it.academy.cv_storage.model.entity.Candidate;

@Repository
public class CvDaoImpl implements CvDao {

	SessionFactory factory;

	@Autowired
	public CvDaoImpl(@Qualifier ("sessionFactory") SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Candidate> getAllCandidateBySql(String sqlQuery) {
		Session session = factory.getCurrentSession();
		return session.createSQLQuery(sqlQuery).addEntity(Candidate.class).getResultList();
	}

}
