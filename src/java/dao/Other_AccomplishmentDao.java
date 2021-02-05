
package dao;

import domain.Accomplishment;
import domain.Division;
import domain.EMonth;
import domain.EPeriod;
import domain.EQuarter;
import domain.Institution;
import domain.Other_Accomplishment;
import domain.Project;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2021
 */
public class Other_AccomplishmentDao extends GenericDao<Other_Accomplishment>{
    
    public List<Other_Accomplishment> findByProjectAndNotInActionPlan(Project pr){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.project = :pr ");
        qu.setParameter("pr", pr);
        List<Other_Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Other_Accomplishment> findByProjectAndQuarterAndPeriodAndMonth(EQuarter q, EPeriod p, Project pr, EMonth mo){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.period = :p AND a.quarter = :q AND a.project = :pr AND a.month = :mo");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        List<Other_Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Other_Accomplishment> findByDivisionAndQuarterAndPeriodAndMonth(EQuarter q, EPeriod p, Division pr, EMonth mo){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.period = :p AND a.quarter = :q AND a.project.division = :pr AND a.month = :mo");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        List<Other_Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    public List<Other_Accomplishment> findByInstitution(Institution p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.project.division.institution = :x");
        q.setParameter("x", p);
        List<Other_Accomplishment> list = q.list();
        s.close();
        return list;
    }
    public List<Other_Accomplishment> findByQuarterAndMonthAndPeriod(EQuarter q,EMonth m, EPeriod p, Institution ins){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.period = :p AND a.quarter = :q AND a.month = :m AND a.project.division.institution = :ins");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("ins", ins);
        List<Other_Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Other_Accomplishment> findByQuarterAndMonthAndPeriod(EQuarter q,EMonth m, EPeriod p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Other_Accomplishment a WHERE a.period = :p AND a.quarter = :q AND a.month = :m ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        List<Other_Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
}
