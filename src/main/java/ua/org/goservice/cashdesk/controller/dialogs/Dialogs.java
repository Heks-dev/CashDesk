package ua.org.goservice.cashdesk.controller.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.dialogs.dialog.DiscountNumberDialogController;
import ua.org.goservice.cashdesk.controller.dialogs.dialog.ProductCountDialogController;
import ua.org.goservice.cashdesk.model.util.validator.DiscountNumberValidator;
import ua.org.goservice.cashdesk.model.util.validator.fund.FundValidator;
import ua.org.goservice.cashdesk.controller.dialogs.dialog.ContributeFundDialogController;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;
import ua.org.goservice.cashdesk.model.exception.CancelOperationException;
import ua.org.goservice.cashdesk.model.util.validator.ProductCountValidator;

import java.io.IOException;

public class Dialogs {
    // todo impl
    public static boolean contributeFund(Stage owner, FundValidator validator, PaymentMethod paymentMethod) {
        FXMLLoader loader = getLoaded(ContributeFundDialogController.LOCATION);
        AnchorPane root = loader.getRoot();
        ContributeFundDialogController controller = loader.getController();
        Stage dialog = createDialogStage(root, ContributeFundDialogController.TITLE, owner);
        controller.setDependencies(dialog, validator, paymentMethod);
        dialog.showAndWait();
        return controller.isConfirmed();
    }

    public static boolean readDiscount(Stage owner, DiscountNumberValidator validator) {
        FXMLLoader loader = getLoaded(DiscountNumberDialogController.LOCATION);
        AnchorPane root = loader.getRoot();
        DiscountNumberDialogController controller = loader.getController();
        Stage dialog = createDialogStage(root, DiscountNumberDialogController.TITLE, owner);
        controller.setDependencies(dialog, validator);
        dialog.showAndWait();
        return controller.isConfirm();
    }

    public static boolean productQuantity(Stage owner, ProductCountValidator validator) {
        FXMLLoader loader = getLoaded(ProductCountDialogController.LOCATION);
        AnchorPane root = loader.getRoot();
        ProductCountDialogController controller = loader.getController();
        Stage dialog = createDialogStage(root, ProductCountDialogController.TITLE, owner);
        controller.setDependencies(dialog, validator);
        dialog.showAndWait();
        return controller.isConfirmed();
    }

    private static FXMLLoader getLoaded(String res) {
        try {
            FXMLLoader loader = new FXMLLoader(Class.class.getClass().getResource(res));
            loader.load();
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CancelOperationException();
    }

    private static Stage createDialogStage(Parent root, String title, Stage owner) {
        Stage dialog = new Stage();
        dialog.setScene(new Scene(root));
        dialog.setTitle(title);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setResizable(false);
        return dialog;
    }
}
