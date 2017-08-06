package ua.org.goservice.cashdesk.controller.control;

import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

import java.math.BigDecimal;

public class EditingCell extends TableCell<DraftEntry, BigDecimal> {

    private TextField textField;
    private final Validator<TextField> validator;

    public EditingCell(Validator<TextField> validator) {
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                startEdit();
            }
        });
        setEditable(true);
        this.validator = validator;
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getItem() != null) {
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setFocused(true);
            setFocusTraversable(true);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(BigDecimal item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getCurrentCellValue());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getCurrentCellValue());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    @Override
    public void commitEdit(BigDecimal item) {
        if (isEditing()) {
            super.commitEdit(item);
        } else {
            final TableView table = getTableView();
            if (table != null) {
                TablePosition position = new TablePosition(getTableView(), getTableRow().getIndex(), getTableColumn());
                TableColumn.CellEditEvent editEvent = new TableColumn.CellEditEvent(table, position, TableColumn.editCommitEvent(), item);
                Event.fireEvent(getTableColumn(), editEvent);
            }
            updateItem(item, false);
            if (table != null) {
                table.edit(-1, null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getCurrentCellValue());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                if (textField.getText() == null || textField.getText().length() == 0) {
                    return;
                }
                try {
                    validator.validate(textField);
                    commitEdit(new BigDecimal(textField.getText()));
                    setText(textField.getText());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                    textField.setText(null);
                } catch (IllegalArgumentException e) {
                    AlertCause cause = AlertCause.INVALID_FORMAT;
                    cause.setContent(e.getMessage());
                    Alerts.notifying(getScene().getWindow(), cause);
                }
            } else if (key.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
                setText(getCurrentCellValue());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        });
    }

    private String getCurrentCellValue() {
        return getItem() == null ? "" : getItem().toString();
    }
}
