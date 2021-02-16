package it.academy.cv_storage.app;

import java.sql.Date;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import it.academy.cv_storage.model.entity.Candidate;
import it.academy.cv_storage.model.entity.Email;
import it.academy.cv_storage.model.entity.Knowledge;
import it.academy.cv_storage.model.entity.Phone;
import it.academy.cv_storage.model.entity.Site;
import it.academy.cv_storage.model.entity.Skype;
import it.academy.cv_storage.model.utilities.Gender;

public class Starter {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
										.configure("hibernate.cfg.xml")
										.addAnnotatedClass(Candidate.class)
										.addAnnotatedClass(Phone.class)
										.addAnnotatedClass(Site.class)
										.addAnnotatedClass(Email.class)
										.addAnnotatedClass(Skype.class)
										.addAnnotatedClass(Knowledge.class)
										.buildSessionFactory();
		
		Session session = factory.openSession();
		
		try {
			session.beginTransaction();
			
//			Candidate candidate = new Candidate("Ilon","Ivanovich","Musk",Date.valueOf("1970-01-01"),Gender.МУЖЧИНА);
//			Phone phone = new Phone("+375296000144");
//			Site site = new Site("www.youtube.com");
//			Email email = new Email("ilon.musk@tut.by");
//			Skype skype = new Skype("i.musk");
//			Knowledge knowledge = new Knowledge(Set.of("java", "Maven", "Spring"));
//			candidate.addPhone(phone);
//			candidate.addSite(site);
//			candidate.addEmail(email);
//			candidate.addSkype(skype);
//			candidate.addKnowledge(knowledge);
//			session.persist(candidate);
//			
			Query createQuery = session.createSQLQuery("select * from candidate where FIRST_NAME='Мария'").addEntity(Candidate.class);
			createQuery.list();
			//Candidate candidate = session.get(Candidate.class, "354b0bdb-691e-11eb-8bfa-a08cfda726f3");
		
			System.out.println(createQuery.list());
//			
			session.getTransaction().commit();
			
	
			
		} finally {
			session.close();
			factory.close();
		}

	}

}
