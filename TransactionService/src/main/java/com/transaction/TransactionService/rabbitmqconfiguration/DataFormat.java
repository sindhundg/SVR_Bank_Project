package com.transaction.TransactionService.rabbitmqconfiguration;

import org.json.simple.JSONObject;

public class DataFormat {
    private JSONObject jsonObject;

    public DataFormat() {
    }

    public DataFormat(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
