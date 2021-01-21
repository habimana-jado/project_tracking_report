
package common;

import dao.AccomplishmentDao;
import dao.DivisionDao;
import dao.InstitutionDao;
import dao.ProjectDao;
import dao.UserDao;
import domain.Accomplishment;
import domain.Account;
import domain.Division;
import domain.EAccessLevel;
import domain.EMonth;
import domain.EPeriod;
import domain.EQuarter;
import domain.EStatus;
import domain.Institution;
import domain.Project;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        Institution institution = new Institution();
        institution.setEmail("info@minict.gov.rw");
        institution.setInstitutionName("Migeprof");
        institution.setLocation("Kacyiru");
        institution.setPhone("078898970");
        new InstitutionDao().register(institution);
        Account u = new Account();
        u.setAccessLevel(EAccessLevel.GLOBAL);
        u.setEmail("info@migeprof.gov.rw");
        u.setFirstName("Migeprof");
        u.setLastName("Migeprof");
        u.setPassword(new PassCode().encrypt("secret"));
        u.setStatus(EStatus.ACTIVE);
        u.setUsername("superadmin");
        u.setInstitution(institution);
        new UserDao().register(u);

//        Indicator i = new IndicatorDao().findOne(Indicator.class, "c81a8be5-f7d4-468e-8246-8585ee05f699");
//        Target t = new TargetDao().findByIndicatorAndQuarter(i, EQuarter.QUARTER_ONE);
//        System.out.println(t.getTargetTitle());


//            Account a = new Account();
//            a.setAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
//            a.setPassword(new PassCode().encrypt("admin"));
//            a.setUsername("admin");
//            a.setStatus(EStatus.ACTIVE);
//            new UserDao().register(a);


//        Indicator in = new IndicatorDao().findOne(Indicator.class, "b26ebd16-fc3d-4794-9923-885e63fbb960");
//        List<Target> targets = new TargetDao().findByIndicatorAndQuarterAndMonth(in,EQuarter.QUARTER_ONE, EMonth.MONTH_ONE);
//        for(Target t: targets){
//            System.out.println(t.getTargetTitle());
//        }

//        Division d = new DivisionDao().findOne(Division.class, "686da8c2-81eb-4ff8-81aa-2125dc29c0eb");
//        Project p =new ProjectDao().findOne(Project.class, "87dc337f-ec69-4505-ae21-a13e140ea927");
//        List<Accomplishment> accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, p, EMonth.MONTH_ONE);
//        for(Accomplishment a: accomplishments){
//            System.out.println(a.getAccomplishment());
//        }
        
    }
}
