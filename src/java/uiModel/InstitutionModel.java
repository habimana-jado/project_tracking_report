package uiModel;

import common.PassCode;
import dao.AccomplishmentDao;
import dao.DivisionDao;
import dao.IndicatorDao;
import dao.InstitutionDao;
import dao.Other_AccomplishmentDao;
import dao.ProjectDao;
import dao.TargetDao;
import dao.UserDao;
import domain.Accomplishment;
import domain.Division;
import domain.EAccessLevel;
import domain.EStatus;
import domain.Project;
import domain.Target;
import domain.Account;
import domain.EMonth;
import domain.EPeriod;
import domain.EQuarter;
import domain.Indicator;
import domain.Institution;
import domain.Other_Accomplishment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@ManagedBean
@SessionScoped
public class InstitutionModel {

    private Account loggedInUser = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private Division division = new Division();
    private List<Division> divisions = new ArrayList<>();
    private List<Division> allDivisions = new DivisionDao().findAll(Division.class);
    private List<Division> activeDivisions = new ArrayList<>();
    private Project project = new Project();
    private List<Project> projects = new ArrayList<>();
    private List<Project> allProjects = new ArrayList<>();
    private Account user = new Account();
    private String password = new String();
    private String departmentFilterValue = new String();
    private String institutionFilterValue = new String();
    private boolean activityFilterType = true;
    private String activityType = "All";
    private List<Account> users = new ArrayList<>();
    private List<Account> divisionUsers = new ArrayList<>();
    private List<Account> globalUsers = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private List<Accomplishment> accomplishments = new ArrayList<>();
    private String divisionId = new String();
    private Division chosenDivision = new Division();
    private List<Indicator> indicators = new ArrayList<>();
    private Indicator indicator = new Indicator();
    private String quarter = "Quarter1";
    private Project registeredProject = new Project();
    private Target target = new Target();
    private List<Accomplishment> divisionAccomplishments = new ArrayList<>();
    private List<Other_Accomplishment> divisionOtherAccomplishments = new ArrayList<>();
    private List<Accomplishment> institutionAccomplishments = new ArrayList<>();
    private List<Other_Accomplishment> institutionOtherAccomplishments = new ArrayList<>();
    private Indicator chosenIndicator = new Indicator();
    private Target chosenTarget = new Target();
    private String period = new String();
    private EPeriod ePeriod;
    private EQuarter eQuarter;
    private EMonth eMonth;
    private List<Target> monthlyTargets = new ArrayList<>();
    private String month = "Month1";
    private Institution institution = new Institution();
    private List<Account> institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
    private List<Institution> institutions = new InstitutionDao().findAll(Institution.class);
    private List<Target> allMonthlyTargets = new TargetDao().findMonthlyTargets();
    private List<Target> institutionMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitution(loggedInUser.getInstitution());
    private String activityId = new String();
    private String institutionId = new String();
    private Date reportDate = new Date();
    private String fiscalYear = new String();
    private Date fiscalYearDate = new Date();
    private List<Division> institutionDivisions = new ArrayList<>();

