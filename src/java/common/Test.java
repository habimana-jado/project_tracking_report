
package common;

import dao.AccomplishmentDao;
import dao.InstitutionDao;
import dao.Other_AccomplishmentDao;
import dao.ProjectDao;
import dao.TargetDao;
import dao.UserDao;
import domain.Accomplishment;
import domain.Account;
import domain.EAccessLevel;
import domain.EMonth;
import domain.EPeriod;
import domain.EQuarter;
import domain.EStatus;
import domain.Institution;
import domain.Other_Accomplishment;
import domain.Project;
import domain.Target;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        
//        Project p = new ProjectDao().findOne(Project.class, "f7d8ce73-de98-4698-a09f-5d29079d40df");
//        for(Target t: new TargetDao().findByProject(p)){
//            for(Target tt: new TargetDao().findByTarget(t)){
//                new TargetDao().delete(tt);
//            }
//            new TargetDao().delete(t);
//        }
//        new ProjectDao().delete(p);

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
//        System.out.println("x Week = " + week1);
//        
//        Calendar cal1 = Calendar.getInstance();
//        cal1.set(2021, Calendar.JULY, 30);
//        int week = cal1.get(Calendar.WEEK_OF_MONTH);
//        System.out.println("y Week = " + week);
    
//        for(Accomplishment a: new AccomplishmentDao().findAll(Accomplishment.class)){
//            System.out.println("Fiscal Year = "+a.getTarget().getTarget().getIndicator().getProject().getStartDate());
//            System.out.println("Period = "+a.getTarget().getTarget().getQuarter() +"   "+a.getTarget().getMonth()+"   "+a.getPeriod());
//            
//        }

        Date from = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/2021");        
        Date to = new SimpleDateFormat("dd/MM/yyyy").parse("30/06/2022");
        
        Project p = new ProjectDao().findOne(Project.class, "22387a46-fd58-4158-b7d4-09b97f813b22");
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        
        Date dt = cal.getTime();
        
        System.out.println("Date = "+new Date());
        
        System.out.println("Past Date = "+dt);
        
        
        String year = new SimpleDateFormat("yyyy").format(dt);
        String monthx = new SimpleDateFormat("MM").format(dt);
        
        EQuarter eQuarter;
        String quarter;
        EMonth eMonth;
        String month;
        Date fiscalYearFrom;
        Date fiscalYearTo;
        String fiscalYear;
        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
        String period = String.valueOf(week1);
        EPeriod ePeriod;
        

        if (monthx.equalsIgnoreCase("01")) {
            eQuarter = EQuarter.QUARTER_THREE;
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("02")) {
            eQuarter = EQuarter.QUARTER_THREE;
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("03")) {
            eQuarter = EQuarter.QUARTER_THREE;
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("04")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("05")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("06")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("07")) {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("08")) {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("09")) {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("10")) {
            eQuarter = EQuarter.QUARTER_TWO;
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("11")) {
            eQuarter = EQuarter.QUARTER_TWO;
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("12")) {
            eQuarter = EQuarter.QUARTER_TWO;
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_THREE;
        } else {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        }

//        int week1 = cal.get(Calendar.WEEK_OF_MONTH);

        month = eMonth + "";
//        period = "Week" + week1;

        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        }
        
        switch (period) {
            case "Week1":
                ePeriod = EPeriod.WEEK_ONE;
                break;
            case "Week2":
                ePeriod = EPeriod.WEEK_TWO;
                break;
            case "Week3":
                ePeriod = EPeriod.WEEK_THREE;
                break;
            case "Week4":
                ePeriod = EPeriod.WEEK_FOUR;
                break;
            case "Week5":
                ePeriod = EPeriod.WEEK_FIVE;
                break;

            default:
                ePeriod = EPeriod.WEEK_ONE;
                break;

        }

        System.out.println("Quarter = "+eQuarter);
        System.out.println("Month = "+eMonth);
        System.out.println("Week = "+ePeriod);
        
        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(dt)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(dt)) - 1) + "";

//        Date fiscalYearFrom;
//        Date fiscalYearTo;

        if (monthx.equalsIgnoreCase("01") || monthx.equalsIgnoreCase("02") || monthx.equalsIgnoreCase("03") || monthx.equalsIgnoreCase("04") || monthx.equalsIgnoreCase("05") || monthx.equalsIgnoreCase("06")) {
            fiscalYearFrom = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + lastYear);
            fiscalYearTo = new SimpleDateFormat("dd/MM/yyyy").parse("30/06/" + year);
            fiscalYear = lastYear + "/" + year;
        } else if (monthx.equalsIgnoreCase("07") || monthx.equalsIgnoreCase("08") || monthx.equalsIgnoreCase("09") || monthx.equalsIgnoreCase("10") || monthx.equalsIgnoreCase("11") || monthx.equalsIgnoreCase("12")) {
            fiscalYearFrom = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + year);
            fiscalYearTo = new SimpleDateFormat("dd/MM/yyyy").parse("30/06/" + nextYear);
            fiscalYear = year + "/" + nextYear;
        } else {
            fiscalYearFrom = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + lastYear);
            fiscalYearTo = new SimpleDateFormat("dd/MM/yyyy").parse("30/06/" + year);
            fiscalYear = lastYear + "/" + year;
        }

        System.out.println("Fiscal Year = "+fiscalYear);
        
        
        for(Accomplishment a: new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonthAndFiscalYear(eQuarter, ePeriod, p, eMonth, fiscalYearFrom, fiscalYearTo)){
        System.out.println(a.getAccomplishment());
    }
        
//        for(Other_Accomplishment a: new Other_AccomplishmentDao().findAll(Other_Accomplishment.class)){
//            System.out.println("Fiscal Year = "+a.getProject().getStartDate());
//            System.out.println("Period = "+a.getQuarter() +"   "+a.getMonth()+"   "+a.getPeriod());
//            
//        }
    }
}
