# School Library
> Database Systems Project
## Table of Contents
- [Features](#Features)
- [Technologies Used](#Technologies-Used)
- [Screenshots](#Screenshots)
- [Database Scheme](#Database-Scheme)
- [How to open](#How-to-open)
- [Acknowledgments](#Acknowledgments)

## Features
The application allows to manage and operate school library. There are two modes of access: regular user and administrator.

Regular user can:
- create an account
- borrow books (maximum 5 and for a maximum of 30 days),
- browse through the history of his borrowed books,
- view currently available books, 
- check information about the books such as number of pages, publisher, description.

The administrator can:
- manage existing rentals,
- manage users (edit their data or delete users),
- add/delete/modify books.

## Technologies Used
- Java 17.0.1
- JavaFX Library + FXML (SceneBuilder)
- Maven
- MySQL Database (Remote, with SSH Tunneling)

## Screenshots
![screenshot1](https://github.com/dkwapisz/SchoolLibrary/blob/master/screenshots/screen1.png)
![screenshot2](https://github.com/dkwapisz/SchoolLibrary/blob/master/screenshots/screen2.png)

## Database Scheme
![database_scheme](https://github.com/dkwapisz/SchoolLibrary/blob/master/screenshots/database_scheme.png)

## How to open 
### IntelliJ IDEA
1. Download project via Github
2. Open using IntelliJ IDEA
3. Reload Maven Dependencies
4. Set database parameters in Database.java class. You can use remote database with SSH Tunnel or change Database.java class to use local MySQL database. 
5. Run via Main.java class

To create database tables with sample data, use [this file](https://github.com/dkwapisz/SchoolLibrary/blob/master/database.sql). You can also use this file to create empty tables.

Administrator account:
- Username: admin
- Password: admin

## Acknowledgments
- Project was created in cooperation with [Agnieszka Żupnik](https://github.com/agnieszkowe) and [Anna Młynarczyk](https://github.com/annamlynarczyk)
