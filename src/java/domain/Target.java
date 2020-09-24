
package domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author  Jean de Dieu HABIMANA @2020
 */
@Entity
public class Target implements Serializable {
    @Id
    private String targetId = UUID.randomUUID().toString();
    private String targetTitle;
    private String targetDescription;
    @Enumerated(EnumType.STRING)
    private EQuarter quarter;
        
    @ManyToOne
    private Project project;
    
    @OneToMany(mappedBy = "target", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Accomplishment> accomplishment;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetDescription() {
        return targetDescription;
    }

    public void setTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Accomplishment> getAccomplishment() {
        return accomplishment;
    }

    public void setAccomplishment(List<Accomplishment> accomplishment) {
        this.accomplishment = accomplishment;
    }

    public EQuarter getQuarter() {
        return quarter;
    }

    public void setQuarter(EQuarter quarter) {
        this.quarter = quarter;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }
    
    
}
