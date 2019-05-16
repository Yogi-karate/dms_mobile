package com.dealermanagmentsystem.dialog;


public abstract class AlertDialogModel extends BaseModel {
    private String title;
    private String message;
    private boolean isCancellable;
    private String positiveButtonText;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }
}
