package view;

import java.io.Serializable;

import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class LocalizationClass implements Serializable {
    @SuppressWarnings("compatibility:-4599627600708035344")
    private static final long serialVersionUID = 1L;
    private String language="en";

    public LocalizationClass() {
        
        language=(getLange().contains("en") ? "عربى":"English");
      
    }

   /*
    * 
    * Setters and getters
    * 
    * */

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
    
    
    /*
     * 
     * 
     * Actions and Listners
     * 
     * 
     * */
    
    
    //Language button action listner
    public void changeLanguage(ActionEvent actionEvent) {
      
        //If the current language english convert it to arabic an vis versa
        if(getLange().equals("en"))
        {
        changeLange("ar");
        MainClass.setSessionVariable("lang", "ar");
        setLanguage("English");
        }else
        {
            changeLange("en");
            MainClass.setSessionVariable("lang", "en");
            setLanguage("عربى");
        }
        
        refreshPage();
        
    }
    
    
    /*
     * 
     * custom Methodes
     * 
     * */
    
    
    //chnage the locale language
    public static void changeLange(String lang) {
        Locale local = new Locale(lang);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(local);
    }

    //get the local language
    public static String getLange() {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale local = context.getViewRoot().getLocale();
        return local.toString();
    }
    
    public void refreshPage() {

    FacesContext fctx = FacesContext.getCurrentInstance();
    String page = fctx.getViewRoot().getViewId();
    ViewHandler ViewH = fctx.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fctx, page);
    UIV.setViewId(page);
    fctx.setViewRoot(UIV);

    }
}
