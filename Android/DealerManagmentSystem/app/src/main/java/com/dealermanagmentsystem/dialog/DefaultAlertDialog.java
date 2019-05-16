package com.dealermanagmentsystem.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class DefaultAlertDialog {

    public static AlertDialog getOneButtonDialog(Context context, OneButtonAlertDialogModel dialogModel, DialogInterface.OnClickListener listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(dialogModel.getTitle());
        alertDialog.setMessage(dialogModel.getMessage());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, dialogModel.getPositiveButtonText(), listener);
        alertDialog.setCancelable(dialogModel.isCancellable());
        alertDialog.setCanceledOnTouchOutside(dialogModel.isCancellable());
        return alertDialog;
    }

    public static AlertDialog getTwoButtonDialog(Context context, TwoButtonAlertDialogModel dialogModel, DialogInterface.OnClickListener listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(dialogModel.getTitle());
        alertDialog.setMessage(dialogModel.getMessage());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, dialogModel.getPositiveButtonText(), listener);
        alertDialog.setButton(dialogModel.getSecondButtonType(), dialogModel.getSecondButtonText(), listener);

        alertDialog.setCancelable(dialogModel.isCancellable());
        alertDialog.setCanceledOnTouchOutside(dialogModel.isCancellable());
        return alertDialog;
    }

    public static AlertDialog getThreeButtonDialog(Context context, ThreeButtonAlertDialogModel dialogModel, DialogInterface.OnClickListener listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(dialogModel.getTitle());
        alertDialog.setMessage(dialogModel.getMessage());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, dialogModel.getPositiveButtonText(), listener);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, dialogModel.getNeutralButtonText(), listener);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, dialogModel.getNegativeButtonText(), listener);

        alertDialog.setCancelable(dialogModel.isCancellable());
        alertDialog.setCanceledOnTouchOutside(dialogModel.isCancellable());
        return alertDialog;
    }


}