
package dao;

import domain.Accomplishment;
import domain.Division;
import domain.EMonth;
import domain.EPeriod;
import domain.EQuarter;
import domain.Indicator;
import domain.Institution;
import domain.Project;
import domain.Target;
import java.util.Date;
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
    
    public List<Accomplishment> findByInstitution(Institution p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target.indicator.project.division.institution = :x");
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
    
    public List<Accomplishment> findByQuarterAndMonthAndPeriod(EQuarter q,EMonth m, EPeriod p, Institution ins){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.division.institution = :ins "
                + "ORDER BY a.target.target.indicator.project.division.divisionName ASC, a.target.target.indicator.project.projectTitle ASC, a.target.target.indicator.indicatorName ASC, a.target.target.targetTitle ASC, a.target.targetTitle ASC");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("ins", ins);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByQuarterAndMonthAndPeriodAndFiscalYear(EQuarter q,EMonth m, EPeriod p, Institution ins, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.division.institution = :ins AND a.target.target.indicator.project.startDate BETWEEN :from AND :to "
                + "ORDER BY a.target.target.indicator.project.division.divisionName ASC, a.target.target.indicator.project.projectTitle ASC, a.target.target.indicator.indicatorName ASC, a.target.target.targetTitle ASC, a.target.targetTitle ASC");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        qu.setParameter("ins", ins);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByQuarterAndMonthAndPeriodAndDivision(EQuarter q,EMonth m, EPeriod p, Division ins){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.division = :ins");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("ins", ins);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    public List<Accomplishment> findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(EQuarter q,EMonth m, EPeriod p, Division ins, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.division = :ins AND a.target.target.indicator.project.startDate BETWEEN :from AND :to ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("ins", ins);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    public List<Accomplishment> findByQuarterAndMonthAndPeriod(EQuarter q,EMonth m, EPeriod p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m "
                + "ORDER BY a.target.target.indicator.project.division.institution.institutionName ASC, a.target.target.indicator.project.division.divisionName ASC, "
                + "a.target.target.indicator.project.projectTitle ASC, a.target.target.indicator.indicatorName ASC, a.target.target.targetTitle ASC, a.target.targetTitle ASC ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    public List<Accomplishment> findByQuarterAndMonthAndPeriodAndFiscalYear(EQuarter q,EMonth m, EPeriod p, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.startDate BETWEEN :from AND :to "
                + "ORDER BY a.target.target.indicator.project.division.institution.institutionName ASC, a.target.target.indicator.project.division.divisionName ASC, "
                + "a.target.target.indicator.project.projectTitle ASC, a.target.target.indicator.indicatorName ASC, a.target.target.targetTitle ASC, a.target.targetTitle ASC ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByQuarterAndMonthAndPeriodAndFiscalYearAndInstitution(EQuarter q,EMonth m, EPeriod p, Date from, Date to, Institution inst){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.target.target.indicator.project.division.institution = :inst AND a.period = :p AND a.target.quarter = :q AND a.target.month = :m AND a.target.target.indicator.project.startDate BETWEEN :from AND :to "
                + "ORDER BY a.target.target.indicator.project.division.institution.institutionName ASC, a.target.target.indicator.project.division.divisionName ASC, "
                + "a.target.target.indicator.project.projectTitle ASC, a.target.target.indicator.indicatorName ASC, a.target.target.targetTitle ASC, a.target.targetTitle ASC ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("m", m);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        qu.setParameter("inst", inst);
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
    
    public List<Accomplishment> findByDivisionAndProjectAndQuarterAndPeriodAndMonth(EQuarter q, EPeriod p, Project pr, EMonth mo){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.target.indicator.project = :pr AND a.target.month = :mo");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByDivisionAndProjectAndQuarterAndPeriodAndMonthAndFiscalYear(EQuarter q, EPeriod p, Project pr, EMonth mo, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.target.indicator.project = :pr AND a.target.month = :mo AND a.target.target.indicator.project.startDate BETWEEN :from AND :to");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByDivisionAndQuarterAndPeriodAndMonth(EQuarter q, EPeriod p, Division pr, EMonth mo){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.target.indicator.project.division = :pr AND a.target.month = :mo");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByDivisionAndQuarterAndPeriodAndMonthAndFiscalYear(EQuarter q, EPeriod p, Division pr, EMonth mo, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.target.indicator.project.division = :pr AND a.target.month = :mo AND a.target.target.indicator.project.startDate BETWEEN :from AND :to ");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        qu.setParameter("from", from);
        qu.setParameter("to", to);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
    
    public List<Accomplishment> findByInstitutionAndQuarterAndPeriodAndMonth(EQuarter q, EPeriod p, Project pr, EMonth mo){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query qu = s.createQuery("SELECT a FROM Accomplishment a WHERE a.period = :p AND a.target.quarter = :q AND a.target.target.indicator.project = :pr AND a.target.month = :mo");
        qu.setParameter("p", p);
        qu.setParameter("q", q);
        qu.setParameter("pr", pr);
        qu.setParameter("mo", mo);
        List<Accomplishment> list = qu.list();
        s.close();
        return list;
    }
}
