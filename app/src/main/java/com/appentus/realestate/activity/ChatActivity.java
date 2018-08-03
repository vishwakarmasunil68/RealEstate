package com.appentus.realestate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.ChatAdapter;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.ChatMsgPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.UtilityFunction;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends ActivityManager {

    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    String fri_id = "";
    String user_name = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

//        Bundle bundle=new Bundle();
//        if(bundle!=null){
//            fri_id=bundle.getString("fri_id");
//        }

        attachAdapter();
        fri_id = getIntent().getStringExtra("fri_id");
        user_name = getIntent().getStringExtra("user_name");
        if (user_name != null && user_name.length() > 0) {
            tv_title.setText(user_name);
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_msg.getText().toString().length() > 0) {
                    sendChat();
                }
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAllChats();
    }

    public void getAllChats() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("fri_id", fri_id));
        new WebServiceBaseResponseList<ChatMsgPOJO>(nameValuePairs, this, new ResponseListCallback<ChatMsgPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ChatMsgPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        chatMsgPOJOS.addAll(responseListPOJO.getResultList());
                        chatAdapter.notifyDataSetChanged();
                        rv_chat.scrollToPosition(chatMsgPOJOS.size() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ChatMsgPOJO.class, "CALL_CHAT_API", false).execute(WebServicesUrls.GET_ALL_CHATS);

    }

    public void sendChat() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("fri_id", fri_id));
        nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getcurrentDateTIme()[0]));
        nameValuePairs.add(new BasicNameValuePair("time", UtilityFunction.getcurrentDateTIme()[1]));
        nameValuePairs.add(new BasicNameValuePair("msg", et_msg.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("type", "1"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        Gson gson = new Gson();
                        ChatMsgPOJO chatMsgPOJO = gson.fromJson(jsonObject.optJSONObject("result").toString(), ChatMsgPOJO.class);
                        chatMsgPOJOS.add(chatMsgPOJO);
                        chatAdapter.notifyDataSetChanged();
                        rv_chat.scrollToPosition(chatMsgPOJOS.size() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_CHAT_API", false).execute(WebServicesUrls.SEND_CHAT);
        et_msg.setText("");
    }


    ChatAdapter chatAdapter;
    List<ChatMsgPOJO> chatMsgPOJOS = new ArrayList<>();

    public void attachAdapter() {

        chatAdapter = new ChatAdapter(this, null, chatMsgPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_chat.setHasFixedSize(true);
        rv_chat.setAdapter(chatAdapter);
        rv_chat.setLayoutManager(linearLayoutManager);
        rv_chat.setItemAnimator(new DefaultItemAnimator());
        rv_chat.setNestedScrollingEnabled(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.CHAT_CLASS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TagUtils.getTag(), "chatresult:-" + result);
            try {
                Gson gson = new Gson();
                ChatMsgPOJO chatResultPOJO = gson.fromJson(new JSONObject(result).optJSONObject("data").toString(), ChatMsgPOJO.class);
                Log.d(TagUtils.getTag(), "user id:-" + Constants.userDetailPOJO.getUserId());
                if (chatResultPOJO.getFriId().equals(Constants.userDetailPOJO.getUserId()) || chatResultPOJO.getUserId().equals(Constants.userDetailPOJO.getUserId())) {
                    chatMsgPOJOS.add(chatResultPOJO);
                    chatAdapter.notifyDataSetChanged();
                    rv_chat.scrollToPosition(chatMsgPOJOS.size() - 1);
//                    Log.d(TagUtils.getTag(), "user chats:-" + databaseHelper.getUserChatList(user_id, friend_user_id).size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
