package agh.biblioteka.tableViewItems;

public class Rental {

    private Integer uid;
    private Integer rid;
    private Integer bid;
    private String title;
    private String author;
    private String startDate;
    private String endDate;
    private String username;

    // Constructor for actual rentals (admin - managing rentals)
    public Rental(Integer uid, Integer rid, Integer bid, String title, String author, String username, String startDate, String endDate) {
        this.uid = uid;
        this.rid = rid;
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Constructor for actual rentals
    public Rental(Integer bid, String title, String author, String startDate, String endDate) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Constructor for history rentals
    public Rental(Integer bid, String title, String author, String startDate) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.startDate = startDate;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getBid() {
        return bid;
    }
    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
