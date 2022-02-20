package agh.biblioteka.tableViewItems;

public class User {

    private Integer uid;
    private String name;
    private String surname;
    private String studentClass;
    private String username;
    private String email;
    private Integer phoneNumber;

    public User(Integer uid, String name, String surname, String studentClass, String username, String email, Integer phoneNumber) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.studentClass = studentClass;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
