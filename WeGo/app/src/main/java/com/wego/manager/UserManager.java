package com.wego.manager;

import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.wego.model.Note;
import com.wego.model.Spot;
import com.wego.model.Travel;
import com.wego.model.User;
import com.wego.model.UserGo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class UserManager extends BaseManger {
    private UserManager() {}
    private static UserManager instance;

    public static  UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return  instance;
    }

    public void regist(String phoneNum, String passWord, String userName) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(passWord);
        user.setAddress("北京");
        user.setMobilePhoneNumber(phoneNum);
        //user.setHeadImage(null);
        user.setMale(true);
        user.setAge(0);
        user.setPerSignature("");
        user.signUp(new SaveListener<String>() {
            @Override
            public void done(String s, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    registUserGo();
                }
            }
        });
        this.setUser(user);


    }

    public void registUserGo() {
        UserGo userGo = new UserGo();
        userGo.setHeadImage(null);
        userGo.setBackgroundImage(null);
        userGo.setTravel(getTravel());
        List<Note> notes = new ArrayList<Note>();
        userGo.setLikeNotes(notes);
        List<Spot> spots = new ArrayList<Spot>();
        userGo.setLikeSpot(spots);
        userGo.setPerson(user);
        userGo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "信息注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "信息注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.setUserGo(userGo);
    }

    public Travel getTravel() {
        TravelManager.getInstance().initTravelWithCityName("北京", new LatLng(39.91667, 116.41667));

        return TravelManager.getInstance().getTravel();
    }
}
