package com.mauriciotogneri.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mauriciotogneri.androidutils.uibinder.UiBinder;

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