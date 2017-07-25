package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ua.org.goservice.cashdesk.controller.control.Alert;
import ua.org.goservice.cashdesk.controller.control.AlertHeader;
import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.exception.CancelOperationException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.util.Validator;
import ua.org.goservice.cashdesk.model.warehouse.Product;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class SaleController implements Initializable {
    public static final String LOCATION = "/view/cashdesk/sale.fxml";
    @FXML
    private JFXTextField productSearchField;
    @FXML
    private JFXComboBox<Organization> buyersChoiceBox;
    /**
     * Draft table
     */
    @FXML
    private TableView<DraftEntry> productTable;
    @FXML
    private TableColumn<DraftEntry, String> barcodeColumn;
    @FXML
    private TableColumn<DraftEntry, String> nameColumn;
    @FXML
    private TableColumn<DraftEntry, String> measureColumn;
    @FXML
    private TableColumn<DraftEntry, BigDecimal> priceColumn;
    @FXML
    private TableColumn<DraftEntry, BigDecimal> countColumn;
    @FXML
    private TableColumn<DraftEntry, BigDecimal> totalResultColumn;
    /**
     * Current cash-memo information
     */
    @FXML
    private Label checkSum;
    @FXML
    private Label discountRate;
    @FXML
    private Label bonusesAccrued;
    /**
     * Funds contribution fields
     */
    @FXML
    private JFXTextField contributedCashFund;
    @FXML
    private JFXTextField contributedTerminalFund;
    @FXML
    private JFXTextField contributedBonusFund;
    /**
     * Cash-memo results fields
     */
    @FXML
    private Label toPay;
    @FXML
    private Label contributedTotalFunds;
    @FXML
    private Label oddsType;
    @FXML
    private Label odds;
    /**
     * Discount card fields
     */
    @FXML
    private JFXTextField discountCardOwner;
    @FXML
    private JFXTextField discountCardNumber;
    @FXML
    private JFXTextField discountCardType;
    @FXML
    private JFXTextField discountCardBalance;

    private FXMLLoader productCountDialogLoader;
    private Stage stage;

    private Employee employee;
    private Warehouse warehouse;
    private OrganizationManager organizationManager;
    private Draft draft;

    private final SaleUIAssistant uiAssistant = new SaleUIAssistant();

    @FXML
    private void handleAddToDraft() {
        if (productSearchField.getText().length() == 0) return;
        try {
            Product product = parseSearchField();
            if (draft == null) createNewDraft();
            BigDecimal actualProductCount = warehouse.getActualProductCount(product);
            BigDecimal desiredCount = callCountDialog(new ProductCountValidator(
                    product.getMeasures(), actualProductCount), actualProductCount);
            draft.addProduct(product, warehouse.getPriceByBarcode(product.getBarcode()), desiredCount);
        } catch (IllegalArgumentException | CancelOperationException e) {
            if (e instanceof IllegalArgumentException) {
                new Alert().callAlert(AlertHeader.ADD_PRODUCT_ERROR, e.getMessage());
            }
        }
    }

    private Product parseSearchField() {
        Product product = warehouse.getProductByName(productSearchField.getText());
        if (product == null) throw new IllegalArgumentException(Exceptions.PRODUCT_NOT_FOUND);
        return product;
    }

    private void createNewDraft() {
        draft = new Draft(uiAssistant);
        productTable.setItems(draft.getDraftList());
    }

    private BigDecimal callCountDialog(Validator<String> productCountValidator, BigDecimal actualProductCount) {
        ProductCountDialogController controller = productCountDialogLoader.getController();
        Stage dialog = new Stage();
        dialog.setScene(new Scene(productCountDialogLoader.getRoot()));
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stage);
        dialog.setResizable(false);
        dialog.setTitle(ProductCountDialogController.TITLE);
        controller.setDependencies(dialog, productCountValidator, actualProductCount);
        dialog.showAndWait();
        if (controller.isConfirmed()) {
            return controller.getDesiredCount();
        } else {
            throw new CancelOperationException();
        }
    }

    @FXML
    private void handleCancelCheck() {

    }

    @FXML
    private void handleCompleteCheck() {

    }

    @FXML
    private void handleReadDiscount() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        indicateObservables();
        loadCountDialog();
    }

    private void initTableColumns() {
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        measureColumn.setCellValueFactory(cellData -> cellData.getValue().measureProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        totalResultColumn.setCellValueFactory(cellData -> cellData.getValue().totalSumProperty());
    }

    private void indicateObservables() {
        uiAssistant.indicateObservables(checkSum.textProperty(),
                discountRate.textProperty(),
                bonusesAccrued.textProperty(),
                contributedCashFund.textProperty(),
                contributedTerminalFund.textProperty(),
                contributedBonusFund.textProperty(),
                toPay.textProperty(),
                contributedTotalFunds.textProperty(),
                oddsType.textProperty(),
                odds.textProperty(),
                discountCardOwner.textProperty(),
                discountCardNumber.textProperty(),
                discountCardType.textProperty(),
                discountCardBalance.textProperty());
    }

    private void loadCountDialog() {
        try {
            productCountDialogLoader = new FXMLLoader(getClass()
                    .getResource(ProductCountDialogController.LOCATION));
            productCountDialogLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDependencies(Stage stage, Employee employee, Warehouse warehouse, OrganizationManager orgManager) {
        this.stage = stage;
        this.employee = employee;
        this.warehouse = warehouse;
        this.organizationManager = orgManager;
        buyersChoiceBox.setItems(orgManager.getBuyers());
        buyersChoiceBox.getSelectionModel().select(orgManager.getCurrentBuyer());
        TextFields.bindAutoCompletion(productSearchField, warehouse.getProducts());
        stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_ANY), () -> {
            buyersChoiceBox.show();
        });
        initBuyersChoiceListener();
    }

    private void initBuyersChoiceListener() {
        buyersChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                });
    }
}