    @PostConstruct
    public void init() {
        findCurrentPeriodReport();
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        users = new UserDao().findByAccessLevel(EAccessLevel.DIVISION_MANAGER);
        globalUsers = new UserDao().findByAccessLevel(EAccessLevel.GLOBAL);
        divisionUsers = new UserDao().findByInstitution(loggedInUser.getInstitution());
        try {
            fetchProjectsByInstitutionAndFiscalYear();
            fetchProjectsByFiscalYear();
            loadReportByFiscalYear();
            loadOtherReportByFiscalYear();
            loadInstitutionReportByFiscalYear();
            loadInstitutionOtherReportByFiscalYear();
        } catch (ParseException ex) {
            Logger.getLogger(InstitutionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        institutionMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitution(loggedInUser.getInstitution());
    }

    public void fetchDivisionsByInstitution() throws ParseException {

        institutionDivisions.clear();

        for (Division d : new DivisionDao().findAll(Division.class)) {
            if (d.getInstitution().getInstitutionId().equalsIgnoreCase(institutionId)) {
                {
                    institutionDivisions.add(d);
                }
            }
        }
        loadInstitutionReportByFiscalYear();
        loadInstitutionOtherReportByFiscalYear();
    }

    public void fetchProjectsByInstitutionAndFiscalYear() throws ParseException {
        String year = new SimpleDateFormat("yyyy").format(fiscalYearDate);
        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(fiscalYearDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(fiscalYearDate)) - 1) + "";
        String monthx = new SimpleDateFormat("MM").format(fiscalYearDate);

        Date fiscalYearFrom;
        Date fiscalYearTo;

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
        projects = new ProjectDao().findByInstitutionAndFiscalYear(loggedInUser.getInstitution(), fiscalYearFrom, fiscalYearTo);

    }

    public void fetchProjectsByFiscalYear() throws ParseException {
        String year = new SimpleDateFormat("yyyy").format(fiscalYearDate);
        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(fiscalYearDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(fiscalYearDate)) - 1) + "";
        String monthx = new SimpleDateFormat("MM").format(fiscalYearDate);

        Date fiscalYearFrom;
        Date fiscalYearTo;

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
        allProjects = new ProjectDao().findByFiscalYear(fiscalYearFrom, fiscalYearTo);

    }

