package engine.Entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Email
    private String email;
    @Size(min=5)
    private String password;
    private boolean active;
    private String roles;


    public User(){
        active = true;
        roles = "ROLE_USER";
    }

    public User(String email,String password) {
        this.email = email;
        this.password = password;
        active = true;
        roles = "ROLE_USER";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
