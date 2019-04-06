package com.scrum.nju.undergraduatetravel.MiddleClass;

public class Team {
    private String Teamid;
    private String Teamname;

    public String getTeamid() {
        return Teamid;
    }

    public String getTeamname() {
        return Teamname;
    }

    public void setTeamid(String teamid) {
        Teamid = teamid;
    }

    public void setTeamname(String teamname) {
        Teamname = teamname;
    }

    public Team(String teamid, String teamname) {
        Teamid = teamid;
        Teamname = teamname;
    }
}
