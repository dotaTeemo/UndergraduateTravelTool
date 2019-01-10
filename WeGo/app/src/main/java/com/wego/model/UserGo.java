package com.wego.model;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class UserGo extends BmobObject implements Serializable{
    private User person;
    private BmobFile headImage;
    private BmobFile backgroundImage;

    private Travel travel;//行程
    private List<Note> likeNotes;//收藏游记
    private List<Spot> likeSpots;//收藏景点

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public BmobFile getHeadImage() {
        return headImage;
    }

    public void setHeadImage(BmobFile headImage) {
        this.headImage = headImage;
    }

    public BmobFile getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BmobFile backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public List<Note> getLikeNotes() {
        return likeNotes;
    }

    public void setLikeNotes(List<Note> likeNotes) {
        this.likeNotes = likeNotes;
    }

    public void addLikeNote(Note likeNote) {
        if(likeNotes.isEmpty()) {
            likeNotes = new ArrayList<Note>();
        }

        this.likeNotes.add(likeNote);

        this.setValue("likeNotes", likeNotes);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "收藏失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public List<Spot> getLikeSpots() {
        return likeSpots;
    }

    public void setLikeSpot(List<Spot> likeSpots) {
        this.likeSpots = likeSpots;
    }

    public void addLikeSpot(Spot likeSpot) {
        if(likeSpots.isEmpty()) {
            likeSpots = new ArrayList<Spot>();
        }

        this.likeSpots.add(likeSpot);

        this.setValue("likeSpots", likeSpots);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "收藏失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
