
package dao;

import domain.Division;
import domain.Project;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class ProjectDao extends GenericDao<Project>{
    public List<Project> findByDivision(Division p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division = :x");
        q.setParameter("x", p);
        List<Project> list = q.list();
        s.close();
        return list;
    }
}
