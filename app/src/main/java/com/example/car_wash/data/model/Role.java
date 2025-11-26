package com.example.car_wash.data.model;

public class Role {
    private String uid;
    private String roleName;

    public Role() {};
    public Role(String uid, String roleName) {
        this.uid = uid;
        this.roleName = roleName;
    }

    public String getUid() {
        return uid;
    }

    public String getRoleName() {
        return roleName;
    }
}
