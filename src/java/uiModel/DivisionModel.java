package uiModel;

import dao.AccomplishmentDao;
import dao.IndicatorDao;
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
    private Target chosenTarget = new Target();
    private Accomplishment accomplishment = new Accomplishment();
    private List<Accomplishment> accomplishments = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private String week = new String();
    private String quarter = new String();
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
    private String month = new String();
    
    @PostConstruct
    public void init() {
        projects = new ProjectDao().findByDivision(loggedInUser.getDivision());
        loadReport();
    }
    
    public void loadIndicator(){
        indicators.clear();
        for(Indicator i: new IndicatorDao().findAll(Indicator.class)){
            if(i.getProject().getProjectId().equals(projectId)){
                indicators.add(i);
            }
        }
//        indicators = new ProjectDao().findOne(Project.class, projectId).getIndicator();
    }

    public void findQuarter(){
        Target t = new TargetDao().findOne(Target.class, targetId);
        System.out.println(t.getTargetTitle());
        quarter = t.getQuarter()+"";
    }
    
    public void deleteReport(Accomplishment acc){
        new AccomplishmentDao().delete(acc);
        loadReport();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Removed"));
    }
    
    public void loadTarget(){
        
//        targets.clear();
//        targets = new IndicatorDao().findOne(Indicator.class, indicatorId).getTarget();
        
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
        System.out.println(quarter);
        System.out.println(eQuarter);
        System.out.println(chosenIndicator.getIndicatorName());
        oneTarget = new TargetDao().findByIndicatorAndQuarter(chosenIndicator, eQuarter);
        System.out.println(oneTarget.getTargetTitle());
    }
    public void chooseTarget(Target target) {
        System.out.println(target.getTargetTitle());
        chosenTarget = target;
        accomplishments = new AccomplishmentDao().findByTarget(target);
    }
    
    public String chooseIndicator(Indicator i){        
        chosenIndicator = i;
//        accomplishments = new AccomplishmentDao().findByIndicatorAndQuarterAndPeriod(i, EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE);
        accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(eQuarter, ePeriod, chosenProject, eMonth);
        quarter = EQuarter.QUARTER_ONE+"";
        month = EMonth.MONTH_ONE+"";
        targets = new TargetDao().findByIndicator(i);
        oneTarget = new TargetDao().findByIndicatorAndQuarterAndMonth(i, EQuarter.QUARTER_ONE, EMonth.MONTH_ONE);
        return "weekly-report.xhtml?faces-redirect=true";
    }
    
    public void findTarget(){
        
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
            case "Week5":
                ePeriod = EPeriod.WEEK_FIVE;
                break;
            case "Week6":
                ePeriod = EPeriod.WEEK_SIX;
                break;
            case "Week7":
                ePeriod = EPeriod.WEEK_SEVEN;
                break;
            case "Week8":
                ePeriod = EPeriod.WEEK_EIGHT;
                break;
            case "Week9":
                ePeriod = EPeriod.WEEK_NINE;
                break;
            case "Week10":
                ePeriod = EPeriod.WEEK_TEN;
                break;
            case "Week11":
                ePeriod = EPeriod.WEEK_ELEVEN;
                break;
            case "Week12":
                ePeriod = EPeriod.WEEK_TWELVE;
                break;
            case "Week13":
                ePeriod = EPeriod.WEEK_THIRTEEN;
                break;
            case "Week14":
                ePeriod = EPeriod.WEEK_FOURTEEN;
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

    public void registerAccomplishment() {
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
            case "Week5":
                accomplishment.setPeriod(EPeriod.WEEK_FIVE);
                break;
            case "Week6":
                accomplishment.setPeriod(EPeriod.WEEK_SIX);
                break;
            case "Week7":
                accomplishment.setPeriod(EPeriod.WEEK_SEVEN);
                break;
            case "Week8":
                accomplishment.setPeriod(EPeriod.WEEK_EIGHT);
                break;
            case "Week9":
                accomplishment.setPeriod(EPeriod.WEEK_NINE);
                break;
            case "Week10":
                accomplishment.setPeriod(EPeriod.WEEK_TEN);
                break;
            case "Week11":
                accomplishment.setPeriod(EPeriod.WEEK_ELEVEN);
                break;
            case "Week12":
                accomplishment.setPeriod(EPeriod.WEEK_TWELVE);
                break;
            case "Week13":
                accomplishment.setPeriod(EPeriod.WEEK_THIRTEEN);
                break;
            case "Week14":
                accomplishment.setPeriod(EPeriod.WEEK_FOURTEEN);
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

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Submitted"));

    }

    public String navigateProject(Project p) {
        chosenProject = p;
        indicators = new IndicatorDao().findByProject(p);
//        targets = new TargetDao().findByProject(p);
        accomplishments = new AccomplishmentDao().findByDivisionAndProjectAndQuarterAndPeriodAndMonth(EQuarter.QUARTER_ONE, EPeriod.WEEK_ONE, p, EMonth.MONTH_ONE);
        return "weekly-view?faces-redirect=true";
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

}
