package com.mauriciotogneri.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mauriciotogneri.common.R;

public abstract class BaseView<UI extends BaseUiContainer> implements BaseViewInterface<UI>
{
    private View view;
    protected UI ui;
    private LayoutInflater inflater;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public final View init(LayoutInflater inflater, ViewGroup container)
    {
        this.inflater = inflater;
        this.view = inflater.inflate(getViewId(), container, false);
        this.ui = getUiContainer(this);

        return view;
    }

    @Override
    public View findViewById(@IdRes int viewId)
    {
        return view.findViewById(viewId);
    }

    @Override
    public Context getContext()
    {
        return view.getContext();
    }

    @Override
    @SuppressLint("InflateParams")
    public void showToast(final int messageId)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                View layout = inflater.inflate(R.layout.view_toast, null);

                TextView text = (TextView) layout.findViewById(R.id.message);
                text.setText(messageId);

                Toast toast = new Toast(getContext().getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 50);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}