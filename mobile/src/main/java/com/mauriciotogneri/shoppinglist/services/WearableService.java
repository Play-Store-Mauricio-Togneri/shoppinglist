package com.mauriciotogneri.shoppinglist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mauriciotogneri.common.wearable.Message;
import com.mauriciotogneri.common.wearable.WearableConnectivity;
import com.mauriciotogneri.common.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.shoppinglist.utils.Preferences;

public class WearableService extends Service implements WearableEvents
{
    private WearableConnectivity connectivity;
    private Preferences preferences;

    @Override
    public void onCreate()
    {
        super.onCreate();

        preferences = Preferences.getInstance(this);

        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onConnectedSuccess()
    {
    }

    @Override
    public void onConnectedFail()
    {
    }

    @Override
    public void onMessageReceived(Message message)
    {
        String nodeId = message.getNodeId();
        String path = message.getPath();
        String payload = message.getPayloadAsString();

        //        if (TextUtils.equals(path, Paths.GET_FAVORITE_STOPS))
        //        {
        //            getFavoriteStops(nodeId);
        //        }
    }

    //    private void getFavoriteStops(String nodeId)
    //    {
    //        List<Stop> stops = preferences.getFavoriteStops();
    //
    //        connectivity.sendMessage(Messages.resultFavoriteStops(nodeId, stops));
    //    }
    //
    //    private void getDepartures(final String nodeId, final String stopCode)
    //    {
    //        final TpgApi tpgApi = TpgApi.getInstance(this);
    //
    //        tpgApi.getLinesColors(new OnRequestResult<GetLinesColors>()
    //        {
    //            @Override
    //            public void onSuccess(final GetLinesColors linesColors)
    //            {
    //                tpgApi.getNextDepartures(stopCode, new OnRequestResult<GetNextDepartures>()
    //                {
    //                    @Override
    //                    public void onSuccess(GetNextDepartures nextDepartures)
    //                    {
    //                        nextDepartures.setColors(linesColors);
    //                        nextDepartures.removeInvalidDepartures();
    //
    //                        connectivity.sendMessage(Messages.resultDepartures(nodeId, nextDepartures.departures));
    //                    }
    //
    //                    @Override
    //                    public void onFailure()
    //                    {
    //                    }
    //                });
    //            }
    //
    //            @Override
    //            public void onFailure()
    //            {
    //            }
    //        });
    //    }
    //
    //    private void increaseStopHitCount(String stopCode)
    //    {
    //        preferences.increaseHitCount(stopCode);
    //    }
}