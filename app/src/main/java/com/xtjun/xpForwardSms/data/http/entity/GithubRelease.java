package com.xtjun.xpForwardSms.data.http.entity;

import com.google.gson.annotations.SerializedName;

public class GithubRelease {

    @SerializedName("tag_name")
    private String tagName;
    @SerializedName("name")
    private String name;
    @SerializedName("body")
    private String body;

    public GithubRelease() {

    }

    public String getTagName() {
        return tagName;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "GithubRelease{" +
                "tagName='" + tagName + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
