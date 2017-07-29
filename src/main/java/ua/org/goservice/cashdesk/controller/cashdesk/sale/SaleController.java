package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.validator.DiscountNumberValidator;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.validator.FundValidator;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.validator.ProductCountValidator;
import ua.org.goservice.cashdesk.controller.control.Alert;
import ua.org.goservice.cashdesk.controller.control.AlertHeader;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.discount.DiscountCardSearcher;
import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;
import ua.org.goservice.cashdesk.model.exception.CancelOperationException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;
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
    public static final String LOCATION = "/view/cashdesk/sale/sale.fxml";
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
    private Label discountCardOwner;
    @FXML
    private Label discountCardNumber;
    @FXML
    private Label discountCardType;
    @FXML
    private Label discountCardBalance;
    private Stage stage;
    /**
     * Model objects
     */
    private Warehouse warehouse;
    private OrganizationManager organizationManager;
    private DiscountCardSearcher cardSearcher;

    private final SaleUIAssistant uiAssistant = new SaleUIAssistant();
    private Draft draft;

    @FXML
    private void handleAddToDraft() {
        if (productSearchField.getText().length() == 0) return;
        try {
            Product product = parseSearchField();
            if (draft == null) createNewDraft();
            BigDecimal actualProductCount = warehouse.getActualProductCount(product);
            BigDecimal desiredCount = callCountDialog(new ProductCountValidator(
                    product.getMeasures(), actualProductCount), actualProductCount);
            draft.addProduct(product, desiredCount);
        } catch (IllegalArgumentException | CancelOperationException e) {
            System.out.println(e.getMessage());
            if (e instanceof IllegalArgumentException) {
                new Alert().callAlert(AlertHeader.ADD_PRODUCT_ERROR, e.getMessage());
            }
        } finally {
            productSearchField.setText(null);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ProductCountDialogController.LOCATION));
            AnchorPane root = loader.load();
            ProductCountDialogController controller = loader.getController();
            Stage dialog = createDialogStage(root, ProductCountDialogController.TITLE);
            controller.setDependencies(dialog, productCountValidator, actualProductCount);
            dialog.showAndWait();
            if (controller.isConfirmed()) {
                return controller.getDesiredCount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CancelOperationException();
    }

    @FXML
    private void handleCancelCheck() {
        // todo confirms dialog
        uiAssistant.clearDraftArea();
        draft = null;
    }

    @FXML
    private void handleCompleteCheck() {

    }

    @FXML
    private void handleReadDiscount() {
        if (draft == null) return;
        try {
            Long discountNumber = callReadDiscountNumber();
            DiscountCard discountCard = cardSearcher.searchDiscountByNumber(discountNumber);
            draft.setDiscountCard(discountCard);
        } catch (IllegalArgumentException | NotFoundException e) {
            // todo alert
        } catch (CancelOperationException e) {
            // do nothing
        }
    }

    private Long callReadDiscountNumber() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(DiscountNumberDialogController.LOCATION));
            AnchorPane root = loader.load();
            DiscountNumberDialogController controller = loader.getController();
            Stage dialog = createDialogStage(root, DiscountNumberDialogController.TITLE);
            controller.setDependencies(dialog, new DiscountNumberValidator());
            dialog.showAndWait();
            if (controller.isConfirm()) {
                return controller.getCardNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CancelOperationException();
    }

    private void callContributeCashFund() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ContributeFundDialogController.LOCATION));
            AnchorPane root = loader.load();
            ContributeFundDialogController controller = loader.getController();
            Stage dialog = createDialogStage(root, ContributeFundDialogController.TITLE);
            FundValidator fundValidator = new FundValidator();
            controller.setDependencies(dialog,fundValidator, PaymentMethod.CASH_PAYMENT_LOCALE);
            dialog.showAndWait();
            if (controller.isConfirmed()) {
                draft.payInCash(fundValidator.getValidFund());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage createDialogStage(Parent root, String title) {
        Stage dialog = new Stage();
        dialog.setScene(new Scene(root));
        dialog.setTitle(title);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stage);
        dialog.setResizable(false);
        return dialog;
    }

    public void setDependencies(Stage primaryStage, OrganizationManager organizationManager,
                                Warehouse warehouse, DiscountCardSearcher cardSearcher) {
        this.stage = primaryStage;
        this.organizationManager = organizationManager;
        this.warehouse = warehouse;
        this.cardSearcher = cardSearcher;
        TextFields.bindAutoCompletion(productSearchField, this.warehouse.getPriceList());
        initializeBuyerChoiceBox();
    }

    private void initializeBuyerChoiceBox() {
        buyersChoiceBox.setItems(organizationManager.getBuyers());
        buyersChoiceBox.getSelectionModel().select(organizationManager.getCurrentBuyer());
        initBuyersChoiceListener();
    }

    private void initBuyersChoiceListener() {
        buyersChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (draft != null) {
                // todo call confirms dialog
            }
            organizationManager.changeCurrentBuyer(newValue);
            warehouse.syncPriceList(organizationManager.getCurrentBuyer());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        indicateObservables();
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
}
