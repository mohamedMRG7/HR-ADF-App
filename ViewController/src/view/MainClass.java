package view;

import auth.AuthFilter;
import auth.UserData;

import departments.DepartmentBindingIDS;

import employees.EmployeeBindingIDS;

import oracle.jbo.domain.Date;
import java.util.Locale;

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
    private UserData userData;

    public MainClass() {
        //get the value from the session to maintain the search view after click search or reset
        if (getSessionVariable("searchViewEnabled") != null) {
            searchViewEnabled = (Boolean) getSessionVariable("searchViewEnabled");
        }
        userData = (UserData) getSessionVariable(AuthFilter.USER_INFO_SESSION_KEY);
        if(getSessionVariable("lang")!=null)
        LocalizationClass.changeLange(getSessionVariable("lang").toString());
    }


    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
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
        //Refresh the both department form form

        addPartialTrigger(getEditableForm());
        addPartialTrigger(getReadOnlyForm());


    }

    public String finishEdite() {
        // Add event code here...
        setReadOnlyView(true);
        setEdaitableView(false);

        //Refresh the both department form form

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
        setAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_ID, null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_NAME, null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.MANAGER_ID, null);
        setAttributeBindingInputeValue(DepartmentBindingIDS.LOCATION_ID, null);

        departmentSearch(null);
    }

    public void departmentSearch(ActionEvent actionEvent) {
        // Add event code here...

        /*DepartmentsCustomSearchVO  Binding Attruibutes */
        String departmentID = String.valueOf(getAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_ID));
        String departmentName = (String) getAttributeBindingInputeValue(DepartmentBindingIDS.DEPARTMENT_NAME);
        String mangerId = String.valueOf(getAttributeBindingInputeValue(DepartmentBindingIDS.MANAGER_ID));
        String locationID = String.valueOf(getAttributeBindingInputeValue(DepartmentBindingIDS.LOCATION_ID));

        /*Update employee iterator VO*/
       

        ViewObject departmentVO = getViewObjectFromIterator(DepartmentBindingIDS.DEPARTMENTVO_ITERATOR);
        departmentVO.setNamedWhereClauseParam("P_DEPT_ID", convertStringForSearch(departmentID));
        departmentVO.setNamedWhereClauseParam("P_DEPT_NAME", departmentName);
        departmentVO.setNamedWhereClauseParam("MANGER_ID", convertStringForSearch(mangerId));
        departmentVO.setNamedWhereClauseParam("P_LOCATION_ID", convertStringForSearch(locationID));

        departmentVO.executeQuery();

    }


    public void employeesSearch(ActionEvent actionEvent) {

        /*EmployeeCustomSearchVO  Binding Attruibutes */
        String employeeId = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.EMPLOYEE_ID);
        String firstName = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.FIRST_NAME);
        String lastName = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.LAST_NAME);
        String email = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.EMAIL);
        String phoneNum = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.PHONE_NUMBER);
        Date hireDate = (Date) getAttributeBindingInputeValue(EmployeeBindingIDS.HIRE_DATE);
        String jobId = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.JOB_ID);
        String salary = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.SALARY);
        String commition = (String) getAttributeBindingInputeValue(EmployeeBindingIDS.COMMITON_PCT);
        String managerId = String.valueOf(getAttributeBindingInputeValue(EmployeeBindingIDS.MANAGER_ID));
        String departmentID = String.valueOf(getAttributeBindingInputeValue(EmployeeBindingIDS.DEPARTMENT_ID));

        /*Update employee iterator VO*/
        ViewObject employeeVO = getViewObjectFromIterator(EmployeeBindingIDS.EMPLOYEEVO_ITERATOR);
        employeeVO.setNamedWhereClauseParam("P_EMP_ID", convertStringForSearch(employeeId));
        employeeVO.setNamedWhereClauseParam("P_FIRST_NAME", firstName);
        employeeVO.setNamedWhereClauseParam("P_LAST_NAME", lastName);
        employeeVO.setNamedWhereClauseParam("P_EMAIL", email);
        employeeVO.setNamedWhereClauseParam("P_PHONE_NUM", phoneNum);
        employeeVO.setNamedWhereClauseParam("P_HIRE_DATE", hireDate);
        employeeVO.setNamedWhereClauseParam("P_JOB_ID", jobId);
        employeeVO.setNamedWhereClauseParam("P_SALARY", convertStringForSearch(salary));
        employeeVO.setNamedWhereClauseParam("P_COMMITION", convertStringForSearch(commition));
        employeeVO.setNamedWhereClauseParam("P_MANAGER_ID", convertStringForSearch(managerId));
        employeeVO.setNamedWhereClauseParam("P_DEPT_ID", convertStringForSearch(departmentID));

        employeeVO.executeQuery();
    }

    public void resetEmployeeSearch(ActionEvent actionEvent) {
        // Add event code here...
        setAttributeBindingInputeValue(EmployeeBindingIDS.EMPLOYEE_ID, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.COMMITON_PCT, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.DEPARTMENT_ID, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.EMAIL, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.FIRST_NAME, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.HIRE_DATE, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.JOB_ID, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.LAST_NAME, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.MANAGER_ID, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.PHONE_NUMBER, null);
        setAttributeBindingInputeValue(EmployeeBindingIDS.SALARY, null);
        
        employeesSearch(null);

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
        DCBindingContainer bContainer = (DCBindingContainer) bContext.getCurrentBindingsEntry();
        AttributeBinding attr = (AttributeBinding) bContainer.getControlBinding(bindingAtrrID);
        return attr.getInputValue();
    }

    public void setAttributeBindingInputeValue(String attrID, Object value) {
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

    public static ViewObject getViewObjectFromIterator(String iteratorID) {
        DCBindingContainer dcContainer = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding employeeIterator = (DCIteratorBinding) dcContainer.get(iteratorID);
        ViewObject employeeVO = employeeIterator.getViewObject();

        System.out.println(employeeVO.getWhereClause());
        /*  for(int i=1; i<=employeeVO.getWhereClauseParams().length;i++)
        {
            employeeVO.setWhereClauseParam(i, null);
        }*/
        return employeeVO;
    }

    public Integer convertStringForSearch(String value) {
        try {
            Integer i = Integer.valueOf(value);
            return i;
        } catch (Exception e) {
            return null;
        }

    }


  


}
