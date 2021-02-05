
package domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jean de Dieu HABIMANA @2021
 */
@Entity
public class Other_Accomplishment implements Serializable {
    @Id
    private String otherAccomplishmentId = UUID.randomUUID().toString();
    @Column(length = 2048)
    private String accomplishment;
    @Column(length = 2048)
    private String challenge;
    @Column(length = 2048)
    private String remarks;
    @Enumerated(EnumType.STRING)
    private EPeriod period;
    private String nextWeekPlan;
    @Enumerated(EnumType.STRING)
    private EQuarter quarter;
    @Enumerated(EnumType.STRING)
    private EMonth month;
    
    @ManyToOne
    private Project project;

    public String getOtherAccomplishmentId() {
        return otherAccomplishmentId;
    }

    public void setOtherAccomplishmentId(String otherAccomplishmentId) {
        this.otherAccomplishmentId = otherAccomplishmentId;
    }

    public String getAccomplishment() {
        return accomplishment;
    }

    public void setAccomplishment(String accomplishment) {
        this.accomplishment = accomplishment;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public EPeriod getPeriod() {
        return period;
    }

    public void setPeriod(EPeriod period) {
        this.period = period;
    }

    public String getNextWeekPlan() {
        return nextWeekPlan;
    }

    public void setNextWeekPlan(String nextWeekPlan) {
        this.nextWeekPlan = nextWeekPlan;
    }

    public EQuarter getQuarter() {
        return quarter;
    }

    public void setQuarter(EQuarter quarter) {
        this.quarter = quarter;
    }

    public EMonth getMonth() {
        return month;
    }

    public void setMonth(EMonth month) {
        this.month = month;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
}
