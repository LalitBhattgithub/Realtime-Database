package com.example.aplloprivateltd;

public class MainModal {
    String name,email,department,eurl;

    MainModal(){

    }

    public MainModal(String name, String email, String department, String eurl) {

        this.name = name;
        this.email = email;
        this.department = department;
        this.eurl = eurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEurl() {
        return eurl;
    }

    public void setEurl(String eurl) {
        this.eurl = eurl;
    }
}
