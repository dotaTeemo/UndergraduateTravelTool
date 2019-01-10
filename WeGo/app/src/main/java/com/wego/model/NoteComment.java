package com.wego.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class NoteComment implements Serializable {
    private String name;
    private String content;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
