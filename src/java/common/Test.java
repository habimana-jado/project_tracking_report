
package common;

import dao.InstitutionDao;
import dao.ProjectDao;
import dao.TargetDao;
import dao.UserDao;
import domain.Account;
import domain.EAccessLevel;
import domain.EStatus;
import domain.Institution;
import domain.Project;
import domain.Target;

public class Test {
    public static void main(String[] args) throws Exception {
//        Institution institution = new Institution();
//        institution.setEmail("info@migeprof.gov.rw");
//        institution.setInstitutionName("Migeprof");
//        institution.setLocation("Kacyiru");
//        institution.setPhone("078898970");
//        new InstitutionDao().register(institution);

//Institution institution = new InstitutionDao().findOne(Institution.class, "ea42fd8a-4a9f-4521-8ea5-b7b3b32652a3");
//        Account u = new Account();
//        u.setAccessLevel(EAccessLevel.GLOBAL);
//        u.setEmail("info@migeprof.gov.rw");
//        u.setFirstName("Migeprof");
//        u.setLastName("Migeprof");
//        u.setPassword(new PassCode().encrypt("secret"));
//        u.setStatus(EStatus.ACTIVE);
//        u.setUsername("admin");
//        u.setInstitution(institution);
//        new UserDao().register(u);
        
        Project p = new ProjectDao().findOne(Project.class, "f7d8ce73-de98-4698-a09f-5d29079d40df");
        for(Target t: new TargetDao().findByProject(p)){
            for(Target tt: new TargetDao().findByTarget(t)){
                new TargetDao().delete(tt);
            }
            new TargetDao().delete(t);
        }
        new ProjectDao().delete(p);
    }
}
