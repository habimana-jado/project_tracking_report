
package dao;

import domain.Accomplishment;
import domain.Project;
import domain.Target;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class AccomplishmentDao extends GenericDao<Accomplishment>{
    public List<Accomplishment> findByTarget(Target p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target = :x");
        q.setParameter("x", p);
        List<Accomplishment> list = q.list();
        s.close();
        return list;
    }
}
