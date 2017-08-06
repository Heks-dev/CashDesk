package ua.org.goservice.cashdesk.controller.dialogs.popup;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.HashMap;
import java.util.Map;

public class Popups {
    private static final Map<PopupStyle, Image> imageMap = new HashMap<>();
    static {
        imageMap.put(PopupStyle.SUCCESSFUL,
                new Image(Class.class.getResourceAsStream("/assets/icon/success-medium.png")));
        imageMap.put(PopupStyle.ERROR,
                new Image(Class.class.getResourceAsStream("/assets/icon/error-medium.png")));
        imageMap.put(PopupStyle.SYNC,
                new Image(Class.class.getResourceAsStream("/assets/icon/sync-medium.png")));
    }

    public static void show(PopupStyle style, String content) {
        Notifications notification = Notifications.create()
                .title(content)
                .graphic(new ImageView(imageMap.get(style)))
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle()
                .hideAfter(Duration.seconds(5));
        notification.hideCloseButton().show();
    }
}