    public void registerInstitution() throws Exception {
        if (new InstitutionDao().findOne(Institution.class, institution.getInstitutionId()) != null) {
            new InstitutionDao().update(institution);
            institution = new Institution();
            institutions = new InstitutionDao().findAll(Institution.class);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Institution Updated"));
        } else {
            if (new UserDao().findByUsername(user.getUsername()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already used"));
            } else {
                new InstitutionDao().register(institution);
                institutions = new InstitutionDao().findAll(Institution.class);
                institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
                user = new Account();
                institution = new Institution();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Institution Registered"));
            }
        }
    }

    public void updateRendering() {
        if (activityType.isEmpty() || activityType == null) {
            activityType = "ActionPlan";
        } else if (activityType.equalsIgnoreCase("ActionPlan")) {
            activityType = "ActionPlan";
        } else if (activityType.equalsIgnoreCase("Other")) {
            activityType = "Other";
        } else if (activityType.equalsIgnoreCase("All")) {
            activityType = "All";
        }
        loadReport();
        loadOtherReport();
    }

    public void updateRenderingInstitution() {
        if (activityType.isEmpty() || activityType == null) {
            activityType = "ActionPlan";
        } else if (activityType.equalsIgnoreCase("ActionPlan")) {
            activityType = "ActionPlan";
        } else if (activityType.equalsIgnoreCase("Other")) {
            activityType = "Other";
        } else if (activityType.equalsIgnoreCase("All")) {
            activityType = "All";
        }
        loadInstitutionReport();
        loadInstitutionOtherReport();
    }

    public void loadReport() {
        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution());
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }

    }

    public void loadReportByFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution(), fiscalYearFrom, fiscalYearTo);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }

        loadOtherReportByFiscalYear();
    }

    public void loadReportCompiledByDateAndFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

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

        Calendar cal = Calendar.getInstance();
        cal.setTime(reportDate);
        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
        System.out.println("Week = " + week1);

        month = eMonth + "";
        period = "Week" + week1;

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

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution(), fiscalYearFrom, fiscalYearTo);
            divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution(), fiscalYearFrom, fiscalYearTo);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
                divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }

    }

    public void loadReportCompiledByDate() {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

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

        Calendar cal = Calendar.getInstance();
        cal.setTime(reportDate);
        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
        System.out.println("Week = " + week1);

        month = eMonth + "";
        period = "Week" + week1;

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
        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution());
            divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution());
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
                divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }

    }

    public void loadOtherReport() {
        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution());
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }

    }

    public void loadOtherReportByFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, loggedInUser.getInstitution(), fiscalYearFrom, fiscalYearTo);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }

    }

    public void findCurrentPeriodReport() {
        EQuarter currentQuarter = EQuarter.QUARTER_ONE;
        EMonth currentMonth = EMonth.MONTH_ONE;
        EPeriod currentPeriod = EPeriod.WEEK_ONE;
        for (Accomplishment a : new AccomplishmentDao().findAll(Accomplishment.class)) {
            switch (a.getTarget().getTarget().getQuarter()) {
                case QUARTER_FOUR:
                    currentQuarter = EQuarter.QUARTER_FOUR;
                    quarter = "Quarter4";
                    break;
                case QUARTER_THREE:
                    currentQuarter = EQuarter.QUARTER_THREE;
                    quarter = "Quarter3";
                    break;
                case QUARTER_TWO:
                    currentQuarter = EQuarter.QUARTER_TWO;
                    quarter = "Quarter2";
                    break;
                case QUARTER_ONE:
                    currentQuarter = EQuarter.QUARTER_ONE;
                    quarter = "Quarter1";
                    break;
                default:
                    currentQuarter = EQuarter.QUARTER_FOUR;
                    quarter = "Quarter4";
                    break;
            }

            switch (a.getTarget().getMonth()) {
                case MONTH_FOUR:
                    currentMonth = EMonth.MONTH_FOUR;
                    month = "Month4";
                    break;
                case MONTH_THREE:
                    currentMonth = EMonth.MONTH_THREE;
                    month = "Month3";
                    break;
                case MONTH_TWO:
                    currentMonth = EMonth.MONTH_TWO;
                    month = "Month2";
                    break;
                case MONTH_ONE:
                    currentMonth = EMonth.MONTH_ONE;
                    month = "Month1";
                    break;
                default:
                    currentMonth = EMonth.MONTH_FOUR;
                    month = "Month4";
                    break;
            }

            switch (a.getPeriod()) {
                case WEEK_FOUR:
                    currentPeriod = EPeriod.WEEK_FOUR;
                    period = "Period4";
                    break;
                case WEEK_THREE:
                    currentPeriod = EPeriod.WEEK_THREE;
                    period = "Period3";
                    break;
                case WEEK_TWO:
                    currentPeriod = EPeriod.WEEK_TWO;
                    period = "Period2";
                    break;
                case WEEK_ONE:
                    currentPeriod = EPeriod.WEEK_ONE;
                    period = "Period1";
                    break;
                default:
                    currentPeriod = EPeriod.WEEK_FOUR;
                    period = "Period4";
                    break;
            }
        }
        institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(currentQuarter, currentMonth, currentPeriod);
        institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(currentQuarter, currentMonth, currentPeriod);
    }

    public void loadInstitutionReport() {
        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }
    }

    public void loadInstitutionReportByFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (institutionId.isEmpty() || institutionId == null) {
            institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
        }
        
        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            Institution i = new InstitutionDao().findOne(Institution.class, institutionId);
            if (i == null) {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYearAndInstitution(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo, i);
            }
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }
        
        loadInstitutionOtherReportByFiscalYear();
    }

    public void loadInstitutionReportCompiledByDate() {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (monthx.equalsIgnoreCase("01")) {
            eQuarter = EQuarter.QUARTER_THREE;
            quarter = "Quarter3";
            month = "Month1";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("02")) {
            eQuarter = EQuarter.QUARTER_THREE;
            month = "Month2";
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("03")) {
            eQuarter = EQuarter.QUARTER_THREE;
            month = "Month3";
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("04")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month1";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("05")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month2";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("06")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month3";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("07")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month1";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("08")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month2";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("09")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month3";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("10")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month1";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("11")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month2";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("12")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month3";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_THREE;
        } else {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(reportDate);
        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
        period = "Week" + week1;

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

        institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);
        institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);
            institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }

    }

    public void loadInstitutionReportCompiledByDateAndFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (monthx.equalsIgnoreCase("01")) {
            eQuarter = EQuarter.QUARTER_THREE;
            quarter = "Quarter3";
            month = "Month1";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("02")) {
            eQuarter = EQuarter.QUARTER_THREE;
            month = "Month2";
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("03")) {
            eQuarter = EQuarter.QUARTER_THREE;
            month = "Month3";
            quarter = "Quarter3";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("04")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month1";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("05")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month2";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("06")) {
            eQuarter = EQuarter.QUARTER_FOUR;
            month = "Month3";
            quarter = "Quarter4";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("07")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month1";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("08")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month2";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("09")) {
            eQuarter = EQuarter.QUARTER_ONE;
            month = "Month3";
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_THREE;
        } else if (monthx.equalsIgnoreCase("10")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month1";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_ONE;
        } else if (monthx.equalsIgnoreCase("11")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month2";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_TWO;
        } else if (monthx.equalsIgnoreCase("12")) {
            eQuarter = EQuarter.QUARTER_TWO;
            month = "Month3";
            quarter = "Quarter2";
            eMonth = EMonth.MONTH_THREE;
        } else {
            eQuarter = EQuarter.QUARTER_ONE;
            quarter = "Quarter1";
            eMonth = EMonth.MONTH_ONE;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(reportDate);
        int week1 = cal.get(Calendar.WEEK_OF_MONTH);
        period = "Week" + week1;

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

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
            institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYearAndNoInstitution(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }

    }

    public void loadInstitutionOtherReport() {
        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(eQuarter, eMonth, ePeriod);
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivision(eQuarter, eMonth, ePeriod, division);
            }
        }
    }

    public void loadInstitutionOtherReportByFiscalYear() throws ParseException {

        String year = new SimpleDateFormat("yyyy").format(reportDate);
        String monthx = new SimpleDateFormat("MM").format(reportDate);

        if (period.isEmpty() || period == null || period.equals("")) {
            period = "Week1";

        } else if (quarter.isEmpty() || quarter == null || quarter.equals("")) {
            quarter = "Quarter1";

        } else if (month.isEmpty() || month == null || month.equals("")) {
            month = "Month1";

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

        switch (quarter) {
            case "Quarter1":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "Quarter2":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "Quarter3":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "Quarter4":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "Month1":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "Month2":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "Month3":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        String nextYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) + 1) + "";
        String lastYear = (Integer.parseInt(new SimpleDateFormat("yyyy").format(reportDate)) - 1) + "";

        Date fiscalYearFrom;
        Date fiscalYearTo;

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

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            Institution i = new InstitutionDao().findOne(Institution.class, institutionId);
            if (i == null) {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYear(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYearAndInstitution(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo, i);
            }
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }

        if (departmentFilterValue.isEmpty() || departmentFilterValue == null) {
            Institution i = new InstitutionDao().findOne(Institution.class, institutionId);
            if (i == null) {
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndFiscalYearAndNoInstitution(eQuarter, eMonth, ePeriod, fiscalYearFrom, fiscalYearTo);
            } else {
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndInstitutionAndFiscalYear(eQuarter, eMonth, ePeriod, i, fiscalYearFrom, fiscalYearTo);
            }
        } else {
            Division division = new DivisionDao().findOne(Division.class, departmentFilterValue);
            if (division == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Division Not Found"));
            } else {
                institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriodAndDivisionAndFiscalYear(eQuarter, eMonth, ePeriod, division, fiscalYearFrom, fiscalYearTo);
            }
        }
    }

    public void filterOperationPlan() {
        if (activityId.equalsIgnoreCase("All")) {
            institutionMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitution(loggedInUser.getInstitution());
        } else {
            Project p = new ProjectDao().findOne(Project.class, activityId);
            institutionMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitutionAndProject(loggedInUser.getInstitution(), p);
        }
    }

    public void adminFilterOperationPlan() {
        if (activityId.equalsIgnoreCase("All")) {
            allMonthlyTargets = new TargetDao().findMonthlyTargets();
        } else {
            Project p = new ProjectDao().findOne(Project.class, activityId);
            allMonthlyTargets = new TargetDao().findMonthlyTargetsByActivity(p);
        }
    }

    public void adminFilterOperationPlanByInstitution() {
        if (institutionId.equalsIgnoreCase("All")) {
            allMonthlyTargets = new TargetDao().findMonthlyTargets();
        } else {
            Institution i = new InstitutionDao().findOne(Institution.class, institutionId);
            allMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitutionAndActionPlan(i);
        }
    }

    public void registerDivisionManager() throws Exception {
        if (new UserDao().findOne(Account.class, user.getUserId()) != null) {
            user.setPassword(new PassCode().encrypt(password));
            Division div = new DivisionDao().findOne(Division.class, divisionId);
            user.setDivision(div);
            new UserDao().update(user);
            user = new Account();
            divisionUsers = new UserDao().findByInstitution(loggedInUser.getInstitution());
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Updated"));
        } else {
            if (new UserDao().findByUsername(user.getUsername()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already used"));
            } else {
                user.setAccessLevel(EAccessLevel.DIVISION_MANAGER);
                user.setStatus(EStatus.ACTIVE);
                user.setPassword(new PassCode().encrypt(password));
                Division div = new DivisionDao().findOne(Division.class, divisionId);
                user.setDivision(div);
                new UserDao().register(user);
                user = new Account();
                divisionUsers = new UserDao().findByInstitution(loggedInUser.getInstitution());
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("User Registered"));
            }
        }
    }

    public void registerInstitutionManager() throws Exception {
        if (new UserDao().findOne(Account.class, user.getUserId()) != null) {
            user.setPassword(new PassCode().encrypt(password));
            Institution div = new InstitutionDao().findOne(Institution.class, divisionId);
            user.setInstitution(div);
            new UserDao().update(user);
            user = new Account();
            institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
            users = new UserDao().findByInstitution(loggedInUser.getInstitution());
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Updated"));
        } else {
            if (new UserDao().findByUsername(user.getUsername()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already used"));
            } else {
                user.setAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
                user.setStatus(EStatus.ACTIVE);
                user.setPassword(new PassCode().encrypt(password));
                Institution div = new InstitutionDao().findOne(Institution.class, divisionId);
                user.setInstitution(div);
                new UserDao().register(user);
                user = new Account();
                institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
                users = new UserDao().findByInstitution(loggedInUser.getInstitution());
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("User Registered"));
            }
        }
    }

    public void registerGlobalUser() throws Exception {
        if (new UserDao().findOne(Account.class, user.getUserId()) != null) {
            user.setPassword(new PassCode().encrypt(password));
            new UserDao().update(user);
            user = new Account();
            globalUsers = new UserDao().findByAccessLevel(EAccessLevel.GLOBAL);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Updated"));
        } else {
            if (new UserDao().findByUsername(user.getUsername()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already used"));
            } else {
                user.setAccessLevel(EAccessLevel.GLOBAL);
                user.setStatus(EStatus.ACTIVE);
                user.setPassword(new PassCode().encrypt(password));
                new UserDao().register(user);
                user = new Account();
                globalUsers = new UserDao().findByAccessLevel(EAccessLevel.GLOBAL);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("User Registered"));
            }
        }
    }

    public void registerDivision() {
        division.setStatus(EStatus.ACTIVE);
        division.setInstitution(loggedInUser.getInstitution());
        new DivisionDao().register(division);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        division = new Division();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Department Registered"));
    }

    public void blockUser(Account a) {
        a.setStatus(EStatus.INACTIVE);
        new UserDao().update(a);
        users = new UserDao().findByAccessLevel(EAccessLevel.DIVISION_MANAGER);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Blocked"));
    }

    public void activateUser(Account a) {
        a.setStatus(EStatus.ACTIVE);
        new UserDao().update(a);
        users = new UserDao().findByAccessLevel(EAccessLevel.DIVISION_MANAGER);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Activated"));
    }

    public void blockGlobalUser(Account a) {
        a.setStatus(EStatus.INACTIVE);
        new UserDao().update(a);
        globalUsers = new UserDao().findByAccessLevel(EAccessLevel.GLOBAL);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Blocked"));
    }

    public void activateGlobalUser(Account a) {
        a.setStatus(EStatus.ACTIVE);
        new UserDao().update(a);
        globalUsers = new UserDao().findByAccessLevel(EAccessLevel.GLOBAL);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Activated"));
    }

    public void blockDivision(Division a) {
        a.setStatus(EStatus.INACTIVE);
        new DivisionDao().update(a);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        allDivisions = new DivisionDao().findAll(Division.class);
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Division Blocked"));
    }

    public void activateDivision(Division a) {
        a.setStatus(EStatus.ACTIVE);
        new DivisionDao().update(a);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        allDivisions = new DivisionDao().findAll(Division.class);
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Division Activated"));
    }

    public String navigateDivision(Division div) {
        chosenDivision = div;
        return "project.xhtml?faces-redirect=true";
    }

    public void chooseUser(Account u) {
        user = u;
    }

    public void chooseDivision(Division u) {
        division = u;
    }

    public void chooseProject(Project u) {
        project = u;
    }

    public void cleanDivision() {
        division = new Division();
    }

    public void cleanUser() {
        user = new Account();
    }

    public void chooseInstitution(Institution u) {
        institution = u;
    }

    public void cleanInstitution() {
        institution = new Institution();
    }

    public void chooseIndicator(Indicator u) {
        indicator = u;
    }

    public void chooseTarget(Target u) {
        month = u.getMonth().toString();
        target = u;
    }

    public void chooseQuarterlyTarget(Target u) {
        quarter = u.getQuarter().toString();
        target = u;
    }

    public void registerProject() throws ParseException {
        Division d = new DivisionDao().findOne(Division.class, divisionId);
        project.setDivision(d);
        project.setIsInActionPlan(Boolean.TRUE);
        new ProjectDao().register(project);
        registeredProject = project;
        project = new Project();
//        projects = new ProjectDao().findByInstitution(loggedInUser.getInstitution());
        fetchProjectsByInstitutionAndFiscalYear();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Activity Registered"));
    }

    public void registerIndicator() {
        indicator.setProject(registeredProject);

        new IndicatorDao().register(indicator);
        indicator = new Indicator();
        indicators = new IndicatorDao().findByProject(registeredProject);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Indicator Added"));
    }

    public void registerTarget() {
        boolean flag = false;

        switch (quarter) {
            case "QUARTER_ONE":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "QUARTER_TWO":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "QUARTER_THREE":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "QUARTER_FOUR":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;
        }

        for (Target t : new TargetDao().findByIndicator(chosenIndicator)) {
            if (t.getQuarter().equals(eQuarter)) {
                flag = true;
            }
        }

        if (flag) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target for " + eQuarter + " has already been added"));
        } else {
            target.setIndicator(chosenIndicator);
            target.setQuarter(eQuarter);
            new TargetDao().register(target);
            target = new Target();
            targets = new TargetDao().findByIndicator(chosenIndicator);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target Added"));
        }
    }

    public void updateTarget() {
        switch (quarter) {
            case "QUARTER_ONE":
                eQuarter = EQuarter.QUARTER_ONE;
                break;

            case "QUARTER_TWO":
                eQuarter = EQuarter.QUARTER_TWO;
                break;

            case "QUARTER_THREE":
                eQuarter = EQuarter.QUARTER_THREE;
                break;

            case "QUARTER_FOUR":
                eQuarter = EQuarter.QUARTER_FOUR;
                break;
        }

        target.setMonth(eMonth);
        target.setTarget(chosenTarget);

        target.setQuarter(chosenTarget.getQuarter());
        new TargetDao().register(target);
        target = new Target();
        monthlyTargets = new TargetDao().findByTarget(chosenTarget);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Target Updated"));

    }

    public void registerMonthlyTarget() {
        boolean flag = false;

        switch (month) {
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

        }

        for (Target t : new TargetDao().findByTarget(chosenTarget)) {
            if (t.getMonth().equals(eMonth)) {
                flag = true;
            }
        }

        if (flag) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target for " + eMonth + " has already been added"));
        } else {
            target.setMonth(eMonth);
            target.setTarget(chosenTarget);

            target.setQuarter(chosenTarget.getQuarter());
            new TargetDao().register(target);
            target = new Target();
            monthlyTargets = new TargetDao().findByTarget(chosenTarget);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target Added"));
        }
        institutionMonthlyTargets = new TargetDao().findMonthlyTargetsByInstitution(loggedInUser.getInstitution());
    }

    public void updateMonthlyTarget() {
        boolean flag = false;

        switch (month) {
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

        }

        for (Target t : new TargetDao().findByTarget(chosenTarget)) {
            if (t.getMonth().equals(eMonth)) {
                flag = true;
            }
        }

        if (flag && !month.equalsIgnoreCase(target.getMonth().toString())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target for " + eMonth + " has already been added"));
            target = new Target();
        } else {
            target.setMonth(eMonth);
            target.setTarget(chosenTarget);

            target.setQuarter(chosenTarget.getQuarter());
            new TargetDao().register(target);
            target = new Target();
            monthlyTargets = new TargetDao().findByTarget(chosenTarget);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Target Updated"));
        }

    }

    public String navigateProject(Project p) {
        registeredProject = p;
        indicators = new IndicatorDao().findByProject(registeredProject);
        return "target.xhtml?faces-redirect=true";
    }

    public String navigateIndicator(Indicator ind) {
        chosenIndicator = ind;
        targets = new TargetDao().findByIndicator(ind);
        return "indicator.xhtml?faces-redirect=true";
    }

    public String navigateMonthlyTarget(Target ind) {
        chosenTarget = ind;
        monthlyTargets = new TargetDao().findByTarget(ind);
        return "monthly-target.xhtml?faces-redirect=true";
    }

    public String navigateProfile() throws Exception {
        password = new PassCode().decrypt(loggedInUser.getPassword());
        return "profile.xhtml?faces-redirect=true";
    }

    public String navigateProfileDM() throws Exception {
        password = new PassCode().decrypt(loggedInUser.getPassword());
        return "dm-profile.xhtml?faces-redirect=true";
    }

    public void updateProfile() throws Exception {
        loggedInUser.setPassword(new PassCode().encrypt(password));
        new UserDao().update(loggedInUser);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Updated"));
    }

    public void removeMontlyTarget(Target t) {
        new TargetDao().delete(t);
        monthlyTargets = new TargetDao().findByTarget(chosenTarget);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Target Removed"));
    }

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Account loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getUsers() {
        return users;
    }

    public void setUsers(List<Account> users) {
        this.users = users;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public List<Accomplishment> getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(List<Accomplishment> accomplishments) {
        this.accomplishments = accomplishments;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public Division getChosenDivision() {
        return chosenDivision;
    }

    public void setChosenDivision(Division chosenDivision) {
        this.chosenDivision = chosenDivision;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Project getRegisteredProject() {
        return registeredProject;
    }

    public void setRegisteredProject(Project registeredProject) {
        this.registeredProject = registeredProject;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public List<Accomplishment> getDivisionAccomplishments() {
        return divisionAccomplishments;
    }

    public void setDivisionAccomplishments(List<Accomplishment> divisionAccomplishments) {
        this.divisionAccomplishments = divisionAccomplishments;
    }

    public Indicator getChosenIndicator() {
        return chosenIndicator;
    }

    public void setChosenIndicator(Indicator chosenIndicator) {
        this.chosenIndicator = chosenIndicator;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Target getChosenTarget() {
        return chosenTarget;
    }

    public void setChosenTarget(Target chosenTarget) {
        this.chosenTarget = chosenTarget;
    }

    public EPeriod getePeriod() {
        return ePeriod;
    }

    public void setePeriod(EPeriod ePeriod) {
        this.ePeriod = ePeriod;
    }

    public EQuarter geteQuarter() {
        return eQuarter;
    }

    public void seteQuarter(EQuarter eQuarter) {
        this.eQuarter = eQuarter;
    }

    public List<Target> getMonthlyTargets() {
        return monthlyTargets;
    }

    public void setMonthlyTargets(List<Target> monthlyTargets) {
        this.monthlyTargets = monthlyTargets;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public EMonth geteMonth() {
        return eMonth;
    }

    public void seteMonth(EMonth eMonth) {
        this.eMonth = eMonth;
    }

    public List<Division> getActiveDivisions() {
        return activeDivisions;
    }

    public void setActiveDivisions(List<Division> activeDivisions) {
        this.activeDivisions = activeDivisions;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<Account> getInstitutionAccounts() {
        return institutionAccounts;
    }

    public void setInstitutionAccounts(List<Account> institutionAccounts) {
        this.institutionAccounts = institutionAccounts;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<Other_Accomplishment> getDivisionOtherAccomplishments() {
        return divisionOtherAccomplishments;
    }

    public void setDivisionOtherAccomplishments(List<Other_Accomplishment> divisionOtherAccomplishments) {
        this.divisionOtherAccomplishments = divisionOtherAccomplishments;
    }

    public List<Accomplishment> getInstitutionAccomplishments() {
        return institutionAccomplishments;
    }

    public void setInstitutionAccomplishments(List<Accomplishment> institutionAccomplishments) {
        this.institutionAccomplishments = institutionAccomplishments;
    }

    public List<Other_Accomplishment> getInstitutionOtherAccomplishments() {
        return institutionOtherAccomplishments;
    }

    public void setInstitutionOtherAccomplishments(List<Other_Accomplishment> institutionOtherAccomplishments) {
        this.institutionOtherAccomplishments = institutionOtherAccomplishments;
    }

    public String getDepartmentFilterValue() {
        return departmentFilterValue;
    }

    public void setDepartmentFilterValue(String departmentFilterValue) {
        this.departmentFilterValue = departmentFilterValue;
    }

    public boolean isActivityFilterType() {
        return activityFilterType;
    }

    public void setActivityFilterType(boolean activityFilterType) {
        this.activityFilterType = activityFilterType;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public List<Division> getAllDivisions() {
        return allDivisions;
    }

    public void setAllDivisions(List<Division> allDivisions) {
        this.allDivisions = allDivisions;
    }

    public List<Target> getInstitutionMonthlyTargets() {
        return institutionMonthlyTargets;
    }

    public void setInstitutionMonthlyTargets(List<Target> institutionMonthlyTargets) {
        this.institutionMonthlyTargets = institutionMonthlyTargets;
    }

    public List<Project> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Project> allProjects) {
        this.allProjects = allProjects;
    }

    public List<Target> getAllMonthlyTargets() {
        return allMonthlyTargets;
    }

    public void setAllMonthlyTargets(List<Target> allMonthlyTargets) {
        this.allMonthlyTargets = allMonthlyTargets;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public List<Account> getDivisionUsers() {
        return divisionUsers;
    }

    public void setDivisionUsers(List<Account> divisionUsers) {
        this.divisionUsers = divisionUsers;
    }

    public List<Account> getGlobalUsers() {
        return globalUsers;
    }

    public void setGlobalUsers(List<Account> globalUsers) {
        this.globalUsers = globalUsers;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Date getFiscalYearDate() {
        return fiscalYearDate;
    }

    public void setFiscalYearDate(Date fiscalYearDate) {
        this.fiscalYearDate = fiscalYearDate;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionFilterValue() {
        return institutionFilterValue;
    }

    public void setInstitutionFilterValue(String institutionFilterValue) {
        this.institutionFilterValue = institutionFilterValue;
    }

    public List<Division> getInstitutionDivisions() {
        return institutionDivisions;
    }

    public void setInstitutionDivisions(List<Division> institutionDivisions) {
        this.institutionDivisions = institutionDivisions;
    }

}
