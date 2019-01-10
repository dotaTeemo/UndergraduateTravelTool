package com.wego.mainFragment;

import com.wego.model.Note;

/**
 * Created by DELL on 2017/7/18.
 */

public class YJEvent {
    public YJEvent(){};
    private Note note;

    public YJEvent(Note note){
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
