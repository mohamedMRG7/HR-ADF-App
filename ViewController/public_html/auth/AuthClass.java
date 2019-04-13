package auth;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import view.MainClass;

public class AuthClass {


    private String userName="";
    private String password="";

    public AuthClass() {
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public String logIn() {
        // Add event code here...
        ViewObject userVO = MainClass.getViewObjectFromIterator("UserInfoIterator");

        UserData userInfo = null;
        userVO.setNamedWhereClauseParam("P_USER_NAME", getUserName());
        userVO.setNamedWhereClauseParam("P_PASSWORD", getPassword());
        userVO.executeQuery();

        if (userVO.hasNext()) {
            Row row = userVO.next();
            userInfo =
                new UserData((String) row.getAttribute("UserName"), (String) row.getAttribute("Email"),
                             (String) row.getAttribute("Password"));
            MainClass.setSessionVariable(AuthFilter.LOG_IN_SESSION_KEY, true);
            MainClass.setSessionVariable(AuthFilter.USER_INFO_SESSION_KEY, userInfo);

            return "departments";
        } else {
            FacesMessage errorMessage =
                new FacesMessage("Log in error", "User name or password is not correct , please try again");
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
            return null;
        }


    }

   

    public String logOut() {
        
        MainClass.setSessionVariable(AuthFilter.LOG_IN_SESSION_KEY, false);
        MainClass.setSessionVariable(AuthFilter.USER_INFO_SESSION_KEY, null);
        System.out.println("Hey");
        
        return "departments";
    }
}
