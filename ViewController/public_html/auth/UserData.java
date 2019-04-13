package auth;

public class UserData {
    private String usreName;
    private String email;
    private String password;
    
    

    public UserData(String usreName, String email, String password) {
        this.usreName = usreName;
        this.email = email;
        this.password = password;
    }

    public void setUsreName(String usreName) {
        this.usreName = usreName;
    }

    public String getUsreName() {
        return usreName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
