package org.example.Lesson4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonAddShoping {

        private String item;
        private String aisle;
        private boolean parse;

    public JsonAddShoping(String item, String aisle, boolean parse) {
        this.item = item;
        this.aisle = aisle;
        this.parse = parse;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public boolean isParse() {
        return parse;
    }

    public void setParse(boolean parse) {
        this.parse = parse;
    }
    public String json(JsonAddShoping apk){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return (gson.toJson(apk));
    }
}
