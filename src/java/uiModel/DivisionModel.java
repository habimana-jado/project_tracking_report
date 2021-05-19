package uiModel;

import dao.AccomplishmentDao;
import dao.IndicatorDao;
import dao.Other_AccomplishmentDao;
import dao.ProjectDao;
import dao.TargetDao;
import domain.Accomplishment;
import domain.Project;
import domain.Account;
import domain.EMonth;
import static domain.EMonth.MONTH_ONE;
import static domain.EMonth.MONTH_THREE;
import static domain.EMonth.MONTH_TWO;
import domain.EPeriod;
import domain.EQuarter;
import domain.Indicator;
import domain.Other_Accomplishment;
import domain.Target;
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
public class DivisionModel {

    private Account loggedInUser = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private Project project = new Project();
    private List<Project> projects = new ArrayList<>();
    private List<Project> otherProjects = new ArrayList<>();
    private Target chosenTarget = new Target();
    private String activityType = "All";
    private Accomplishment accomplishment = new Accomplishment();
    private Accomplishment chosenAccomplishment = new Accomplishment();
    private Other_Accomplishment otherAccomplishment = new Other_Accomplishment();
    private List<Other_Accomplishment> compiledOtherAccomplishments = new ArrayList<>();
    private List<Accomplishment> accomplishments = new ArrayList<>();
    private List<Accomplishment> compiledAccomplishments = new ArrayList<>();
    private List<Other_Accomplishment> otherAccomplishments = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private String week = new String();
    private String quarter = "Quarter1";
    private String period = new String();
    private EPeriod ePeriod;
    private EQuarter eQuarter;
    private EMonth eMonth;
    private String targetId = new String();
    private Project chosenProject = new Project();
    private List<Indicator> indicators = new ArrayList<>();
    private String projectId = new String();
    private String indicatorId = new String();
    private Indicator chosenIndicator = new Indicator();
    private Target oneTarget = new Target();
    private Target quarterTarget = new Target();
    private String month = new String();
    private Other_Accomplishment chosenOtherAccomplishment = new Other_Accomplishment();

