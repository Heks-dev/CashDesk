package ua.org.goservice.cashdesk.controller.cashdesk.refund;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.control.EditingCell;
import ua.org.goservice.cashdesk.controller.control.TextFieldCell;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.Dialogs;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.discount.DiscountCardSearcher;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;
import ua.org.goservice.cashdesk.model.draft.RefundDraft;
import ua.org.goservice.cashdesk.model.draft.RefundDraftEntry;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;
import ua.org.goservice.cashdesk.model.trade.RefundOperator;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleContent;
import ua.org.goservice.cashdesk.model.util.validator.ProductCountValidator;
import ua.org.goservice.cashdesk.model.util.validator.field.CheckNumberFieldValidator;
import ua.org.goservice.cashdesk.model.util.validator.fund.DependFundValidator;
import ua.org.goservice.cashdesk.model.util.validator.tableCell.CellProductCountValidator;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class RefundController implements Initializable {
    public static final String LOCATION = "/view/cashdesk/refund.fxml";
    /**
     *
     */
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXCheckBox fullRefund;
    /**
     *
     */
    @FXML
    private Label checkNumber;
    @FXML
    private Label checkSum;
    @FXML
    private Label cardType;
    @FXML
    private Label dataOf;
    @FXML
    private Label cardNumber;
    @FXML
    private Label checkSumToPay;
    @FXML
    private Label terminalFund;
    @FXML
    private Label bonusFund;
    @FXML
    private Label cashFund;
    /**
     *
     */
    @FXML
    private TableView<RefundDraftEntry> draftTable;
    @FXML
    private TableColumn<RefundDraftEntry, String> barcodeColumn;
    @FXML
    private TableColumn<RefundDraftEntry, String> productNameColumn;
    @FXML
    private TableColumn<RefundDraftEntry, String> measureColumn;
    @FXML
    private TableColumn<RefundDraftEntry, BigDecimal> priceColumn;
    @FXML
    private TableColumn<RefundDraftEntry, BigDecimal> quantityColumn;
    @FXML
    private TableColumn<RefundDraftEntry, BigDecimal> totalPriceColumn;
    @FXML
    private TableColumn<RefundDraftEntry, BigDecimal> refundQuantityColumn;

    /**
     *
     */
    @FXML
    private JFXTextField refundCash;
    @FXML
    private JFXTextField refundBonuses;
    @FXML
    private Label totalRefundSum;

    /**
     * Model objects
     */
    private final RefundUIAssistant uiAssistant = new RefundUIAssistant();
    private final RefundOperator operator = new RefundOperator();
    private RefundDraft draft;
    private Stage stage;
    private IDGoodSearcher goodSearcher;
    private DiscountCardSearcher cardSearcher;

    @FXML
    private void loadCheck() {
        boolean emptyField = searchField.getText() == null || searchField.getText().length() == 0;
        if (emptyField) return;
        if (!searchField.validate()) return;
        try {
            Integer enteredID = Integer.valueOf(searchField.getText());
            SaleContent saleContent = operator.loadSaleContent(enteredID);
            draft = new RefundDraft(uiAssistant, saleContent, goodSearcher, cardSearcher);
            draftTable.getItems().setAll(draft.getSaleList());
        } catch (NotFoundException e) {
            AlertCause cause = AlertCause.NOT_FOUND;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        } finally {
            searchField.textProperty().setValue(null);
        }
    }

    @FXML
    private void submit() {
    }

    @FXML
    private void cancel() {
    }

    public void setDependencies(Stage stage, IDGoodSearcher goodSearcher, DiscountCardSearcher cardSearcher) {
        this.stage = stage;
        this.goodSearcher = goodSearcher;
        this.cardSearcher = cardSearcher;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSearchField();
        initializeFullRefundBox();
        initializeDraftColumns();
        indicateObservables();
        uiAssistant.clearUI();
    }

    private void initializeFullRefundBox() {
        fullRefund.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (draft != null) {
                if (newValue) {
                    draft.fullRefund(true);
                } else {
                    draft.fullRefund(false);
                }
            }
        });
    }

    private void initializeDraftColumns() {
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        measureColumn.setCellValueFactory(cellData -> cellData.getValue().measureProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalSumProperty());
        refundQuantityColumn.setCellFactory(param -> new TextFieldCell(new CellProductCountValidator()));
        refundQuantityColumn.setOnEditCommit(event -> {
            RefundDraftEntry entry = event.getRowValue();
            try {
                ProductCountValidator validator = new ProductCountValidator(entry.getMeasure(), entry.getCount());
                validator.validate(event.getNewValue().toString());
                entry.setRefundCount(validator.getDesiredQuantity());
                draft.calculateChanges();
            } catch (IllegalArgumentException e) {
                event.getRowValue().setRefundCount(event.getOldValue());
                throw e;
            }
        });
    }

    private void indicateObservables() {
        uiAssistant.indicateObservables(
                checkNumber.textProperty(),
                dataOf.textProperty(),
                checkSum.textProperty(),
                cardNumber.textProperty(),
                cardType.textProperty(),
                checkSumToPay.textProperty(),
                cashFund.textProperty(),
                terminalFund.textProperty(),
                bonusFund.textProperty(),
                refundCash.textProperty(),
                refundBonuses.textProperty(),
                totalRefundSum.textProperty());
    }

    private void initializeSearchField() {
        CheckNumberFieldValidator validator = new CheckNumberFieldValidator();
        searchField.getValidators().add(validator);
        searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                searchField.validate();
            }
        });
    }

    public void callContributeCashFund() {
        if (draft == null || draft.getRefundList().size() == 0) return;
        try {
            DependFundValidator validator = new DependFundValidator(draft.getTotalContributedFunds(),
                    draft.getCashFund(), draft.getAmountToRefund());
            if (Dialogs.contributeFund(stage, validator, PaymentMethod.CASH)) {
                draft.refundInCash(validator.getValidFund());
            }
        } catch (ActionDeniedException e) {
            AlertCause cause = AlertCause.ACTION_DENIED;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        }
    }

    public void callContributeBonusesFund() {
        if (draft == null || draft.getRefundList().size() == 0) return;
        try {
            if (draft.getDiscountCard() == null || draft.getDiscountCard().getType().equals(DiscountCard.DISCOUNT_TYPE)) {
                throw new ActionDeniedException(Exceptions.DISCOUNT_NOT_SPECIFIED);
            }
            DependFundValidator validator = new DependFundValidator(draft.getTotalContributedFunds(),
                    draft.getBonusFund(), draft.getAmountToRefund());
            if (Dialogs.contributeFund(stage, validator, PaymentMethod.BONUSES)) {
                draft.refundInBonuses(validator.getValidFund());
            }
        } catch (ActionDeniedException e) {
            AlertCause cause = AlertCause.ACTION_DENIED;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        }
    }
}
