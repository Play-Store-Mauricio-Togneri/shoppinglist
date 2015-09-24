package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseDialog;
import com.mauriciotogneri.shoppinglist.R;

public class DialogError extends BaseDialog
{
    public DialogError(Context context)
    {
        super(context, context.getString(R.string.label_error));
    }

    public void initialize(int messageId)
    {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(messageId);

        setPositiveButton(R.string.button_accept, null);
    }

    @Override
    protected int getViewId()
    {
        return R.layout.dialog_content_text;
    }
}