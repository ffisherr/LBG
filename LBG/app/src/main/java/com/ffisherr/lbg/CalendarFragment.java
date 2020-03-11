package com.ffisherr.lbg;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;


public class CalendarFragment extends ListFragment {

    EventPesronse[] allEvents;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] tags = {"tag2", "tag3"};
        getEvents(tags);
        EventArrayAdapter arr = new EventArrayAdapter(inflater.getContext(), allEvents);
        setListAdapter(arr);
        Toast.makeText(inflater.getContext(), allEvents[0].getTitle(), Toast.LENGTH_LONG).show();
        return super.onCreateView(inflater, container, savedInstanceState);
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
