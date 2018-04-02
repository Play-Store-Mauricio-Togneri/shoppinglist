package com.mauriciotogneri.shoppinglist.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mauriciotogneri.androidutils.uibinder.UiBinder;
import com.mauriciotogneri.shoppinglist.R;

public abstract class BaseView<O, C>
{
    private View view;
    private Context context;
    private final int viewId;
    protected final C ui;
    protected final O observer;

    protected BaseView(@LayoutRes int viewId, O observer, C viewContainer)
    {
        this.viewId = viewId;
        this.observer = observer;
        this.ui = viewContainer;
    }

    @SuppressWarnings("unchecked")
    public final View inflate(LayoutInflater inflater, ViewGroup container)
    {
        context = inflater.getContext();
        view = inflater.inflate(viewId, container, false);

        if (container == null)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }

        UiBinder uiBinder = new UiBinder();
        uiBinder.bind(view, this);

        if (ui != null)
        {
            uiBinder.bind(view, ui);
        }

        initialize();

        return view;
    }

    protected void initialize()
    {
    }

    protected Context context()
    {
        return context;
    }

    protected void toolbarTitle(@StringRes int resId)
    {
        TextView textView = view.findViewById(R.id.toolbar_title);
        textView.setText(resId);
    }

    protected void enableBack(OnClickListener onClickListener)
    {
        ImageView imageView = view.findViewById(R.id.toolbar_back);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(onClickListener);
    }

    protected void enableToolbarAction(@DrawableRes int resId, OnClickListener onClickListener)
    {
        ImageView imageView = view.findViewById(R.id.toolbar_action);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(resId);
        imageView.setOnClickListener(onClickListener);
    }

    protected void disableToolbarAction()
    {
        ImageView imageView = view.findViewById(R.id.toolbar_action);
        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener(null);
    }

    @SuppressLint("InflateParams")
    public void showToast(final int messageId)
    {
        /*handler.post(() -> {
            View layout = inflater.inflate(R.layout.view_toast, null);

            TextView text = layout.findViewById(R.id.message);
            text.setText(messageId);

            Toast toast = new Toast(getContext().getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 50);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        });*/
    }
}