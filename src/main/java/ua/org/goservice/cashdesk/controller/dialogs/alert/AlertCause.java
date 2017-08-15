package ua.org.goservice.cashdesk.controller.dialogs.alert;

import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;

public enum AlertCause {
    CHANGE_COUNTERPARTY("change.counterparty"),
    INPUT_ERROR("input.error"),
    CANCEL_SALE("cancel.sale"),
    INVALID_FORMAT("invalid.format"),
    LOG_OUT("log.out"),
    SYNCHRONIZATION_PROBLEM("sync.problem"),
    NOT_FOUND("not.found"),
    ACTION_DENIED("action.denied"),
    COMPLETE_SALE("complete.sale"),
    ISSUE_CHANGE("issue.change"),
    CARD_ISSUED("card.issued");

    private static final PropertyLoader title_loader = new PropertyLoader("/strings/alert-title.properties");
    private static final PropertyLoader content_loader = new PropertyLoader("/strings/alert-content.properties");
    private final String key;
    private String content;

    AlertCause(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title_loader.getValue(key);
    }

    public String getContent() {
        if (content != null) {
            String result = content;
            content = null;
            return result;
        }
        return content_loader.getValue(key);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void formattingContent(String val) {
        content = String.format(content_loader.getValue(key), val);
    }
}
