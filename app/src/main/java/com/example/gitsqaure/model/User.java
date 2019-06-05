package com.example.gitsqaure.model;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("avatar_url")
    String avatar_url;
    @SerializedName("login")
    String login;
    @SerializedName("id")
    String id;
    @SerializedName("repos_url")
    String repos_url;
    @SerializedName("contributions")
    int contributions;

    public User() {
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }
}
