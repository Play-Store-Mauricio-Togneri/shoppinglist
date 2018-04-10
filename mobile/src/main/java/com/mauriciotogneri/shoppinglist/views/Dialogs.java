package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.mauriciotogneri.shoppinglist.R;

import java.util.List;

public class Dialogs
{
    private final Context context;

    public Dialogs(Context context)
    {
        this.context = context;
    }

    public void options(String title, List<String> list, OnOptionSelected callback)
    {
        String[] options = new String[list.size()];
        list.toArray(options);

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

    public void input(Context context, String title, String initialInput, OnInputConfirmed callback)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.view_dialog_input, null);
        EditText nameField = view.findViewById(R.id.input);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title(title));
        builder.setView(view);
        builder.setPositiveButton(R.string.button_accept, (dialogInterface, i) -> callback.onInputConfirmed(nameField.getText().toString()));
        builder.setNegativeButton(R.string.button_cancel, null);

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();

        if (window != null)
        {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        dialog.show();

        nameField.setText(initialInput);
        nameField.requestFocus();
        nameField.selectAll();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(initialInput.length() != 0);

        nameField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable text)
            {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(text.length() != 0);
            }
        });

        nameField.setOnEditorActionListener((view1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
            {
                try
                {
                    dialog.dismiss();
                }
                catch (Exception e)
                {
                    // ignore
                }

                callback.onInputConfirmed(nameField.getText().toString());
            }

            return false;
        });
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

    public interface OnInputConfirmed
    {
        void onInputConfirmed(String input);
    }
}