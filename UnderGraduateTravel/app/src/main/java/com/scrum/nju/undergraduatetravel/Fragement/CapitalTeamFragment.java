package com.scrum.nju.undergraduatetravel.Fragement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Activity.ForgetActivity;
import com.scrum.nju.undergraduatetravel.Activity.LoginActivity;
import com.scrum.nju.undergraduatetravel.Activity.RegisterActivity;
import com.scrum.nju.undergraduatetravel.Adapter.CapitalAdapter;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.R;

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

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.search)
    SearchView SearchviewC;

    @Bind(R.id.recycleviewcapital)
    RecyclerView recyclerViewcapital;

    @Bind(R.id.searchitem)
    RecyclerView searchitem;

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
        CapitalAdapter itemAdapter2=new CapitalAdapter(list2, getActivity());//添加适配器，这里适配器刚刚装入了数据
//        final String url = HostIp.ip + "/getAllTeam";

        for (int i = 3; i < 15; i++) {
            list2.add("" + i);
        }
        List<String> list = new ArrayList<>();
        CapitalAdapter itemAdapter=new CapitalAdapter(list, getActivity());//添加适配器，这里适配器刚刚装入了数据
        searchitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchitem.setAdapter(itemAdapter2);

        recyclerViewcapital.setAdapter(itemAdapter);

        recyclerViewcapital.setLayoutManager(new LinearLayoutManager(getActivity()));//设置布局管理器，这里选择用竖直的列表


        for (int i = 5; i < 15; i++) {
            list.add("" + i);
        }



        SearchviewC.setOnSearchClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                recyclerViewcapital.setVisibility(View.INVISIBLE);
                searchitem.setVisibility(View.VISIBLE);
            }
        });
        SearchviewC.setOnCloseListener(new SearchView.OnCloseListener(){

            @Override
            public boolean onClose() {
                recyclerViewcapital.setVisibility(View.VISIBLE);
                searchitem.setVisibility(View.GONE);
                return false;
            }
        });
        SearchviewC.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
//                recyclerViewcapital.setVisibility(View.GONE);
                searchitem.setVisibility(View.VISIBLE);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                recyclerViewcapital.setVisibility(View.GONE);
                searchitem.setVisibility(View.VISIBLE);
                //do something
                //当没有输入任何内容的时候清除结果，看实际需求

                return false;
            }
        });
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


}
