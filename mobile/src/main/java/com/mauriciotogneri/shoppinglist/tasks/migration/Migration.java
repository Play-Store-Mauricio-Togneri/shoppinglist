package com.mauriciotogneri.shoppinglist.tasks.migration;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.database.AppDatabase;

public class Migration extends AsyncTask<Void, Void, Void>
{
    private final Context context;
    private final AppDatabase database;
    private final OnMigrationDone callback;
    private final ProgressDialog dialog;

    public Migration(Context context, OnMigrationDone callback)
    {
        this.context = context;
        this.database = AppDatabase.instance(context);
        this.callback = callback;
        this.dialog = new ProgressDialog(context);
        this.dialog.setMessage(context.getString(R.string.dialog_updatingDatabase));
    }

    @Override
    protected void onPreExecute()
    {
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        database.initialize(context);

        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        dialog.dismiss();

        callback.onMigrationDone();
    }

    public interface OnMigrationDone
    {
        void onMigrationDone();
    }
}