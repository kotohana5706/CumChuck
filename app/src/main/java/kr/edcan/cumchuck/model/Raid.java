package kr.edcan.cumchuck.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Chad on 7/18/16.
 */
public class Raid {
    public String host, title, content, resId, raidMax, resTitle, resAddress, id;
    public boolean commit;
    public User[] member;
    public Date date;

    public String getHost() {
        return host;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getResId() {
        return resId;
    }

    public String getRaidMax() {
        return raidMax;
    }

    public boolean isCommit() {
        return commit;
    }

    public User[] getMember() {
        return member;
    }

    public Date getRaidDate() {
        return date;
    }

    public String getResTitle() {
        return resTitle;
    }

    public String getResAddress() {
        return resAddress;
    }
}
