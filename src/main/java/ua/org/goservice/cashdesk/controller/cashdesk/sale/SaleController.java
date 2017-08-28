package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ua.org.goservice.cashdesk.controller.control.EditingCell;
import ua.org.goservice.cashdesk.controller.dialogs.Dialogs;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.trade.Operator;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleTransaction;
import ua.org.goservice.cashdesk.model.util.validator.tableCell.CellProductCountValidator;
import ua.org.goservice.cashdesk.model.util.validator.DiscountNumberValidator;
import ua.org.goservice.cashdesk.model.util.validator.fund.ComplexFundValidator;
import ua.org.goservice.cashdesk.model.util.validator.fund.DependFundValidator;
import ua.org.goservice.cashdesk.model.util.validator.fund.FundValidator;
import ua.org.goservice.cashdesk.model.util.validator.ProductCountValidator;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.discount.DiscountCardSearcher;
import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.warehouse.Product;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

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
    private Operator<SaleTransaction> operator;

    @FXML
    private void handleAddToDraft() {
        if (productSearchField.getText() == null
                || productSearchField.getText().length() == 0) return;
        try {
            Product product = parseSearchField();
            BigDecimal actualProductCount = warehouse.getActualProductCount(product.getBarcode());
            ProductCountValidator validator = new ProductCountValidator(product.getMeasures(), actualProductCount);
            if (Dialogs.productQuantity(stage, validator)) {
                if (draft == null) createNewDraft();
                draft.addProduct(product, validator.getDesiredQuantity());
            }
        } catch (IllegalArgumentException e) {
            AlertCause cause = AlertCause.NOT_FOUND;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
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

    @FXML
    private void handleCancelSale() {
        if (draft == null) return;
        if (Alerts.confirm(stage, AlertCause.CANCEL_SALE)) {
            closeSale();
        }
    }

    @FXML
    private void handleCompleteSale() {
        if (draft == null || draft.getDraftList().size() == 0) return;
        if (Alerts.confirm(stage, AlertCause.COMPLETE_SALE)) {
            try {
                ComplexFundValidator validator = new ComplexFundValidator();
                validator.validate(draft);
                operator.complete(new SaleTransaction(validator.getAmountInCash(), warehouse, draft,
                        organizationManager.getOurOrganization(), organizationManager.getCurrentBuyer()));
                if (draft.getOdds().compareTo(BigDecimal.ZERO) > 0) {
                    AlertCause.ISSUE_CHANGE.formattingContent(draft.getOdds().toString());
                    Alerts.notifying(stage, AlertCause.ISSUE_CHANGE);
                }
                closeSale();
            } catch (ActionDeniedException e) {
                AlertCause cause = AlertCause.ACTION_DENIED;
                cause.setContent(e.getMessage());
                Alerts.notifying(stage, cause);
            }
        }
    }

    private void closeSale() {
        productTable.setItems(null);
        uiAssistant.clearUI();
        draft = null;
    }

    /**
     * Contribute funds event handlers
     */
    public void callContributeCashFund() {
        if (draft == null) return;
        FundValidator validator = new FundValidator();
        if (Dialogs.contributeFund(stage, validator, PaymentMethod.CASH)) {
            draft.payInCash(validator.getValidFund());
        }
    }

    public void callContributeTerminalFund() {
        if (draft == null) return;
        DependFundValidator validator = new DependFundValidator(draft.getTotalContributedFunds(),
                draft.getTerminalFund(), draft.getAmountToPay());
        if (Dialogs.contributeFund(stage, validator, PaymentMethod.TERMINAL)) {
            draft.payInTerminal(validator.getValidFund());
        }
    }

    public void callContributeBonusesFund() {
        if (draft == null) return;
        try {
            if (draft.getDiscountCard() == null || draft.getDiscountCard().getType().equals(DiscountCard.DISCOUNT_TYPE)) {
                throw new ActionDeniedException(Exceptions.DISCOUNT_NOT_SPECIFIED);
            }
            DependFundValidator validator = new DependFundValidator(draft.getTotalContributedFunds(),
                    draft.getBonusFund(), draft.getAmountToPay());
            if (Dialogs.contributeFund(stage, validator, PaymentMethod.BONUSES)) {
                draft.payInBonuses(validator.getValidFund());
            }
        } catch (ActionDeniedException e) {
            AlertCause cause = AlertCause.ACTION_DENIED;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        }
    }

    /**
     * Discount read event handler
     */
    @FXML
    private void handleReadDiscount() {
        if (draft == null) return;
        try {
            DiscountNumberValidator validator = new DiscountNumberValidator();
            if (Dialogs.readDiscount(stage, validator)) {
                DiscountCard discountCard = cardSearcher
                        .searchDiscountByNumber(validator.getValidatedNumber());
                draft.setDiscountCard(discountCard);
            }
        } catch (NotFoundException e) {
            AlertCause cause = AlertCause.NOT_FOUND;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        }
    }

    /**
     * Initializing and setting dependencies
     */
    public void setDependencies(Stage primaryStage, OrganizationManager organizationManager,
                                Warehouse warehouse, DiscountCardSearcher cardSearcher,
                                Operator<SaleTransaction> operator) {
        this.stage = primaryStage;
        this.organizationManager = organizationManager;
        this.warehouse = warehouse;
        this.cardSearcher = cardSearcher;
        this.operator = operator;
        TextFields.bindAutoCompletion(productSearchField, this.warehouse.getPriceList());
        initializeBuyerChoiceBox();
    }

    private void initializeBuyerChoiceBox() {
        buyersChoiceBox.setItems(organizationManager.getBuyers());
        buyersChoiceBox.getSelectionModel().select(organizationManager.getCurrentBuyer());
        initializeBuyerChangeListener();
    }

    private void initializeBuyerChangeListener() {
        buyersChoiceBox.getSelectionModel().selectedItemProperty().addListener(new CounterPartyChangeListener());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        indicateObservables();
        uiAssistant.clearUI();
        initializeFundFields();
    }

    private void initTableColumns() {
        countColumn.setCellFactory(param -> new EditingCell(new CellProductCountValidator()));
        countColumn.setOnEditCommit(event -> {
            try {
                BigDecimal actualProductCount = warehouse.getActualProductCount(
                        event.getRowValue().getBarcode());
                ProductCountValidator validator = new ProductCountValidator(event.getRowValue().getMeasure(), actualProductCount);
                validator.validate(event.getNewValue().toString());
                if (event.getNewValue().compareTo(BigDecimal.ZERO) == 0) {
                    draft.removeEmptyEntry(event.getRowValue());
                } else {
                    event.getRowValue().setQuantity(event.getNewValue());
                }
                if (draft.getDraftList().size() == 0) {
                    closeSale();
                } else {
                    draft.calculateChanges();
                }
            } catch (IllegalArgumentException e) {
                event.getRowValue().setQuantity(event.getOldValue());
                AlertCause cause = AlertCause.ACTION_DENIED;
                cause.setContent(e.getMessage());
                Alerts.notifying(stage, cause);
            }
        });
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

    private void initializeFundFields() {
        contributedCashFund.setEditable(false);
        contributedTerminalFund.setEditable(false);
        contributedBonusFund.setEditable(false);
    }

    class CounterPartyChangeListener implements ChangeListener<Organization> {

        @Override
        public void changed(ObservableValue<? extends Organization> observable, Organization oldValue, Organization newValue) {
            if (draft != null && newValue != null) {
                if (Alerts.confirm(stage, AlertCause.CHANGE_COUNTERPARTY)) {
                    change(newValue);
                    draft = null;
                    productTable.setItems(null);
                    uiAssistant.clearUI();
                } else {
                    Platform.runLater(() -> {
                        buyersChoiceBox.setValue(oldValue);
                    });
                }
            } else {
                change(newValue);
            }
        }

        private void change(Organization newValue) {
            organizationManager.changeCurrentBuyer(newValue);
            warehouse.syncPriceList(newValue);
        }
    }
}
