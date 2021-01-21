
package dao;

import domain.Division;
import domain.EStatus;
import domain.Institution;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class DivisionDao extends GenericDao<Division>{
    
    public List<Division> findByInstitution(Institution institution){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Division a WHERE a.institution = :inst");
        q.setParameter("inst", institution);
        List<Division> list = q.list();
        s.close();
        return list;
    }
    public List<Division> findByInstitutionAndStatus(Institution institution, EStatus st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Division a WHERE a.institution = :inst AND a.status = :st");
        q.setParameter("inst", institution);
        q.setParameter("st", st);
        List<Division> list = q.list();
        s.close();
        return list;
    }

}
