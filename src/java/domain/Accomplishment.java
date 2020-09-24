
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
 * @author  Jean de Dieu HABIMANA @2020
 */
@Entity
public class Accomplishment implements Serializable {
    @Id
    private String accomplishmentId = UUID.randomUUID().toString();
    @Column(length = 2048)
    private String accomplishment;
    @Column(length = 2048)
    private String challenge;
    @Column(length = 2048)
    private String remarks;
    @Enumerated(EnumType.STRING)
    private EPeriod period;
    
    @ManyToOne
    private Target target;

    public String getAccomplishmentId() {
        return accomplishmentId;
    }

    public void setAccomplishmentId(String accomplishmentId) {
        this.accomplishmentId = accomplishmentId;
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

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
    
    
}
