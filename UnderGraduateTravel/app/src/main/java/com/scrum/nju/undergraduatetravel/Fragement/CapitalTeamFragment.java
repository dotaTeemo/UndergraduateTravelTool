package com.scrum.nju.undergraduatetravel.Fragement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Adapter.CapitalAdapter;
import com.scrum.nju.undergraduatetravel.Adapter.ContactAdapter;
import com.scrum.nju.undergraduatetravel.Adapter.DialogMemberAdapter;
import com.scrum.nju.undergraduatetravel.Adapter.FriendsAddAdapter;
import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.MiddleClass.MyOkHttp;
import com.scrum.nju.undergraduatetravel.MiddleClass.Team;
import com.scrum.nju.undergraduatetravel.MiddleClass.User;
import com.scrum.nju.undergraduatetravel.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CapitalTeamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CapitalTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CapitalTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int GETTEAM=1;
    private static final int GETFRIEND=2;

    private OnFragmentInteractionListener mListener;
    private CapitalAdapter itemAdapter;
    private DialogMemberAdapter adapter;

    ArrayList <Team> Teams=new <Team>ArrayList();
    ArrayList <User>contactNames=new <User>ArrayList();


    @Bind(R.id.rv_selector)
    RecyclerView contactList;

    @Bind(R.id.recycleviewcapital)
    RecyclerView recyclerViewcapital;

    @Bind(R.id.newcapital)
    Button newcapital;

    public CapitalTeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CapitalTeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CapitalTeamFragment newInstance(String param1, String param2) {
        CapitalTeamFragment fragment = new CapitalTeamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Handler handlerCap = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GETTEAM) {
                CapitalAdapter itemAdapter=new CapitalAdapter(Teams, getActivity());//添加适配器，这里适配器刚刚装入了数据
                recyclerViewcapital.setAdapter(itemAdapter);
                recyclerViewcapital.setLayoutManager(new LinearLayoutManager(getActivity()));//设置布局管理器，这里选择用竖直的列表
//                itemAdapter.setOnItemClickListener(MyItemClickListener);
            }else if(msg.what == GETFRIEND){
                adapter = new DialogMemberAdapter(contactNames,getActivity());
                contactList.setAdapter(adapter);
                contactList.setLayoutManager(new LinearLayoutManager(getActivity()));
            }else{
                Toast.makeText(getActivity(), "发送失败，检查网络连接", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);

        }

    };

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

        View view =inflater.inflate(R.layout.fragment_capital_team, container, false);
        ButterKnife.bind(this,view);
        List<String> list2 = new ArrayList<>();
//        CapitalAdapter itemAdapter2=new CapitalAdapter(list2, getActivity());//添加适配器，这里适配器刚刚装入了数据
//        final String url = HostIp.ip + "/getAllTeam";

//        for (int i = 3; i < 15; i++) {
//            list2.add("" + i);
//        }
//        List<String> list = new ArrayList<>();
//        CapitalAdapter itemAdapter=new CapitalAdapter(list, getActivity());//添加适配器，这里适配器刚刚装入了数据
//        searchitem.setLayoutManager(new LinearLayoutManager(getActivity()));
//        searchitem.setAdapter(itemAdapter2);



        newcapital.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final EditText edit=new EditText(getActivity());
                //edit.setBackgroundColor(getResources().getColor(R.color.cyanosis));
                edit.setInputType(InputType.TYPE_CLASS_TEXT);
                edit.setLines(1);
                edit.setMaxLines(1);
                new AlertDialog.Builder(getActivity())
                        .setTitle("确定要创建吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String str1 = edit.getText().toString();
                                        userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                                        final  String url9=HostIp.ip + "/buildTeam?account="+userManager.getAccountId();
                                        String result = MyOkHttp.get(url9);
//                                        Log.e("创建信息",result);
//                                        JSONObject jsonObject2 = new JSONObject(result);
//                                        Message msg = new Message();
//                                        msg.what = ;
//                                        msg.obj = jsonObject2.getString("response");
//                                        handler2.sendMessage(msg);
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        getnewTeam();

        getnewmsg();


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

    //加载队伍列表
    private void getnewTeam(){
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
                    String url6 = HostIp.ip + "/getTeamsByAccount?account="+userManager.getAccountId();
                    String result = MyOkHttp.get(url6);
                    Log.e("请求信息",result);
                    JSONObject jsonObject2 = new JSONObject(result);
                    JSONArray jsonArray = jsonObject2.getJSONArray("response");
                    Teams.clear();
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Teams.add(new Team(jsonObject.getString("teamId"),jsonObject.getString("account")));
                    }
                    Log.e("messa",Teams.toString());
                    Message mes=new Message();
                    mes.what=GETTEAM;
                    handlerCap.sendMessage(mes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                    handlerCap.sendMessage(mes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
