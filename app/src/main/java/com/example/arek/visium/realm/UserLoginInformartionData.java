package com.example.arek.visium.realm;

import io.realm.RealmObject;

/**
 * Created by arek on 7/25/2017.
 */

public class UserLoginInformartionData extends RealmObject {

    private String m_token;
    private String m_email;
    private String m_password;

    public String getM_token() {
        return m_token;
    }

    public void setM_token(String m_token) {
        this.m_token = m_token;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }
}
