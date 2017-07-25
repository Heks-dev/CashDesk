package ua.org.goservice.cashdesk.controller.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class Alert {

    public void callAlert(String header, String content) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(header));
        layout.setBody(new Text(content));
        JFXButton confirmButton = new JFXButton("Подтвердить");
        layout.setActions(confirmButton);
        JFXDialog dialog = new JFXDialog();
        dialog.setContent(layout);
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        confirmButton.setOnAction(event -> dialog.close());

        dialog.show();
    }
}
