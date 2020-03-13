package com.ffisherr.lbg;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
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

    Message_Response[] AllMessages;
    private int messCounter = 0;


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
        try {
            uIsKnown = getArguments().getBoolean(Config.IS_KNOWN_BOOL);
            uLogin = getArguments().getString(Config.LOGIN_TEXT);
            uUniversity = getArguments().getString(Config.UNIVERSITY_TEXT);
            uId = getArguments().getInt(Config.USER_ID);
        } catch (NullPointerException ex) {
            uIsKnown = false;
            uLogin = "";
            uUniversity = "";
            uId = -1;
        }
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        sendBtn = view.findViewById(R.id.send_message_button);
        messagesLayout = view.findViewById(R.id.all_messages_field);

        getAllMessages();
        try {
            if (AllMessages != null && AllMessages[0] != null && AllMessages[0].getMessage_text() != null) {
                fillMessages(AllMessages);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

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
                Message_Response newMessage = new Message_Response("error", -1, currDate,
                        uId, newMessageEditText, uLogin);
                message = null;
                sendMessage(newMessage);
                if (message != null) {

                    LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                            wrapContent);

                    LinearLayout newMessageLayout = new LinearLayout(messagesLayout.getContext());
                    newMessageLayout.setOrientation(LinearLayout.VERTICAL);
                    messagesLayout.addView(newMessageLayout);

                    String myMessageText = getArguments().getString(LOGIN_TEXT) + ": " + message.getMessage_text();
                    TextView newMessageText = new TextView(newMessageLayout.getContext());
                    newMessageText.setText(myMessageText);
                    newMessageText.setWidth(wrapContent);
                    newMessageLayout.addView(newMessageText);

                    TextView newMessageSender = new TextView(newMessageLayout.getContext());
                    newMessageSender.setText("--------------------");
                    newMessageSender.setWidth(wrapContent);
                    newMessageLayout.addView(newMessageSender);

                } else {
                    // TODO пролемы отправки сообщения
                    Toast.makeText(inflater.getContext(), "Ошибка отправки сообщения", Toast.LENGTH_LONG).show();
                }
            }
        };
        sendBtn.setOnClickListener(sendBtnListener);
        runTimer();
        return view;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getAllMessages();
                int all_l = AllMessages.length;
                if (messCounter < all_l) {
                    Message_Response[] addedMessages = new Message_Response[all_l-messCounter];
                    for (int i = 0; i < all_l-messCounter; i++) {
                        addedMessages[i] = AllMessages[messCounter + i];
                    }
                    fillMessages(addedMessages);
                }
                handler.postDelayed(this, 200);
            }
        });
    }

    private void fillMessages(Message_Response[] mass){
        for (Message_Response m : mass) {
            messCounter++;
            System.out.println(m.getMessage_text());
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                    wrapContent);

            LinearLayout newMessageLayout = new LinearLayout(messagesLayout.getContext());
            newMessageLayout.setOrientation(LinearLayout.VERTICAL);
            messagesLayout.addView(newMessageLayout);

            String myMessageText = m.getSender_login() + ": " + m.getMessage_text();
            TextView newMessageText = new TextView(newMessageLayout.getContext());
            newMessageText.setText(myMessageText);
            newMessageText.setWidth(wrapContent);
            newMessageLayout.addView(newMessageText);

            TextView newMessageSender = new TextView(newMessageLayout.getContext());
            newMessageSender.setText("--------------------");
            newMessageSender.setWidth(wrapContent);
            newMessageLayout.addView(newMessageSender);
        }
    }

    private void sendMessage(Message_Response mess) {
        messCounter++;
        message = null;

        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/add_message";

        Gson g = new Gson();
        String event_data = g.toJson(mess);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            if (result.equals("[{'status':'connectionError'}]")) {
                Toast.makeText(myInflater.getContext(), "Нет связи с сервером", Toast.LENGTH_LONG).show();
                return;
            }
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

    private void getAllMessages() {
        AllMessages = null;
        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/get_all_messages";

        Message_Response mess = new Message_Response("",-1, "", 0,
                "", "");
        Gson g = new Gson();
        String mess_data = g.toJson(mess);
        ts.execute(url, mess_data);
        try {
            result = ts.get();
            try {
                AllMessages = g.fromJson(result, Message_Response[].class);
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
