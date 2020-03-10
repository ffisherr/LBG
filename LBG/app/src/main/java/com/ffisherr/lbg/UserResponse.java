package com.ffisherr.lbg;

public class UserResponse {
    private String status;
    private String login;
    private String FirstName;
    private String SurName;
    private String SecondName;
    private String Passw;
    private Integer id;
    private Integer Role_id;
    private Integer University_id;

    public UserResponse(String status, String login, String Passw, Integer id, String FirstName,
                        String SurName, String SecondName, Integer Role_id, Integer University_id) {
        this.status = status;
        this.login  = login;
        this.Passw  = Passw;
        this.FirstName = FirstName;
        this.SecondName = SecondName;
        this.SurName = SurName;
        this.id = id;
        this.Role_id = Role_id;
        this.University_id = University_id;
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

    public String getPassw() {
        return Passw;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRole_id() {
        return Role_id;
    }

    public Integer getUniversity_id() {
        return University_id;
    }
}
