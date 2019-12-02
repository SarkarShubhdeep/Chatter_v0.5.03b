package com.basic.chatter_v05a;


public class Messages {

    private String message;
    private boolean seen;
    private String type;
    private long time;
    private String from;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Messages(String message, boolean seen, String type, long time, String from, String profile) {
        this.message = message;
        this.seen = seen;
        this.type = type;
        this.time = time;
        this.from = from;
        this.profile = profile;
    }

    private String profile;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Messages(String from) {
        this.from = from;
    }

    public Messages(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Messages(String message, boolean seen, String type, long time) {
        this.message = message;
        this.seen = seen;
        this.type = type;
        this.time = time;
    }
}
