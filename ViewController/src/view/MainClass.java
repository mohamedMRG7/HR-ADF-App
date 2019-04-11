package view;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

public class MainClass {
    private boolean readOnlyView=true;
    private boolean edaitableView=false;
    private RichPanelGroupLayout deptView;

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
        // Add event code here...
        setReadOnlyView(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getDeptView());
       
    }


  

    public void setDeptView(RichPanelGroupLayout deptView) {
        this.deptView = deptView;
    }

    public RichPanelGroupLayout getDeptView() {
        return deptView;
    }
}
