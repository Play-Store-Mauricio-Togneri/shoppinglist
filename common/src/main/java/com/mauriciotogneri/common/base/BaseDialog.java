package com.mauriciotogneri.common.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mauriciotogneri.common.R;
import com.mauriciotogneri.common.widgets.Fonts;

public abstract class BaseDialog extends AlertDialog.Builder
{
    protected View view;
    private AlertDialog dialog;
    private OnAccept positiveButtonAction;

    @SuppressLint("InflateParams")
    public BaseDialog(Context context, String title)
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
    }

    protected abstract int getViewId();

    protected void setPositiveButtonAction(OnAccept positiveButtonAction)
    {
        this.positiveButtonAction = positiveButtonAction;
    }

    public void close()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

    public void display()
    {
        dialog = create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();

        Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (buttonNegative != null)
        {
            buttonNegative.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
            buttonNegative.setTextColor(getContext().getResources().getColor(R.color.default_text_color));
        }

        Button buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (buttonPositive != null)
        {
            buttonPositive.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
            buttonPositive.setTextColor(getContext().getResources().getColor(R.color.default_text_color));

            if (positiveButtonAction != null)
            {
                buttonPositive.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        positiveButtonAction.onAccept(BaseDialog.this);
                    }
                });
            }
        }
    }

    public interface OnAccept
    {
        void onAccept(BaseDialog dialog);
    }
}