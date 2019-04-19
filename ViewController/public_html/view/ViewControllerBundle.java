package view;

import java.util.ListResourceBundle;

public class ViewControllerBundle extends ListResourceBundle {
    private static final Object[][] contents = {
        //
        { "SELECTED_DEPARTMENT", "Selected Department" },
        //
        { "RESET", "Reset" },
        //
        { "CANCEL", "Cancel" },
        //
        { "SAVE", "Save" },
        //
        { "ROLL_BACK", "Roll back" },
        //
        { "DELETE_DEPARTMENT", "Delete department" },
        //
        { "DELETE", "Delete" },
        //
        { "SEARCH", "Search" },
        //
        { "DEPARTMENTS", "Departments" },
        //
        { "ADD_DEPARTMENT", "Add department" },
        //
        { "DEPARTMENT", "Department" },
        //
        { "ADD", "Add" },
        //
        { "DELETE_EMPLOYEE", "Delete employee" },
        //
        { "SELECTED_EMPLOYEE", "Selected employee" },
        //
        { "EMPLOYEES", "Employees" },
        //
        { "EMPLOYEE", "Employee" },
        //
        { "ADD_EMPLOYEE", "Add employee" },
        //
        { "SEARCH_AND_SELECT", "Search and select" }



    };

    public Object[][] getContents() {
        return contents;
    }
}
