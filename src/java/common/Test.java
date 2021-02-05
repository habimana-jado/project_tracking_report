
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
import static domain.EMonth.MONTH_ONE;
import domain.EPeriod;
import domain.EQuarter;
import domain.EStatus;
import domain.Institution;
import domain.Project;
import java.util.List;
import javax.faces.context.FacesContext;
import org.eclipse.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;

public class Test {
    public static void main(String[] args) throws Exception {
//        Institution institution = new Institution();
//        institution.setEmail("info@minict.gov.rw");
//        institution.setInstitutionName("Migeprof");
//        institution.setLocation("Kacyiru");
//        institution.setPhone("078898970");
//        new InstitutionDao().register(institution);

Institution institution = new InstitutionDao().findOne(Institution.class, "ea42fd8a-4a9f-4521-8ea5-b7b3b32652a3");
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

//        Division d = new DivisionDao().findOne(Division.class, "4e6a7933-f982-46b9-b112-a3492bfdf8a9");
//        Project p = new ProjectDao().findOne(Project.class, "2980886a-dfe3-453d-ad21-0227faf1bac8");
//        System.out.println("Project  "+d.getDivisionName());
//        Project p =new ProjectDao().findOne(Project.class, "87dc337f-ec69-4505-ae21-a13e140ea927");
//        List<Accomplishment> accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, p, EMonth.MONTH_ONE);
//List<Accomplishment> accomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, d, MONTH_ONE);
        
//        for(Accomplishment a: accomplishments){
//            System.out.println(a.getAccomplishment());
//            System.out.println("Activity "+a.getTarget().getTarget().getIndicator().getProject().getProjectTitle());
//        }
        
//        Institution in = new InstitutionDao().findOne(Institution.class, "8a8180f0-eb45-4bcb-9b5d-deb01a940002");
//        System.out.println("Test 1 --"+in);
//        for(Accomplishment a: new AccomplishmentDao().findByQuarterAndMonthAndPeriod(EQuarter.QUARTER_ONE, EMonth.MONTH_ONE, EPeriod.WEEK_ONE, in)){
//            System.out.println("Test--" + a.getAccomplishment());
//        }

//        Account x = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
//        System.out.println("Test Test Test "+a.getDivision().getDivisionName());
//        Division d = new DivisionDao().findOne(Division.class, "64402f29-e6e6-467d-87b9-df2825e09d92");
//        for(Accomplishment a: new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, x.getDivision(), MONTH_ONE)){
//            System.out.println("Test-- "+a.getAccomplishment());
//        }
//        for(Project p: new ProjectDao().findByDivisionAndNotInActionPlan(d)){
//            System.out.println("Test 2-- "+p.getProjectTitle());
//        }
    }
}
