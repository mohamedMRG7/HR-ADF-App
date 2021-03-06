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
    
    
    /*
     * 
     * Getters and setters
     * 
     * */
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
    
    
    /*
     * 
     * 
     * Actions and actionListners
     * 
     * */
    
    public String logIn() {
        // Add event code here...
        //get userInfo view object from the iterator and apply its criteria
        ViewObject userVO = MainClass.getViewObjectFromIterator("UserInfoIterator");
        UserData userInfo = null;
        userVO.setNamedWhereClauseParam("P_USER_NAME", getUserName());
        userVO.setNamedWhereClauseParam("P_PASSWORD", getPassword());
        userVO.executeQuery();
        
        //Check if the VO get data and userName ,password fields are not null  
        if (userVO.hasNext()&&(userName!=null||password!=null)) {
            Row row = userVO.next();
            userInfo =
                new UserData((String) row.getAttribute("UserName"), (String) row.getAttribute("Email"),
                             (String) row.getAttribute("Password"));
            
            //set Log in session attribute to true , and fill the userInfo attribute with the new userInfo
            MainClass.setSessionVariable(AuthFilter.LOG_IN_SESSION_KEY, true);
            MainClass.setSessionVariable(AuthFilter.USER_INFO_SESSION_KEY, userInfo);

            return "departments";
        } else {
            //if no data back show error message
            FacesMessage errorMessage =
                new FacesMessage("Log in error", "User name or password is not correct , please try again");
            errorMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
            return null;
        }


    }

    public String logOut() {
        //set Log in session attribute to false , and fill the userInfo attribute by null 
        MainClass.setSessionVariable(AuthFilter.LOG_IN_SESSION_KEY, false);
        MainClass.setSessionVariable(AuthFilter.USER_INFO_SESSION_KEY, null);
        
        return "departments";
    }
}
