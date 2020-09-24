
package domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class Account implements Serializable {
    @Id
    private String userId = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EAccessLevel accessLevel;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    
    @ManyToOne
    private Division division;

    @ManyToOne
    private Institution institution;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(EAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    
    
}
