
package dao;

import domain.Indicator;
import domain.Project;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class IndicatorDao extends GenericDao<Indicator>{
    public List<Indicator> findByProject(Project p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Indicator a WHERE a.project = :x");
        q.setParameter("x", p);
        List<Indicator> list = q.list();
        s.close();
        return list;
    }
}
