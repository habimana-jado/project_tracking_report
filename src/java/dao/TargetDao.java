
package dao;

import domain.EMonth;
import domain.EQuarter;
import domain.Indicator;
import domain.Institution;
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
    
    public List<Target> findByTarget(Target p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.target = :x");
        q.setParameter("x", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
    
    public Target findByIndicatorAndQuarter(Indicator p, EQuarter qu){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.indicator = :x AND a.quarter = :qu");
        q.setParameter("x", p);
        q.setParameter("qu", qu);
        Target list = (Target) q.uniqueResult();
        s.close();
        return list;
    }
    
    public List<Target> findByProject(Project p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.indicator.project = :x");
        q.setParameter("x", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
    
    public List<Target> findByProjectAndIndicator(Project p, Indicator in){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.indicator.project = :x AND a.indicator = :y");
        q.setParameter("x", p);
        q.setParameter("y", in);
        List<Target> list = q.list();
        s.close();
        return list;
    }
     
    public Target findByIndicatorAndQuarterAndMonth(Indicator in, EQuarter qu, EMonth p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.target.indicator = :y AND a.quarter = :z AND a.month = :x");
        q.setParameter("x", p);
        q.setParameter("y", in);
        q.setParameter("z", qu);
        Target list = (Target) q.uniqueResult();
        s.close();
        return list;
    }
    
    public List<Target> findMonthlyTargets(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.month IS NOT NULL");
        List<Target> list = q.list();
        s.close();
        return list;
    }
    public List<Target> findMonthlyTargetsByActivity(Project p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.month IS NOT NULL AND a.target.indicator.project = :p");
        q.setParameter("p", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
    
    public List<Target> findMonthlyTargetsByInstitutionAndActionPlan(Institution p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.month IS NOT NULL AND a.target.indicator.project.division.institution = :p");
        q.setParameter("p", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
    
    public List<Target> findMonthlyTargetsByInstitution(Institution i){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.target.indicator.project.division.institution = :i");
        q.setParameter("i", i);
        List<Target> list = q.list();
        s.close();
        return list;
    }
    
    public List<Target> findMonthlyTargetsByInstitutionAndProject(Institution i, Project p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.target.indicator.project.division.institution = :i AND a.target.indicator.project = :p");
        q.setParameter("i", i);
        q.setParameter("p", p);
        List<Target> list = q.list();
        s.close();
        return list;
    }
}
