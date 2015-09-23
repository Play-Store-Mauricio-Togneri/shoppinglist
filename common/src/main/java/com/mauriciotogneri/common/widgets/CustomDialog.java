package com.mauriciotogneri.common.widgets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mauriciotogneri.common.R;

public abstract class CustomDialog extends AlertDialog.Builder
{
    protected View view;

    @SuppressLint("InflateParams")
    public CustomDialog(Context context, String title)
    {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        LinearLayout customTitle = (LinearLayout) inflater.inflate(R.layout.dialog_title, null);
        TextView dialogTitle = (TextView) customTitle.findViewById(R.id.title);
        dialogTitle.setText(title);

        setCustomTitle(customTitle);
        setCancelable(true);

        int viewId = getViewId();

        if (viewId != 0)
        {
            view = inflater.inflate(viewId, null);
            setView(view);
        }

        initialize();
    }

    protected abstract int getViewId();

    protected void initialize()
    {
    }

    public void display()
    {
        AlertDialog dialog = create();
        dialog.show();

        Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (buttonNegative != null)
        {
            buttonNegative.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
        }

        Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);

        if (buttonNeutral != null)
        {
            buttonNeutral.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
        }

        Button buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (buttonPositive != null)
        {
            buttonPositive.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
        }
    }
}