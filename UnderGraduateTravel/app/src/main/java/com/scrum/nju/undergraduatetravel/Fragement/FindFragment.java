package com.scrum.nju.undergraduatetravel.Fragement;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Activity.RegisterActivity;
import com.scrum.nju.undergraduatetravel.Adapter.ContactAdapter;
import com.scrum.nju.undergraduatetravel.Adapter.FriendsAddAdapter;
import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.MiddleClass.LetterView;
import com.scrum.nju.undergraduatetravel.MiddleClass.MyOkHttp;
import com.scrum.nju.undergraduatetravel.MiddleClass.User;
import com.scrum.nju.undergraduatetravel.R;
import com.scrum.nju.undergraduatetravel.MiddleClass.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int UPDATE = 0;
    private static final int SENDAPPLY =1;//登录验证
    private static final int ADDFRIEND=2;
    private static final int GETFRIEND=3;
    private static final int DELFRIEND=4;
    private static final int GETApplyFRIEND=5;

    private RecyclerView contactList;
    private RecyclerView AddfriendList;
//    private String[] contactNames;

    ArrayList <User>contactNames=new <User>ArrayList();
    ArrayList <User> contactApplyNames=new <User> ArrayList();

    private LinearLayoutManager layoutManager;
    private  EditText sendaccount;
    private FriendsAddAdapter addFriendadapter;
    private ContactAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE) {
                if(msg.obj.toString() == "true"){
                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                    sendaccount.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//                getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(), "请不要重复发送", Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what == ADDFRIEND){
                int position=Integer.parseInt(msg.obj.toString());
                contactApplyNames.remove(position);
                addFriendadapter.notifyItemRemoved(position);
                addFriendadapter.notifyItemRangeChanged(position,contactApplyNames.size()-position);
            }else if(msg.what == DELFRIEND){
                int position=Integer.parseInt(msg.obj.toString());
                contactNames.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position,contactNames.size()-position);
            }
            //加载朋友列表
            else if(msg.what == GETFRIEND){
                adapter = new ContactAdapter(contactNames,getActivity());
                contactList.setAdapter(adapter);
                adapter.setOnItemClickListener(MyItemClickListener);
            }else if(msg.what == GETApplyFRIEND){
                addFriendadapter = new FriendsAddAdapter(contactApplyNames,getActivity());
                AddfriendList.setAdapter(addFriendadapter);
                addFriendadapter.setOnItemClickListener(Myaddfriendsitemclick);
            }else{
                    Toast.makeText(getActivity(), "发送失败，检查网络连接", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);

        }

    };

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_find, container, false);
//        handler=new Handler();

        contactList = (RecyclerView) view.findViewById(R.id.contact_list);
