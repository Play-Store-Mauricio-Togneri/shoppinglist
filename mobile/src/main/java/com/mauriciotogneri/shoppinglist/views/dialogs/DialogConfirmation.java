package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseDialog;
import com.mauriciotogneri.shoppinglist.R;

public class DialogConfirmation extends BaseDialog
{
    public DialogConfirmation(Context context, String title)
    {
        super(context, title);
    }

    public void initialize(int messageId, OnAccept callback)
    {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(messageId);

        setPositiveButton(R.string.button_accept, null);
        setPositiveButtonAction(callback);

        setNegativeButton(R.string.button_cancel, null);
    }

    @Override
    protected int getViewId()
    {
        return R.layout.dialog_content_text;
    }
}