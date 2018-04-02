package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.mauriciotogneri.shoppinglist.R;

public class Dialogs
{
    private final Context context;

    public Dialogs(Context context)
    {
        this.context = context;
    }

    public void options(String title, String[] options, OnOptionSelected callback)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title(title));
        builder.setItems(options, (dialog, which) -> callback.onOptionSelected(which));
        builder.show();
    }

    public void confirmation(String title, String message, OnConfirmationAccepted callback)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title(title));
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.yes, (dialog, whichButton) -> callback.onConfirmationAccepted());
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }

    private SpannableStringBuilder title(String text)
    {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        spannable.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary)),
                0,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    public interface OnOptionSelected
    {
        void onOptionSelected(int option);
    }

    public interface OnConfirmationAccepted
    {
        void onConfirmationAccepted();
    }
}