//        letterView = (LetterView) view.findViewById(R.id.letter_view);
        AddfriendList = view.findViewById(R.id.recforAddFriends);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        AddfriendList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        getnewmsg();
        getnewApply();

      return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private ContactAdapter.OnItemClickListener MyItemClickListener = new ContactAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, ContactAdapter.ViewName viewName,final int position) {

            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.btn_cancel:
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    final String url3 = HostIp.ip + "/deleteFriend?account="+userManager.getAccountId()+"&friend="+contactNames.get(position).getAccountId();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(url3);
                                Log.e("注册信息",result);
                                JSONObject jsonObject2 = new JSONObject(result);
                                String response = jsonObject2.getString("response");
                                if (response=="true"){
                                    Message mes = new Message();
                                    mes.what=DELFRIEND;
                                    mes.obj=position;
                                    handler2.sendMessage(mes);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Toast.makeText(getActivity(),"你点击了删除按钮"+position,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(),"你点击了item按钮"+position,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //添加朋友点击
    private FriendsAddAdapter.OnItemClickListener Myaddfriendsitemclick=new FriendsAddAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, FriendsAddAdapter.ViewName viewName,final int position) {
            switch (v.getId()) {
                case R.id.btn_accept:
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    final String url4 = HostIp.ip + "/permitFriend?account="+userManager.getAccountId()+"&friend="+contactApplyNames.get(position).getAccountId();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(url4);
                                JSONObject jsonObject2 = new JSONObject(result);
                                String response = jsonObject2.getString("response");
                                if (response=="true"){
                                    Message msg = new Message();
                                    msg.what = ADDFRIEND;
                                    msg.obj = position;
                                    handler2.sendMessage(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case R.id.btn_refuse:
                    contactApplyNames.remove(position);
                    addFriendadapter.notifyItemRemoved(position);
                    addFriendadapter.notifyItemRangeChanged(position,contactApplyNames.size());
                    break;
            }
            }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    //好友请求查看的监听
        Button appled = (Button) getActivity().findViewById(R.id.appled);
        appled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnewApply();

                LinearLayout sendaccount = (LinearLayout) getActivity().findViewById(R.id.myfriends);
                LinearLayout RequestAddFriends=getActivity().findViewById(R.id.RequestAddFriends);
                sendaccount.setVisibility(View.GONE);
                RequestAddFriends.setVisibility(View.VISIBLE);

            }
        });

        //发送好友请求
        Button sendapply = (Button) getActivity().findViewById(R.id.sendapply);
        sendapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendaccount= (EditText) getActivity().findViewById(R.id.sendaccount);
                String str2=sendaccount.getText().toString();

                if (!TextUtils.isEmpty(str2)) {
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    final String url3 = HostIp.ip + "/addFriendApply?account="+str2+"&friend="+userManager.getAccountId();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(url3);
                                JSONObject jsonObject2 = new JSONObject(result);
                                Message msg = new Message();
                                msg.what = UPDATE;
                                msg.obj = jsonObject2.getString("response");
                                handler2.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
//                    Toast.makeText(getActivity(), "buhao", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "请输入要添加的好友账号", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button backTo = (Button) getActivity().findViewById(R.id.backTo);
        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getnewmsg();
                LinearLayout sendaccount = (LinearLayout) getActivity().findViewById(R.id.myfriends);
                LinearLayout RequestAddFriends=getActivity().findViewById(R.id.RequestAddFriends);
                sendaccount.setVisibility(View.VISIBLE);
                RequestAddFriends.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
    }


    //加载好友列表
    private void  getnewmsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    Boolean isempty=true;
                    while(isempty){
                        try {
                            if (TextUtils.isEmpty(userManager.getAccountId())) {
                                Thread.sleep(10);
                            }else {
                                isempty=false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }}
                        //网络请求
                    String url6 = HostIp.ip + "/getFriend?account="+userManager.getAccountId();
                    String result = MyOkHttp.get(url6);
                    Log.e("注册信息",result);
                    JSONObject jsonObject2 = new JSONObject(result);
                    JSONArray jsonArray = jsonObject2.getJSONArray("response");
                    contactNames.clear();
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        contactNames.add(new User(jsonObject.getString("friend")));
                    }
                    Log.e("messa",contactNames.toString());
                    Message mes=new Message();
                    mes.what=GETFRIEND;
                    handler2.sendMessage(mes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //加载好友申请列表
    private void getnewApply(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    Boolean isempty=true;
                    while(isempty){
                        try {
                            if (TextUtils.isEmpty(userManager.getAccountId())) {
                                Thread.sleep(10);
                            }else {
                                isempty=false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }}
                    String url6 = HostIp.ip + "/getFriendApplies?account="+userManager.getAccountId();
                    String result = MyOkHttp.get(url6);
                    Log.e("请求信息",result);
                    JSONObject jsonObject2 = new JSONObject(result);
                    JSONArray jsonArray = jsonObject2.getJSONArray("response");
                    contactApplyNames.clear();
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        contactApplyNames.add(new User(jsonObject.getString("friend")));
                    }
                    Log.e("messa",contactApplyNames.toString());
                    Message mes=new Message();
                    mes.what=GETApplyFRIEND;
                    handler2.sendMessage(mes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
