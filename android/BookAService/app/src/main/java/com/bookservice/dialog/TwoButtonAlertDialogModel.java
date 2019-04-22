package com.bookservice.dialog;

public class TwoButtonAlertDialogModel extends AlertDialogModel {
    private int secondButtonType;
    private String secondButtonText;

    public int getSecondButtonType() {
        return secondButtonType;
    }

    public void setSecondButtonType(int secondButtonType) {
        this.secondButtonType = secondButtonType;
    }

    public String getSecondButtonText() {
        return secondButtonText;
    }

    public void setSecondButtonText(String secondButtonText) {
        this.secondButtonText = secondButtonText;
    }
}