    @PostConstruct
    public void init() {
        compiledAccomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, loggedInUser.getDivision(), MONTH_ONE);
        compiledOtherAccomplishments = new Other_AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, loggedInUser.getDivision(), MONTH_ONE);
        projects = new ProjectDao().findByDivision(loggedInUser.getDivision());
        otherProjects = new ProjectDao().findByDivisionAndNotInActionPlan(loggedInUser.getDivision());
        loadReport();
    }

    public void loadIndicator() {
        indicators.clear();
        for (Indicator i : new IndicatorDao().findAll(Indicator.class)) {
            if (i.getProject().getProjectId().equals(projectId)) {
                indicators.add(i);
            }
        }
//        indicators = new ProjectDao().findOne(Project.class, projectId).getIndicator();
    }

    public void registerOtherProject() {
        project.setIsInActionPlan(Boolean.FALSE);
        project.setDivision(loggedInUser.getDivision());
        new ProjectDao().register(project);
        project = new Project();
        otherProjects = new ProjectDao().findByDivisionAndNotInActionPlan(loggedInUser.getDivision());

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Activity Registered"));
    }

    public void findQuarter() {
        Target t = new TargetDao().findOne(Target.class, targetId);
        quarter = t.getQuarter() + "";
    }

    public void chooseReport(Accomplishment acc) {
        chosenAccomplishment = acc;
    }

    public void chooseOtherReport(Other_Accomplishment acc) {
        chosenOtherAccomplishment = acc;
    }

    public void deleteReport(Accomplishment acc) {
        new AccomplishmentDao().delete(acc);
        loadReport();
        loadReportCompiled();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Removed"));
    }

    public void deleteOtherReport(Other_Accomplishment acc) {
        new Other_AccomplishmentDao().delete(acc);
        loadOtherReport();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Removed"));
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

    public void loadTarget() {

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
        oneTarget = new TargetDao().findByIndicatorAndQuarter(chosenIndicator, eQuarter);
    }

    public void chooseTarget(Target target) {
        chosenTarget = target;
        accomplishments = new AccomplishmentDao().findByTarget(target);
    }

    public String chooseIndicator(Indicator i) {
        chosenIndicator = i;
        compiledAccomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, loggedInUser.getDivision(), MONTH_ONE);
        accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);
        quarter = EQuarter.QUARTER_ONE + "";
        month = EMonth.MONTH_ONE + "";
        targets = new TargetDao().findByIndicator(i);
        quarterTarget = new TargetDao().findByIndicatorAndQuarter(i, EQuarter.QUARTER_ONE);
        oneTarget = new TargetDao().findByIndicatorAndQuarterAndMonth(i, EQuarter.QUARTER_ONE, EMonth.MONTH_ONE);
        return "weekly-report.xhtml?faces-redirect=true";
    }

    public void findTarget() {

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

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        switch (month) {
            case "MONTH_ONE":
                eMonth = MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = MONTH_THREE;
                break;

            default:
                eMonth = MONTH_ONE;
                break;

        }
        oneTarget = new TargetDao().findByIndicatorAndQuarterAndMonth(chosenIndicator, eQuarter, eMonth);
        quarterTarget = new TargetDao().findByIndicatorAndQuarter(chosenIndicator, eQuarter);
    }

    public void loadReport() {
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
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);

    }

    public void loadReportCompiled() {
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
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        compiledAccomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, loggedInUser.getDivision(), eMonth);

    }

    public void filterReport() {
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
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        accomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, loggedInUser.getDivision(), eMonth);

    }

    public void loadOtherReport() {
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
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        otherAccomplishments = new Other_AccomplishmentDao().findByProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);

    }

    public void loadOtherReportCompiled() {
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
            case "MONTH_ONE":
                eMonth = EMonth.MONTH_ONE;
                break;

            case "MONTH_TWO":
                eMonth = EMonth.MONTH_TWO;
                break;

            case "MONTH_THREE":
                eMonth = EMonth.MONTH_THREE;
                break;

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }
        compiledOtherAccomplishments = new Other_AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, loggedInUser.getDivision(), eMonth);

    }

    public void registerAccomplishment() {
        if (oneTarget == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("No Monthly Target Available for " + month));
            return;
        }
        switch (week) {
            case "Week1":
                accomplishment.setPeriod(EPeriod.WEEK_ONE);
                break;
            case "Week2":
                accomplishment.setPeriod(EPeriod.WEEK_TWO);
                break;
            case "Week3":
                accomplishment.setPeriod(EPeriod.WEEK_THREE);
                break;
            case "Week4":
                accomplishment.setPeriod(EPeriod.WEEK_FOUR);
                break;
        }
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

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        accomplishment.setTarget(oneTarget);
        new AccomplishmentDao().register(accomplishment);
        accomplishment = new Accomplishment();
        loadReport();
        compiledAccomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, loggedInUser.getDivision(), MONTH_ONE);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Submitted"));

    }

    public void updateAccomplishment() {
        if (oneTarget == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("No Monthly Target Available for " + month));
            return;
        }
        switch (week) {
            case "Week1":
                chosenAccomplishment.setPeriod(EPeriod.WEEK_ONE);
                break;
            case "Week2":
                chosenAccomplishment.setPeriod(EPeriod.WEEK_TWO);
                break;
            case "Week3":
                chosenAccomplishment.setPeriod(EPeriod.WEEK_THREE);
                break;
            case "Week4":
                chosenAccomplishment.setPeriod(EPeriod.WEEK_FOUR);
                break;
        }
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

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;

        }

        chosenAccomplishment.setTarget(oneTarget);
        new AccomplishmentDao().update(chosenAccomplishment);
        chosenAccomplishment = new Accomplishment();
        loadReport();
        compiledAccomplishments = new AccomplishmentDao().findByDivisionAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, loggedInUser.getDivision(), MONTH_ONE);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Updated"));

    }

    public void registerOtherAccomplishment() {
        switch (week) {
            case "Week1":
                otherAccomplishment.setPeriod(EPeriod.WEEK_ONE);
                break;
            case "Week2":
                otherAccomplishment.setPeriod(EPeriod.WEEK_TWO);
                break;
            case "Week3":
                otherAccomplishment.setPeriod(EPeriod.WEEK_THREE);
                break;
            case "Week4":
                otherAccomplishment.setPeriod(EPeriod.WEEK_FOUR);
                break;
        }
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

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;
        }

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

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        otherAccomplishment.setQuarter(eQuarter);
        otherAccomplishment.setMonth(eMonth);
        otherAccomplishment.setProject(chosenProject);
        new Other_AccomplishmentDao().register(otherAccomplishment);
        otherAccomplishment = new Other_Accomplishment();

        otherAccomplishments = new Other_AccomplishmentDao().findByProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Submitted"));

    }

    public void updateOtherAccomplishment() {
        switch (week) {
            case "Week1":
                chosenOtherAccomplishment.setPeriod(EPeriod.WEEK_ONE);
                break;
            case "Week2":
                chosenOtherAccomplishment.setPeriod(EPeriod.WEEK_TWO);
                break;
            case "Week3":
                chosenOtherAccomplishment.setPeriod(EPeriod.WEEK_THREE);
                break;
            case "Week4":
                chosenOtherAccomplishment.setPeriod(EPeriod.WEEK_FOUR);
                break;
        }
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

            default:
                eQuarter = EQuarter.QUARTER_ONE;
                break;
        }

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

            default:
                eMonth = EMonth.MONTH_ONE;
                break;

        }

        chosenOtherAccomplishment.setQuarter(eQuarter);
        chosenOtherAccomplishment.setMonth(eMonth);
        chosenOtherAccomplishment.setProject(chosenProject);
        new Other_AccomplishmentDao().update(chosenOtherAccomplishment);
        chosenOtherAccomplishment = new Other_Accomplishment();

        otherAccomplishments = new Other_AccomplishmentDao().findByProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Updated"));

    }

    public String navigateProject(Project p) {
        chosenProject = p;
        indicators = new IndicatorDao().findByProject(p);
//        targets = new TargetDao().findByProject(p);
        accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, p, EMonth.MONTH_ONE);
        return "weekly-view?faces-redirect=true";
    }

    public String navigateOtherProject(Project p) {
        chosenProject = p;
//        otherAccomplishments = new Other_AccomplishmentDao().findByProjectAndNotInActionPlan(p);
        otherAccomplishments = new Other_AccomplishmentDao().findByProjectAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, p, EMonth.MONTH_ONE);
        return "weekly-other-report?faces-redirect=true";
    }

    public void chooseProject(Project u) {
        project = u;
    }

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Account loggedInUser) {
        this.loggedInUser = loggedInUser;
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

    public Target getChosenTarget() {
        return chosenTarget;
    }

    public void setChosenTarget(Target chosenTarget) {
        this.chosenTarget = chosenTarget;
    }

    public Accomplishment getAccomplishment() {
        return accomplishment;
    }

    public void setAccomplishment(Accomplishment accomplishment) {
        this.accomplishment = accomplishment;
    }

    public List<Accomplishment> getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(List<Accomplishment> accomplishments) {
        this.accomplishments = accomplishments;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Project getChosenProject() {
        return chosenProject;
    }

    public void setChosenProject(Project chosenProject) {
        this.chosenProject = chosenProject;
    }

    public Indicator getChosenIndicator() {
        return chosenIndicator;
    }

    public void setChosenIndicator(Indicator chosenIndicator) {
        this.chosenIndicator = chosenIndicator;
    }

    public Target getOneTarget() {
        return oneTarget;
    }

    public void setOneTarget(Target oneTarget) {
        this.oneTarget = oneTarget;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Target getQuarterTarget() {
        return quarterTarget;
    }

    public void setQuarterTarget(Target quarterTarget) {
        this.quarterTarget = quarterTarget;
    }

    public List<Project> getOtherProjects() {
        return otherProjects;
    }

    public void setOtherProjects(List<Project> otherProjects) {
        this.otherProjects = otherProjects;
    }

    public EMonth geteMonth() {
        return eMonth;
    }

    public void seteMonth(EMonth eMonth) {
        this.eMonth = eMonth;
    }

    public List<Other_Accomplishment> getOtherAccomplishments() {
        return otherAccomplishments;
    }

    public void setOtherAccomplishments(List<Other_Accomplishment> otherAccomplishments) {
        this.otherAccomplishments = otherAccomplishments;
    }

    public Other_Accomplishment getOtherAccomplishment() {
        return otherAccomplishment;
    }

    public void setOtherAccomplishment(Other_Accomplishment otherAccomplishment) {
        this.otherAccomplishment = otherAccomplishment;
    }

    public List<Accomplishment> getCompiledAccomplishments() {
        return compiledAccomplishments;
    }

    public void setCompiledAccomplishments(List<Accomplishment> compiledAccomplishments) {
        this.compiledAccomplishments = compiledAccomplishments;
    }

    public List<Other_Accomplishment> getCompiledOtherAccomplishments() {
        return compiledOtherAccomplishments;
    }

    public void setCompiledOtherAccomplishments(List<Other_Accomplishment> compiledOtherAccomplishments) {
        this.compiledOtherAccomplishments = compiledOtherAccomplishments;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Accomplishment getChosenAccomplishment() {
        return chosenAccomplishment;
    }

    public void setChosenAccomplishment(Accomplishment chosenAccomplishment) {
        this.chosenAccomplishment = chosenAccomplishment;
    }

    public Other_Accomplishment getChosenOtherAccomplishment() {
        return chosenOtherAccomplishment;
    }

    public void setChosenOtherAccomplishment(Other_Accomplishment chosenOtherAccomplishment) {
        this.chosenOtherAccomplishment = chosenOtherAccomplishment;
    }

}
