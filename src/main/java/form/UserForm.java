package form;


import form.contraints.PasswordMatches;
import form.contraints.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserForm {
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @NotNull
    private String password;

    private String matchingPassword;

    @NotNull
    @ValidEmail
    @Email
    private String email;

    @NotNull
    private String[] role;

    public String getUsername() {
        return username;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
