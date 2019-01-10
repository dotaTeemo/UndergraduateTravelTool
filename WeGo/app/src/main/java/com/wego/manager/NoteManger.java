package com.wego.manager;

import android.widget.Toast;

import com.wego.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class NoteManger {
    private NoteManger() {}
    private static NoteManger instance;

    private Note note;
    private List<Note> publicNotes = new ArrayList<Note>();
    private List<Note> userNotes = new ArrayList<Note>();

    public static  NoteManger getInstance() {
        if(instance == null) {
            instance = new NoteManger();
        }
        return  instance;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }


    public List<Note> downloadPublicNotes() {
        BmobQuery<Note> query = new BmobQuery<Note>();

        String end ="2016-01-05 20:20:17";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1  =null;try{
            date1 = sdf1.parse(end);
        }catch(ParseException e){
        }
        query .addWhereLessThanOrEqualTo("updatedAt", new BmobDate(date1));
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    publicNotes.addAll(list);
                } else {

                }

            }
        });

        return publicNotes;
    }

    public List<Note> downloadUserNotes() {
        BmobQuery<Note> query = new BmobQuery<Note>();

        query.addWhereEqualTo("person", UserManager.getInstance().getUser());
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    userNotes = list;
                } else {

                }
            }
        });

        return userNotes;
    }

    public void uploadNote(Note userNote) {
        note = userNote;
        note.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "游记发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "游记发布失败，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
        publicNotes.add(note);
    }
}
