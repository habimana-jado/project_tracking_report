
package dao;

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
public class TargetDao extends GenericDao<Target>{
    public List<Target> findByIndicator(Indicator p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Target a WHERE a.indicator = :x");
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
}
