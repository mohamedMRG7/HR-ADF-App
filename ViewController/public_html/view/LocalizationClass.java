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
    private String language="ar";

    public LocalizationClass() {
        
        language=(getLange().contains("en") ? "عربى":"English");
        System.out.println(getLange());
    }

    public void refreshPage() {

    FacesContext fctx = FacesContext.getCurrentInstance();
    String page = fctx.getViewRoot().getViewId();
    ViewHandler ViewH = fctx.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fctx, page);
    UIV.setViewId(page);
    fctx.setViewRoot(UIV);

    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
    
    
    public void changeLanguage(ActionEvent actionEvent) {
        // Add event code here...
        
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
    
    public static void changeLange(String lang) {
        Locale local = new Locale(lang);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(local);
    }


    public static String getLange() {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale local = context.getViewRoot().getLocale();
        return local.toString();
    }
    
   
}
