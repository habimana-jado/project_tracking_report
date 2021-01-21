
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
public class Division implements Serializable {
    @Id
    private String divisionId = UUID.randomUUID().toString();
    private String divisionName;
    private String divisionDescription;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    
    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Account> user;
    
    @ManyToOne
    private Institution institution;
    
    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Project> project;

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDivisionDescription() {
        return divisionDescription;
    }

    public void setDivisionDescription(String divisionDescription) {
        this.divisionDescription = divisionDescription;
    }

    public List<Account> getUser() {
        return user;
    }

    public void setUser(List<Account> user) {
        this.user = user;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }
    
    
}
