package dao;

import domain.Account;
import common.PassCode;
import domain.EAccessLevel;
import domain.Institution;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDao extends GenericDao<Account> {

    public List<Account> login(String u, String password) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("select a from Account a where a.username=:v and a.password=:p");
        q.setParameter("v", u);
        q.setParameter("p", password);
        List<Account> l = q.list();
        return l;
    }

    public List<Account> loginencrypt(String u, String pass) throws Exception {

        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Account> list = new ArrayList<>();

        List<Account> users = new UserDao().findAll(Account.class);
        String z = "";
        for (Account us : users) {
            if (us.getUsername().matches(u)) {
                if ((new PassCode().decrypt(us.getPassword())).matches(pass)) {
                    list.add(us);
                }
            }

        }

        s.close();
        return list;

    }

    public Account findByUsername(String username) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Account a WHERE a.username = :x");
        q.setParameter("x", username);
        Account u = (Account) q.uniqueResult();
        s.close();
        return u;
    }

    public List<Account> findByInstitution(Institution inst) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Account a WHERE a.division.institution = :x");
        q.setParameter("x", inst);
        List<Account> u = q.list();
        s.close();
        return u;
    }

    public List<Account> findByAccessLevel(EAccessLevel inst) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Account a WHERE a.accessLevel = :x");
        q.setParameter("x", inst);
        List<Account> u = q.list();
        s.close();
        return u;
    }

}
