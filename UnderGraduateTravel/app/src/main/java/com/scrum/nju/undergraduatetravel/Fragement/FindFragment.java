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
import com.scrum.nju.undergraduatetravel.MiddleClass.Contact;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.MiddleClass.LetterView;
import com.scrum.nju.undergraduatetravel.MiddleClass.MyOkHttp;
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

    private RecyclerView contactList;
//    private String[] contactNames;
    ArrayList contactNames=new ArrayList();
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManagerforadd;
    private LetterView letterView;
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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//                getActivity().finish();
            }
            else{
                Toast.makeText(getActivity(), "请不要重复发送", Toast.LENGTH_SHORT).show();
            }}
        else{
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
         userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
        final String url2 = HostIp.ip + "/getFriend?account="+userManager.getAccountId();
//        contactNames.add("安然");
//        contactNames.add("奥兹");
        contactList = (RecyclerView) view.findViewById(R.id.contact_list);
        letterView = (LetterView) view.findViewById(R.id.letter_view);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = MyOkHttp.get(url2);
                    Log.e("注册信息",result);
                    JSONObject jsonObject2 = new JSONObject(result);
                    JSONArray jsonArray = jsonObject2.getJSONArray("response");
                    Message msg = new Message();
                    for (int i=0; i < jsonArray.length(); i++)    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        contactNames.add(jsonObject.getString("friend"));
                    }
                    adapter = new ContactAdapter(getActivity(), contactNames);
                    contactList.setAdapter(adapter);
                    adapter.setOnItemClickListener(MyItemClickListener);
//                    msg.what = UPDATE;
//                    msg.obj = contactNames;
//                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

//           contactNames = new String[] {"安然","奥兹","德玛","张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智"};
//


        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character),0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0,0);
            }
        });
        //好友请求加载
//        final String url3 = HostIp.ip + "/getFriend?account=12345678912";
//        RecyclerView recforAddFriends = (RecyclerView) view.findViewById(R.id.recforAddFriends);
//        letterView = (LetterView) view.findViewById(R.id.letter_view);
        //layoutManagerforadd = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

//        recforAddFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recforAddFriends.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//
//        List<userManager> list;
//        list.add(new userManager("111111"));
//        list.add("111111");
//        adapter = new FriendsAddAdapter(getActivity(), contactNames);
//        contactList.setAdapter(adapter);
//        adapter.setOnItemClickListener(MyItemClickListener);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String result = MyOkHttp.get(url2);
//                    Log.e("注册信息",result);
//                    JSONObject jsonObject2 = new JSONObject(result);
//
//                    JSONArray jsonArray = jsonObject2.getJSONArray("response");
//                    Message msg = new Message();
//                    for (int i=0; i < jsonArray.length(); i++)    {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        contactNames.add(jsonObject.getString("friend"));
//                    }
//                    adapter = new ContactAdapter(getActivity(), contactNames);
//                    contactList.setAdapter(adapter);
//                    adapter.setOnItemClickListener(MyItemClickListener);
////                    msg.what = UPDATE;
////                    msg.obj = contactNames;
////                    handler.sendMessage(msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
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
        public void onItemClick(View v, ContactAdapter.ViewName viewName, int position,String account) {
            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.btn_cancel:
                    adapter.notifyItemRemoved(position);
                    final String url3 = HostIp.ip + "/deleteFriend?account=12345678912&friend="+account;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(url3);
                                Log.e("注册信息",result);
                                JSONObject jsonObject2 = new JSONObject(result);
                                String response = jsonObject2.getString("response");
                                if (response=="true"){
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Toast.makeText(getActivity(),"你点击了同意按钮"+(position),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(),"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button appled = (Button) getActivity().findViewById(R.id.appled);
        appled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "nihao", Toast.LENGTH_LONG).show();
                LinearLayout sendaccount = (LinearLayout) getActivity().findViewById(R.id.myfriends);
                LinearLayout RequestAddFriends=getActivity().findViewById(R.id.RequestAddFriends);
                sendaccount.setVisibility(View.GONE);
                RequestAddFriends.setVisibility(View.VISIBLE);
            }
        });

        Button sendapply = (Button) getActivity().findViewById(R.id.sendapply);
        sendapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sendaccount = (EditText) getActivity().findViewById(R.id.sendaccount);
                String str2=sendaccount.getText().toString();

                if (!TextUtils.isEmpty(str2)) {
                    userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                    final String url3 = HostIp.ip + "/addFriendApply?account="+userManager.getAccountId()+"&friend="+str2;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(url3);
                                Log.e("注册信息",result);
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
                LinearLayout sendaccount = (LinearLayout) getActivity().findViewById(R.id.myfriends);
                LinearLayout RequestAddFriends=getActivity().findViewById(R.id.RequestAddFriends);
                sendaccount.setVisibility(View.VISIBLE);
                RequestAddFriends.setVisibility(View.GONE);
            }
        });
    }
}
