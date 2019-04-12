package view;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class MainClass {
    private boolean readOnlyView=true;
    private boolean edaitableView=false;
    private RichPanelFormLayout readOnlyForm;
    private RichPanelGroupLayout editableForm;

    public MainClass() {
    }

    public void setReadOnlyView(boolean readOnlyView) {
        this.readOnlyView = readOnlyView;
    }

    public boolean isReadOnlyView() {
        return readOnlyView;
    }

    public void setEdaitableView(boolean edaitableView) {
        this.edaitableView = edaitableView;
    }

    public boolean isEdaitableView() {
        return edaitableView;
    }

    public void edite(ActionEvent actionEvent) {
        
        //Replace readOnly form with editable form when click on edite button 
        setReadOnlyView(false);
        setEdaitableView(true);
        //Refresh the both form
        addPartialTrigger(getEditableForm());
        addPartialTrigger(getReadOnlyForm());
      
       
    }

    public void setReadOnlyForm(RichPanelFormLayout readOnlyForm) {
        this.readOnlyForm = readOnlyForm;
    }

    public RichPanelFormLayout getReadOnlyForm() {
        return readOnlyForm;
    }

    public void setEditableForm(RichPanelGroupLayout editableForm) {
        this.editableForm = editableForm;
    }

    public RichPanelGroupLayout getEditableForm() {
        return editableForm;
    }
    
    //add partial trigger to component 
    public void addPartialTrigger(UIComponent ui)
    {
         AdfFacesContext.getCurrentInstance().addPartialTarget(ui);
    }

   

    public String finishEdite() {
        // Add event code here...
        setReadOnlyView(true);
        setEdaitableView(false);
        //Refresh the both form
        addPartialTrigger(getEditableForm());
        addPartialTrigger(getReadOnlyForm());
        return null;
    }
}
