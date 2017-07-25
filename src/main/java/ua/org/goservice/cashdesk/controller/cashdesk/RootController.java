package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.auth.ScreenBlocker;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleController;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    static final String LOCATION = "/view/cashdesk/root.fxml";
    static final String TITLE = "GoService - CASH DESK 1.0";
    @FXML
    private Accordion accordion;

    private SaleController saleController;
    private ScreenBlocker screenBlocker;

    private OrganizationManager orgManager;
    private Warehouse warehouse;


    public void handleSynchronize() {
        
    }

    public void handleRefund() {

    }

    public void handleBlockScreen() {

    }

    public void handleLogOut() {

    }

    void setDependencies(Stage stage, ScreenBlocker screenBlocker, Employee employee) {
        this.screenBlocker = screenBlocker;
        orgManager = new OrganizationManager(employee.getOrgid());
        warehouse = new Warehouse(employee.getStoreid(), orgManager.getOurOrganization().getPriceid(), orgManager.getCurrentBuyer());
        saleController.setDependencies(stage, employee, warehouse, orgManager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load Report pane
        // load Refund pane
        // load Discount pane
        loadSalePane();
    }

    private void loadSalePane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SaleController.LOCATION));
            TitledPane salePane = loader.load();
            accordion.setExpandedPane(salePane);
            saleController = loader.getController();
            accordion.getPanes().add(salePane);
            accordion.setExpandedPane(salePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
