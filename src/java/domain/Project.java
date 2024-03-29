
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author  Jean de Dieu HABIMANA @2020
 */
@Entity
public class Project implements Serializable {
    @Id
    private String projectId = UUID.randomUUID().toString();
    private String projectTitle;
    private String projectDescription;
    private Double projectBudget;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Boolean isInActionPlan;
    
    @ManyToOne
    private Division division;
    
//    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Indicator> indicator;
    
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Double getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(Double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Boolean getIsInActionPlan() {
        return isInActionPlan;
    }

    public void setIsInActionPlan(Boolean isInActionPlan) {
        this.isInActionPlan = isInActionPlan;
    }
    
    
}
