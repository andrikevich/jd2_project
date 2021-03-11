package it.academy.cv_storage.service.dbunit;


import it.academy.cv_storage.model.entity.Candidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CvServiceTestWithDbUnit  extends BaseTest{

    @Test
    @Transactional
    public void read(){
        cleanInsert("CvStorageTest.xml");
        Session session = sessionFactory.getCurrentSession();
        List<Candidate> candidates = session.createQuery("from Candidate where phones='+3752988888888", Candidate.class).list();

        Assert.assertNotNull(candidates);
        Assert.assertEquals(3, candidates.size());
        //deleteDataset();
        session.close();
    }

}
