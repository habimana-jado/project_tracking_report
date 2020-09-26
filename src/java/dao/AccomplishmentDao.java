
package dao;

import domain.Accomplishment;
import domain.Division;
import domain.EPeriod;
import domain.EQuarter;
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
public class AccomplishmentDao extends GenericDao<Accomplishment>{
    public List<Accomplishment> findByTarget(Target p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target = :x");
        q.setParameter("x", p);
        List<Accomplishment> list = q.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByIndicator(Indicator p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target.indicator = :x");
        q.setParameter("x", p);
        List<Accomplishment> list = q.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByIndicatorAndQuarterAndPeriod(Indicator p,EQuarter qu, EPeriod pe){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target.indicator = :x AND a.period = :pe AND a.target.quarter = :qu");
        q.setParameter("x", p);
        q.setParameter("pe", pe);
        q.setParameter("qu", qu);
        List<Accomplishment> list = q.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByQuarterAndPeriod(EQuarter q, EPeriod p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByDivisionAndQuarterAndPeriod(EQuarter q, Division d, EPeriod p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.indicator.project.division = :d");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("d", d);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByDivisionAndProjectAndQuarterAndPeriod(EQuarter q, Division d, EPeriod p, Project pr){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.indicator.project.division = :d AND a.target.indicator.project = :pr");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("d", d);
        qu.setParameter("pr", pr);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
}
