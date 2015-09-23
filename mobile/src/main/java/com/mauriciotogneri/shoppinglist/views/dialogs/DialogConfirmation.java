package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.TextView;

import com.mauriciotogneri.common.widgets.CustomDialog;
import com.mauriciotogneri.shoppinglist.R;

public class DialogConfirmation extends CustomDialog
{
    public DialogConfirmation(Context context, String title)
    {
        super(context, title);
    }

    public void initialize(int messageId, OnClickListener callback)
    {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(messageId);

        setPositiveButton(R.string.button_accept, callback);
        setNegativeButton(R.string.button_cancel, null);
    }

    @Override
    protected int getViewId()
    {
        return R.layout.dialog_content_text;
    }
}