
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
    @Enumerated(EnumType.STRING)
    private EMonth month;
    
    @ManyToOne
    private Target target;
        
    @ManyToOne
    private Indicator indicator;
    
//    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Accomplishment> accomplishment;

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

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
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

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public EMonth getMonth() {
        return month;
    }

    public void setMonth(EMonth month) {
        this.month = month;
    }
    
    
}
