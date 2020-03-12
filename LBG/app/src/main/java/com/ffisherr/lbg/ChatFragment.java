package com.ffisherr.lbg;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.ffisherr.lbg.Config.LOGIN_TEXT;


public class ChatFragment extends Fragment {

    LayoutInflater myInflater;
    Button sendBtn;
    Message_Response message;
    LinearLayout messagesLayout;
    private String uLogin;
    private String uUniversity;
    private Boolean uIsKnown;
    private Integer uId;


    private static final int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myInflater = inflater;
        uIsKnown    = getArguments().getBoolean(Config.IS_KNOWN_BOOL);
        uLogin      = getArguments().getString(Config.LOGIN_TEXT);
        uUniversity = getArguments().getString(Config.UNIVERSITY_TEXT);
        uId         = getArguments().getInt(Config.USER_ID);
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        sendBtn = view.findViewById(R.id.send_message_button);
        messagesLayout = view.findViewById(R.id.all_messages_field);


        View.OnClickListener sendBtnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText messTextEdit = view.findViewById(R.id.editMessageText);
                String newMessageEditText = messTextEdit.getText().toString();
                if (newMessageEditText.equals(""))
                    return;
                boolean ifStrEmpty = true;
                for (int i = 0; i < newMessageEditText.length(); i++) {
                    if (newMessageEditText.charAt(i) != ' ' &&
                            newMessageEditText.charAt(i) != '\n') {
                        ifStrEmpty = false;
                        break;
                    }
                }
                if (ifStrEmpty) {
                    messTextEdit.setText("");
                    return;
                }
                messTextEdit.setText("");
                DateFormat myDf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date dateObj = new Date();
                String currDate = myDf.format(dateObj);
                Message_Response newMessage = new Message_Response(-1, currDate,
                        uId, newMessageEditText);
                message = null;
                sendMessage(newMessage);
                if (message != null) {

                    LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                            wrapContent);

                    LinearLayout newMessageLayout = new LinearLayout(messagesLayout.getContext());
                    newMessageLayout.setOrientation(LinearLayout.VERTICAL);
                    messagesLayout.addView(newMessageLayout);


                    TextView newMessageText = new TextView(newMessageLayout.getContext());
                    newMessageText.setText(message.getMessage_text());
                    newMessageText.setWidth(wrapContent);
                    newMessageLayout.addView(newMessageText);

                    TextView newMessageSender = new TextView(newMessageLayout.getContext());
                    newMessageSender.setText(getArguments().getString(LOGIN_TEXT));
                    newMessageSender.setWidth(wrapContent);
                    newMessageLayout.addView(newMessageSender);

                } else {
                    // TODO пролемы отправки сообщения
                    Toast.makeText(inflater.getContext(), "Ошибка отправки сообщения", Toast.LENGTH_LONG).show();
                }
            }
        };
        sendBtn.setOnClickListener(sendBtnListener);
        return view;
    }

    private void sendMessage(Message_Response mess) {
        message = null;

        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/add_message";

        Gson g = new Gson();
        String event_data = g.toJson(mess);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            try {
                Message_Response getMess = g.fromJson(result, Message_Response.class);
                if (getMess != null) {
                    if (getMess.getId() != 0) {
                        message = getMess;
                    }
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
