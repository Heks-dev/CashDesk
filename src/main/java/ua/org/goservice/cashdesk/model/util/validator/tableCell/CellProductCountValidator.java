package ua.org.goservice.cashdesk.model.util.validator.tableCell;

import javafx.scene.control.TextField;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

public class CellProductCountValidator implements Validator<TextField> {

    private static final String PIECE_EXP = "(\\d+)|(\\d+)(.|,)(0){1,2}";
    private static final String WEIGHING_EXP = "(\\d+)|(\\d+)(.|,)(\\d){1,2}";
    private static final String COMMA_SEPARATOR = ".+(,).+";

    @Override
    public void validate(TextField val) {
        boolean pieceFormat = val.getText().matches(PIECE_EXP);
        boolean weighingFormat = val.getText().matches(WEIGHING_EXP);
        if (!(pieceFormat && weighingFormat)) {
            throw new IllegalArgumentException(Exceptions.INCORRECT_MEASURE_FORMAT);
        }
        if (val.getText().matches(COMMA_SEPARATOR)) {
            val.setText(val.getText().replace(",", "."));
        }
    }
}
