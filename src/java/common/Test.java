
package common;

import dao.InstitutionDao;
import dao.UserDao;
import domain.EAccessLevel;
import domain.EStatus;
import domain.Institution;
import domain.Account;

public class Test {
    public static void main(String[] args) throws Exception {
        Institution institution = new Institution();
        institution.setEmail("risa@gov.rw");
        institution.setInstitutionName("Risa");
        institution.setLocation("Kacyiru");
        institution.setPhone("078898970");
        new InstitutionDao().register(institution);
        Account u = new Account();
        u.setAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
        u.setEmail("risa@gov.rw");
        u.setFirstName("Manzi");
        u.setLastName("Andy");
        u.setPassword(new PassCode().encrypt("manzi"));
        u.setStatus(EStatus.ACTIVE);
        u.setUsername("manzi");
        u.setInstitution(institution);
        new UserDao().register(u);
    }
}
