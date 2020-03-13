package com.ffisherr.lbg;

public class UserResponse {
    private String status;
    private String login;
    private String FirstName;
    private String SurName;
    private String SecondName;
    private String passw_hash;
    private Integer id;
    private Integer role_id;
    private Integer university_id;

    public UserResponse() {
        this.status = "";
        this.login  = "";
        this.passw_hash  = "";
        this.FirstName = "";
        this.SecondName = "";
        this.SurName = "";
        this.id = -1;
        this.role_id = -1;
        this.university_id = -1;
    }

    public UserResponse(String login, String passw_hash) {
        this.status = "";
        this.login  = login;
        this.passw_hash  = passw_hash;
        this.FirstName = "";
        this.SecondName = "";
        this.SurName = "";
        this.id = -1;
        this.role_id = -1;
        this.university_id = -1;
    }

    public UserResponse(String status, String login, String passw_hash, Integer id, String FirstName,
                        String SurName, String SecondName, Integer role_id, Integer university_id) {
        this.status = status;
        this.login  = login;
        this.passw_hash  = passw_hash;
        this.FirstName = FirstName;
        this.SecondName = SecondName;
        this.SurName = SurName;
        this.id = id;
        this.role_id = role_id;
        this.university_id = university_id;
    }

    public String getPassw_hash() {
        return passw_hash;
    }

    public void setPassw_hash(String passw_hash) {
        this.passw_hash = passw_hash;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public void setUniversity_id(Integer university_id) {
        university_id = university_id;
    }

    public String getStatus() {
        return status;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getSurName() {
        return SurName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public Integer getUniversity_id() {
        return university_id;
    }

    public boolean checkIfOk() {
        if (!this.FirstName.equals("") && !this.SurName.equals("")
                && !this.passw_hash.equals("") && !this.login.equals(""))
            return true;
        return false;
    }
}
