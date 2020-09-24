package uiModel;

import dao.UserDao;
import domain.Account;
import domain.EStatus;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class AccountModel {

    private Account user = new Account();

    private List<Account> users;

    private String username = new String();

    private String password = new String();

    private Account u = new Account();


    public String login() throws IOException, Exception {
        findAccount();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (user != null && user.getStatus().equals(EStatus.ACTIVE)) {

            switch (user.getAccessLevel()) {
                case GLOBAL:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/admin/addfaculty.xhtml");
                    return "pages/admin/addfaculty.xhtml?faces-redirect=true";
                case INSTITUTION_MANAGER:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/institution/users.xhtml");
                    return "faces/institution/users?faces-redirect=true";
                case DIVISION_MANAGER:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/faces/institution/project-details.xhtml");
                    return "faces/institution/project-details.xhtml?faces-redirect=true";
                default:
                    user = null;

                    ec.redirect(ec.getRequestContextPath() + "/index.xhtml");

                    return "/QuarterlyTarget/index.xhtml";
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong Password Or Accountname"));
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
            return "index.xhtml";
        }

    }

    public void findAccount() throws Exception {
        List<Account> usersLogin = new UserDao().loginencrypt(username, password);

        if (!usersLogin.isEmpty()) {
            for (Account u : usersLogin) {
                user = u;
            }
        } else {
            user = null;
        }
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = null;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
    }

    public Account getAccount() {
        return user;
    }

    public void setAccount(Account user) {
        this.user = user;
    }

    public List<Account> getAccounts() {
        return users;
    }

    public void setAccounts(List<Account> users) {
        this.users = users;
    }

    public String getAccountname() {
        return username;
    }

    public void setAccountname(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Account getU() {
        return u;
    }

    public void setU(Account u) {
        this.u = u;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
