package com.ffisherr.lbg;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;


public class CalendarFragment extends ListFragment {

    EventPesronse[] allEvents;
    private String uLogin;
    private String uUniversity;
    private Boolean uIsKnown;
    private Listener listener;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uIsKnown    = getArguments().getBoolean(Config.IS_KNOWN_BOOL);
        uLogin      = getArguments().getString(Config.LOGIN_TEXT);
        uUniversity = getArguments().getString(Config.UNIVERSITY_TEXT);
        String[] tags = {Config.common_tag, uLogin, uUniversity};
        getEvents(tags);
        EventArrayAdapter arr;
        if (allEvents != null) {
            arr = new EventArrayAdapter(inflater.getContext(), allEvents);
        } else {
            EventPesronse[] er = {};
            Toast.makeText(inflater.getContext(), "Нет доступных мероприятий", Toast.LENGTH_LONG).show();
            arr = new EventArrayAdapter(inflater.getContext(), er);
        }
        setListAdapter(arr);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        if (listener != null) {
            EventPesronse ret = allEvents[(int) (id)];
            listener.eventClicked(ret.getId(), ret.getTitle(), ret.getAbout(),
                    ret.getDt());
        }
    }

    public void getEvents(String [] tags) {
        allEvents = null;
        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/get_event_by_tags";

        EventPesronse event = new EventPesronse(tags);
        Gson g = new Gson();
        String event_data = g.toJson(event);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            try {
                EventPesronse[] ev = g.fromJson(result, EventPesronse[].class);
                if (ev[0] != null) {
                    if (ev[0].getStatus().equals(ServerDescriptor.SUCCESS)) {
                        allEvents = ev;
                    }
                    /*else if (ev[0].getStatus().equals(ServerDescriptor.INTERNET_ERROR)) {
                        Toast.makeText(inflater.getContext(), "Нет доступа к серверу", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(inflater.getContext(), "Ошибка", Toast.LENGTH_LONG).show();
                    }*/
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
