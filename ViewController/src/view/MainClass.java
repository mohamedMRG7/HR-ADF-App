package view;

import departments.DepartmentBindingIDS;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfdt.model.objects.IteratorBinding;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class MainClass {
    private boolean readOnlyView = true;
    private boolean edaitableView = false;
    private RichPanelFormLayout readOnlyForm;
    private RichPanelGroupLayout editableForm;
    private boolean searchViewEnabled = false;
    private RichPanelGroupLayout searchViewForm;
    private RichPopup deletePopUpView;

    public MainClass() {
        //get the value from the session to maintain the search view after click search or reset
        if (getSessionVariable("searchViewEnabled") != null) {
            searchViewEnabled = (Boolean) getSessionVariable("searchViewEnabled");
        }
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

    public void setSearchViewForm(RichPanelGroupLayout searchViewForm) {
        this.searchViewForm = searchViewForm;
    }

    public RichPanelGroupLayout getSearchViewForm() {
        return searchViewForm;
    }

    public void setSearchViewEnabled(boolean searchViewEnabled) {
        this.searchViewEnabled = searchViewEnabled;
    }

    public boolean getSearchViewEnabled() {
        return searchViewEnabled;
    }

    public void setDeletePopUpView(RichPopup deletePopUpView) {
        this.deletePopUpView = deletePopUpView;
    }

    public RichPopup getDeletePopUpView() {
        return deletePopUpView;
    }


    /*
     *
     *
     *
     *Action lisners And Actions
     *
     *
     *
     * */


    public void edite(ActionEvent actionEvent) {

        //Replace readOnly form with editable form when click on edite button
        setReadOnlyView(false);
        setEdaitableView(true);
        //Refresh the both form
        addPartialTrigger(getEditableForm());
        addPartialTrigger(getReadOnlyForm());


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


    public void openSearchView(ActionEvent actionEvent) {

        setSearchViewEnabled(true);
        //To maintain the seach view open after click search or reset
        setSessionVariable("searchViewEnabled", true);
        addPartialTrigger(getSearchViewForm());
    }

    public void cancelSearch(ActionEvent actionEvent) {
        // Add event code here...
        setSearchViewEnabled(false);
        setSessionVariable("searchViewEnabled", false);
        addPartialTrigger(getSearchViewForm());
    }

    public void resetDepartmentSearch(ActionEvent actionEvent) {
        // Add event code here...
        setAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_ID,null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_NAME,null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.MANAGER_ID,null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.LOCATION_ID,null);
    }

    public void departmentSearch(ActionEvent actionEvent) {
        // Add event code here...


        String departmentID = (String) getAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_ID);
        String departmentName = (String) getAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_NAME);
        String mangerId = (String) getAttributeBindingInputeValue(DepartmentBindingIDS.MANAGER_ID);
        String locationID = (String) getAttributeBindingInputeValue(DepartmentBindingIDS.LOCATION_ID);

        DCBindingContainer dcContainer = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding departmentiterator =
            (DCIteratorBinding) dcContainer.get(DepartmentBindingIDS.DEPARTMENTVO_ITERATOR);
        ViewObject departmentVO = departmentiterator.getViewObject();
        departmentVO.setNamedWhereClauseParam("P_DEPT_ID", departmentID);
        departmentVO.setNamedWhereClauseParam("P_DEPT_NAME", departmentName);
        departmentVO.setNamedWhereClauseParam("MANGER_ID", mangerId);
        departmentVO.setNamedWhereClauseParam("P_LOCATION_ID", locationID);

        departmentVO.executeQuery();

    }


    public void showdeletePopUp(ActionEvent actionEvent) {
        showPopUp(getDeletePopUpView());
    }


    public void delete(ActionEvent actionEvent) {
        // Add event code here...
        excuteBindingOperation("Delete");
        excuteBindingOperation("Commit");
        hidePopUp(getDeletePopUpView());
    }

    public void cancelPopUp(ActionEvent actionEvent) {
        // Add event code here...
        hidePopUp(getDeletePopUpView());
    }

    /*
     *
     *
     * Custom Methods
     *
     */


    //add partial trigger to component
    public void addPartialTrigger(UIComponent ui) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(ui);
    }

    public static void setSessionVariable(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static Object getSessionVariable(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static Object getAttributeBindingInputeValue(String bindingAtrrID) {
        BindingContext bContext = BindingContext.getCurrent();
        BindingContainer bContainer = bContext.getCurrentBindingsEntry();
        AttributeBinding attr = (AttributeBinding) bContainer.getControlBinding(bindingAtrrID);
        return attr.getInputValue();
    }
    
    public void setAttributeBindingInputeValue(String attrID,Object value)
    {
        BindingContext bContext = BindingContext.getCurrent();
        BindingContainer bContainer = bContext.getCurrentBindingsEntry();
        AttributeBinding attr = (AttributeBinding) bContainer.getControlBinding(attrID);
        attr.setInputValue(value);
    }

    public void excuteBindingOperation(String operationID) {
        BindingContext bContext = BindingContext.getCurrent();
        BindingContainer bContainer = bContext.getCurrentBindingsEntry();
        bContainer.getOperationBinding(operationID).execute();
    }

    public void showPopUp(RichPopup popUpCom) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popUpCom.show(hints);
    }

    public void hidePopUp(RichPopup popUpCom) {
        popUpCom.hide();
    }


}
