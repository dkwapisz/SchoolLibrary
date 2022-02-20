package agh.biblioteka.tableViewItems;

public class Book {

    private final Integer bid;
    private String title;
    private String author;
    private String genre;
    private Integer yearOfPublication;
    private String publisher;
    private Integer noOfPages;
    private String targetGroup;
    private Integer availableCopies;

    public Book(Integer bid, String title, String author, String genre, Integer yearOfPublication, String publisher, Integer noOfPages, String targetGroup, Integer availableCopies) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
        this.noOfPages = noOfPages;
        this.targetGroup = targetGroup;
        this.availableCopies = availableCopies;
    }

    public Integer getBid() {
        return bid;
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

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }
    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getNoOfPages() {
        return noOfPages;
    }
    public void setNoOfPages(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getTargetGroup() {
        return targetGroup;
    }
    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }
}
