package view;

import java.io.Serializable;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class LocalizationClass implements Serializable {
    @SuppressWarnings("compatibility:-4599627600708035344")
    private static final long serialVersionUID = 1L;
    private String language="arabic";

    public LocalizationClass() {
    }

  

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
    
    
    public void changeLanguage(ActionEvent actionEvent) {
        // Add event code here...
        changeLange("ar");
    }
    
    
    /*
     * 
     * custom Methodes
     * 
     * */
    
    public void changeLange(String lang) {
        Locale local = new Locale(lang);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(local);
    }


    public String getLange() {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale local = context.getViewRoot().getLocale();
        return local.toString();
    }
}
