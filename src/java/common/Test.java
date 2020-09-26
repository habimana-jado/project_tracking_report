
package common;

import dao.IndicatorDao;
import dao.InstitutionDao;
import dao.TargetDao;
import dao.UserDao;
import domain.EAccessLevel;
import domain.EStatus;
import domain.Institution;
import domain.Account;
import domain.EQuarter;
import domain.Indicator;
import domain.Target;

public class Test {
    public static void main(String[] args) throws Exception {
//        Institution institution = new Institution();
//        institution.setEmail("risa@gov.rw");
//        institution.setInstitutionName("Risa");
//        institution.setLocation("Kacyiru");
//        institution.setPhone("078898970");
//        new InstitutionDao().register(institution);
//        Account u = new Account();
//        u.setAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
//        u.setEmail("risa@gov.rw");
//        u.setFirstName("Manzi");
//        u.setLastName("Andy");
//        u.setPassword(new PassCode().encrypt("manzi"));
//        u.setStatus(EStatus.ACTIVE);
//        u.setUsername("manzi");
//        u.setInstitution(institution);
//        new UserDao().register(u);

//        Indicator i = new IndicatorDao().findOne(Indicator.class, "c81a8be5-f7d4-468e-8246-8585ee05f699");
//        Target t = new TargetDao().findByIndicatorAndQuarter(i, EQuarter.QUARTER_ONE);
//        System.out.println(t.getTargetTitle());


            Account a = new Account();
            a.setAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
            a.setPassword(new PassCode().encrypt("admin"));
            a.setUsername("admin");
            a.setStatus(EStatus.ACTIVE);
            new UserDao().register(a);
    }
}
