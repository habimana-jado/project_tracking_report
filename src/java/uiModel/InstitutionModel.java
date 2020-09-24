package uiModel;

import common.PassCode;
import dao.AccomplishmentDao;
import dao.DivisionDao;
import dao.IndicatorDao;
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
import domain.EQuarter;
import domain.Indicator;
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
    private Project project = new Project();
    private List<Project> projects = new ArrayList<>();
    private Account user = new Account();
    private String password = new String();
    private List<Account> users = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private List<Accomplishment> accomplishments = new ArrayList<>();
    private String divisionId = new String();
    private Division chosenDivision = new Division();
    private List<Indicator> indicators = new ArrayList<>();
    private Indicator indicator = new Indicator();
    private String quarter = new String();
    private Project registeredProject = new Project();
    private Target target = new Target();
    private List<Accomplishment> divisionAccomplishments = new AccomplishmentDao().findAll(Accomplishment.class);
    private Indicator chosenIndicator = new Indicator();
    
    @PostConstruct
    public void init() {
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        users = new UserDao().findByInstitution(loggedInUser.getInstitution());
        divisionAccomplishments = new AccomplishmentDao().findAll(Accomplishment.class);
        projects = new ProjectDao().findAll(Project.class);
    }

    public void registerDivisionManager() throws Exception {
        user.setAccessLevel(EAccessLevel.DIVISION_MANAGER);
        user.setStatus(EStatus.ACTIVE);
        user.setPassword(new PassCode().encrypt(password));
        Division div = new DivisionDao().findOne(Division.class, divisionId);
        user.setDivision(div);
        new UserDao().register(user);
        user = new Account();
        users = new UserDao().findByInstitution(loggedInUser.getInstitution());
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Division Manager Registered"));
    }

    public void registerDivision() {
        division.setInstitution(loggedInUser.getInstitution());
        new DivisionDao().register(division);
        divisions = new DivisionDao().findByInstitution(loggedInUser.getInstitution());
        division = new Division();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Division Registered"));
    }

    public String navigateDivision(Division div) {
        chosenDivision = div;
        return "project.xhtml?faces-redirect=true";
    }

    public void registerProject() {
        Division d = new DivisionDao().findOne(Division.class, divisionId);
        project.setDivision(d);
        new ProjectDao().register(project);
        registeredProject = project;
        project = new Project();
        projects = new ProjectDao().findAll(Project.class);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Project Registered"));
    }

    public void registerIndicator() {
        indicator.setProject(registeredProject);
        switch (quarter) {
            case "QUARTER_ONE":
                indicator.setQuarter(EQuarter.QUARTER_ONE);
                break;

            case "QUARTER_TWO":
                indicator.setQuarter(EQuarter.QUARTER_TWO);
                break;

            case "QUARTER_THREE":
                indicator.setQuarter(EQuarter.QUARTER_THREE);
                break;

            case "QUARTER_FOUR":
                indicator.setQuarter(EQuarter.QUARTER_FOUR);
                break;
        }
        
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

    public String navigateProject(Project p){
        registeredProject = p;
        indicators = new IndicatorDao().findByProject(registeredProject);
        return "target.xhtml?faces-redirect=true";
    } 
    
    public String navigateIndicator(Indicator ind){
        chosenIndicator = ind;
        targets = new TargetDao().findByIndicator(ind);
        return "indicator.xhtml?faces-redirect=true";        
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

}
