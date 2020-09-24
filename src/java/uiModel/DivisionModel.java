
package uiModel;

import dao.AccomplishmentDao;
import dao.ProjectDao;
import dao.TargetDao;
import domain.Accomplishment;
import domain.Project;
import domain.Account;
import domain.EPeriod;
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
    
    @PostConstruct
    public void init(){
        projects = new ProjectDao().findByDivision(loggedInUser.getDivision());        
    }
    public void chooseTarget(Target target){
        System.out.println(target.getTargetTitle());
        chosenTarget = target;
        accomplishments = new AccomplishmentDao().findByTarget(target);
    }
    
    public void registerAccomplishment(){
        switch(week){
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
        accomplishment.setTarget(chosenTarget);
        new AccomplishmentDao().register(accomplishment);
        accomplishment = new Accomplishment();
        accomplishments = new AccomplishmentDao().findByTarget(chosenTarget);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Report Submitted"));
        
    }
    
    public String navigateProject(Project p){
        targets = new TargetDao().findByProject(p);
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
    
    
}
