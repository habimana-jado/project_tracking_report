
package dao;

import domain.Indicator;
import domain.Project;
import domain.Target;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class TargetDao extends GenericDao<Target>{
    public List<Target> findByIndicator(Indicator p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.indicator = :x");
        q.setParameter("x", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
}
