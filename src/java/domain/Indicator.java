
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
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class Indicator implements Serializable{
    @Id
    private String indicatorId = UUID.randomUUID().toString();
    private String indicatorName;
    private String description;
    private String remarks;
    @Enumerated(EnumType.STRING)
    private EQuarter quarter;
    private String baseline;
    
    @OneToMany(mappedBy = "indicator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Target> target;
    
    @ManyToOne
    private Project project;

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public EQuarter getQuarter() {
        return quarter;
    }

    public void setQuarter(EQuarter quarter) {
        this.quarter = quarter;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Target> getTarget() {
        return target;
    }

    public void setTarget(List<Target> target) {
        this.target = target;
    }

    public String getBaseline() {
        return baseline;
    }

    public void setBaseline(String baseline) {
        this.baseline = baseline;
    }
    
    
}
