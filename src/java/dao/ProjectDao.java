
package dao;

import domain.Division;
import domain.Institution;
import domain.Project;
import java.util.Date;
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
    public List<Project> findByDivisionAndFiscalYear(Division p, Date from, Date to){
        Boolean y = Boolean.TRUE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division = :x AND a.isInActionPlan = :y AND a.startDate BETWEEN :from AND :to");
        q.setParameter("x", p);
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
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
    
    public List<Project> findByInstitutionAndFiscalYear(Institution p, Date from, Date to){
        Boolean y = Boolean.TRUE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division.institution = :x AND a.isInActionPlan = :y AND a.startDate BETWEEN :from AND :to");
        q.setParameter("x", p);
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
        List<Project> list = q.list();
        s.close();
        return list;
    }
    public List<Project> findByFiscalYear(Date from, Date to){
        Boolean y = Boolean.TRUE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.isInActionPlan = :y AND a.startDate BETWEEN :from AND :to");
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
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
    
    public List<Project> findByDivisionAndNotInActionPlanAndFiscalYear(Division p, Date from, Date to){
        Boolean y = Boolean.FALSE;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Project a WHERE a.division = :x AND a.isInActionPlan = :y AND a.startDate BETWEEN :from AND :to");
        q.setParameter("x", p);
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
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
