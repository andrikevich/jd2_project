package it.academy.cv_storage.data.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;


public class CustomSqlSelectTest {

	@Test
	public void test() throws StartSqlSentenceExeption {
		CustomSqlSelect selector = new CustomSqlSelect();
		String requiredSrt = selector.getQuery();
		System.out.println(requiredSrt);
		assertEquals("", requiredSrt);
	
	}

}
