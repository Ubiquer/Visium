package com.example.arek.visium.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arek on 7/25/2017.
 */

public class Token extends RealmObject{

    String m_token;

    public String getM_token() {
        return m_token;
    }

    public void setM_token(String m_token) {
        this.m_token = m_token;
    }
}
