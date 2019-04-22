package com.bookservice.dialog;

public class ThreeButtonAlertDialogModel extends AlertDialogModel {
    private String neutralButtonText;
    private String negativeButtonText;

    public String getNeutralButtonText() {
        return neutralButtonText;
    }

    public void setNeutralButtonText(String neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }
}