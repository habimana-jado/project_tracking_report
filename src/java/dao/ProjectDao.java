
package dao;

import domain.Division;
import domain.Institution;
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
        Boolean y = Boolean.TRUE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division = :x AND a.isInActionPlan = :y");
        q.setParameter("x", p);
        q.setParameter("y", y);
        List<Project> list = q.list();
        s.close();
        return list;
    }
    public List<Project> findByInstitution(Institution p){
        Boolean y = Boolean.TRUE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division.institution = :x AND a.isInActionPlan = :y");
        q.setParameter("x", p);
        q.setParameter("y", y);
        List<Project> list = q.list();
        s.close();
        return list;
    }
    
    public List<Project> findByDivisionAndNotInActionPlan(Division p){
        Boolean y = Boolean.FALSE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division = :x AND a.isInActionPlan = :y");
        q.setParameter("x", p);
        q.setParameter("y", y);
        List<Project> list = q.list();
        s.close();
        return list;
    }
    public List<Project> findByInstitutionAndNotInActionPlan(Institution p){
        Boolean y = Boolean.FALSE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division.institution = :x");
        q.setParameter("x", p);
        q.setParameter("y", y);
        List<Project> list = q.list();
        s.close();
        return list;
    }
}
