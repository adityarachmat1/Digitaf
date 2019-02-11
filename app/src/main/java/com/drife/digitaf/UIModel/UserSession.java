package com.drife.digitaf.UIModel;

import com.drife.digitaf.retrofitmodule.Model.Profile;
import com.drife.digitaf.retrofitmodule.Model.User;

import java.io.Serializable;

public class UserSession implements Serializable {
    private String Token;
    private String UseFingerprint; //0 == no, 1 == yes
    private User user;
    private Profile profile;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUseFingerprint() {
        return UseFingerprint;
    }

    public void setUseFingerprint(String useFingerprint) {
        UseFingerprint = useFingerprint;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
