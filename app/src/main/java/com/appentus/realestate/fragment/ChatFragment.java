package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.ChatAdapter;
import com.appentus.realestate.adapter.ChatUserAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.ChatMsgPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Constants;
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

public class ChatFragment extends FragmentController{

    @BindView(R.id.rv_chat_users)
    RecyclerView rv_chat_users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_chat,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();
//        getList();
    }


    public void getList(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        new WebServiceBaseResponseList<ChatMsgPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ChatMsgPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ChatMsgPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        chatMsgPOJOS.clear();
                        chatMsgPOJOS.addAll(responseListPOJO.getResultList());
                        chatAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        },ChatMsgPOJO.class,"CALL_CHAT_API",true).execute(WebServicesUrls.CHAT_USERS);

    }

    ChatUserAdapter chatAdapter;
    List<ChatMsgPOJO> chatMsgPOJOS= new ArrayList<>();

    public void attachAdapter() {

        chatAdapter = new ChatUserAdapter(getActivity(), this, chatMsgPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_chat_users.setHasFixedSize(true);
        rv_chat_users.setAdapter(chatAdapter);
        rv_chat_users.setLayoutManager(linearLayoutManager);
        rv_chat_users.setItemAnimator(new DefaultItemAnimator());
        rv_chat_users.setNestedScrollingEnabled(false);

    }
}
