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
import java.util.ArrayList;
import java.util.List;
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
    private Account user = new Account();
    private String password = new String();
    private String departmentFilterValue = new String();
    private boolean activityFilterType = true;
    private String activityType = "All";
    private List<Account> users = new ArrayList<>();
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
    private List<Accomplishment> institutionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(EQuarter.QUARTER_ONE, EMonth.MONTH_ONE, EPeriod.WEEK_ONE);
    private List<Other_Accomplishment> institutionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(EQuarter.QUARTER_ONE, EMonth.MONTH_ONE, EPeriod.WEEK_ONE);
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

    @PostConstruct
    public void init() {
        divisionAccomplishments = new AccomplishmentDao().findByQuarterAndMonthAndPeriod(EQuarter.QUARTER_ONE, EMonth.MONTH_ONE, EPeriod.WEEK_ONE, loggedInUser.getInstitution());
        divisionOtherAccomplishments = new Other_AccomplishmentDao().findByQuarterAndMonthAndPeriod(EQuarter.QUARTER_ONE, EMonth.MONTH_ONE, EPeriod.WEEK_ONE, loggedInUser.getInstitution());
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        users = new UserDao().findByAccessLevel(EAccessLevel.DIVISION_MANAGER);
        projects = new ProjectDao().findAll(Project.class);
        loadReport();
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

    public void registerDivisionManager() throws Exception {
        if (new UserDao().findOne(Account.class, user.getUserId()) != null) {
            user.setPassword(new PassCode().encrypt(password));
            Division div = new DivisionDao().findOne(Division.class, divisionId);
            user.setDivision(div);
            new UserDao().update(user);
            user = new Account();
            users = new UserDao().findByInstitution(loggedInUser.getInstitution());
            institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
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
                users = new UserDao().findByInstitution(loggedInUser.getInstitution());
                institutionAccounts = new UserDao().findByAccessLevel(EAccessLevel.INSTITUTION_MANAGER);
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

    public void blockDivision(Division a) {
        a.setStatus(EStatus.INACTIVE);
        new DivisionDao().update(a);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        activeDivisions = new DivisionDao().findByInstitutionAndStatus(loggedInUser.getInstitution(), EStatus.ACTIVE);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Division Blocked"));
    }

    public void activateDivision(Division a) {
        a.setStatus(EStatus.ACTIVE);
        new DivisionDao().update(a);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
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

    public void registerProject() {
        Division d = new DivisionDao().findOne(Division.class, divisionId);
        project.setDivision(d);
        project.setIsInActionPlan(Boolean.TRUE);
        new ProjectDao().register(project);
        registeredProject = project;
        project = new Project();
        projects = new ProjectDao().findByInstitution(loggedInUser.getInstitution());

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
        target.setIndicator(chosenIndicator);
        switch (quarter) {
            case "QUARTER_ONE":
                target.setQuarter(EQuarter.QUARTER_ONE);
                break;

            case "QUARTER_TWO":
                target.setQuarter(EQuarter.QUARTER_TWO);
                break;

            case "QUARTER_THREE":
                target.setQuarter(EQuarter.QUARTER_THREE);
                break;

            case "QUARTER_FOUR":
                target.setQuarter(EQuarter.QUARTER_FOUR);
                break;
        }

        new TargetDao().register(target);
        target = new Target();
        targets = new TargetDao().findByIndicator(chosenIndicator);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Target Added"));
    }

    public void registerMonthlyTarget() {
        target.setTarget(chosenTarget);
        switch (month) {
            case "MONTH_ONE":
                target.setMonth(EMonth.MONTH_ONE);
                break;

            case "MONTH_TWO":
                target.setMonth(EMonth.MONTH_TWO);
                break;

            case "MONTH_THREE":
                target.setMonth(EMonth.MONTH_THREE);
                break;

        }
        target.setQuarter(chosenTarget.getQuarter());
        new TargetDao().register(target);
        target = new Target();
        monthlyTargets = new TargetDao().findByTarget(chosenTarget);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Target Added"));
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

}
