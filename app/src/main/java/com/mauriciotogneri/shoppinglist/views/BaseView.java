package com.mauriciotogneri.shoppinglist.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.widgets.CustomDialog;
import com.mauriciotogneri.shoppinglist.widgets.CustomEditText;
import com.mauriciotogneri.shoppinglist.widgets.CustomImageView;
import com.mauriciotogneri.shoppinglist.widgets.CustomTextView;

public abstract class BaseView implements BaseViewInterface
{
    private View view;
    private LayoutInflater inflater;

    @Override
    public final View init(LayoutInflater inflater, ViewGroup container)
    {
        this.inflater = inflater;
        this.view = inflater.inflate(getViewId(), container, false);

        return this.view;
    }

    @Override
    public ListView getListView(int listViewId)
    {
        return (ListView) this.view.findViewById(listViewId);
    }

    @Override
    public CustomTextView getCustomTextView(int textViewId)
    {
        return (CustomTextView) this.view.findViewById(textViewId);
    }

    @Override
    public CustomEditText getCustomEditText(int editTextId)
    {
        return (CustomEditText) this.view.findViewById(editTextId);
    }

    @Override
    public CustomImageView getCustomImageView(int imageId)
    {
        return (CustomImageView) this.view.findViewById(imageId);
    }

    @Override
    public Spinner getSpinner(int spinnerId)
    {
        return (Spinner) this.view.findViewById(spinnerId);
    }

    @Override
    public ViewPager getViewPager(int viewPagerId)
    {
        return (ViewPager) this.view.findViewById(viewPagerId);
    }

    @Override
    public void setButtonAction(int buttonId, View.OnClickListener callback)
    {
        View toolbarButton = this.view.findViewById(buttonId);
        toolbarButton.setOnClickListener(callback);
    }

    @Override
    @SuppressLint("InflateParams")
    public void showToast(Context context, int messageId)
    {
        View layout = this.inflater.inflate(R.layout.view_toast, null);

        TextView text = (TextView) layout.findViewById(R.id.message);
        text.setText(messageId);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    @SuppressLint("InflateParams")
    public void showError(Context context, int messageId)
    {
        CustomDialog dialog = new CustomDialog(context, context.getString(R.string.label_error));
        dialog.setLayout(R.layout.dialog_content_text);

        TextView text = dialog.getCustomTextView(R.id.text);
        text.setText(messageId);

        dialog.setPositiveButton(R.string.button_accept, null);

        dialog.display();
    }
}