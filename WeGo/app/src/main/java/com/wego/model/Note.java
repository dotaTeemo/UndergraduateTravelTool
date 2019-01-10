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
 * Created by Administrator on 2017/7/17 0017.
 */

public class Note extends BmobObject implements Serializable{
    private User person;
    private String title;
    private String content;
    private BmobFile photo;

    private Integer praise;
    private Integer like;
    private List<NoteComment> comments;

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public void addPraise() {
        this.praise += 1;

        this.setValue("praise", praise);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "点赞失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void undoPraise() {
        this.praise -= 1;

        this.setValue("praise", praise);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "取消赞成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "取消赞失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public void addLike() {
        this.like += 1;

        this.setValue("like", like);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "收藏失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void undoLike() {
        this.like -= 1;

        this.setValue("like", like);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "取消收藏成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "取消收藏失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public List<NoteComment> getComments() {
        return comments;
    }

    public void setComments(List<NoteComment> comments) {
        this.comments = comments;
    }

    public void addComment(NoteComment comment) {
        if(comments.isEmpty()) {
            comments = new ArrayList<NoteComment>();
        }

        this.comments.add(comment);

        this.setValue("comments", comments);
        this.update(this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "评论失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
