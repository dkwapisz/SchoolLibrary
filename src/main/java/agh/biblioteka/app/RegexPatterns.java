package agh.biblioteka.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatterns {

    // Name and Surname Pattern: min. 2 chars, only letters (with polish letters)
    private static final Pattern namePattern = Pattern.compile("^[a-zżźćńółęąśŻŹĆŃÓŁĘĄŚ]{2,}$", Pattern.CASE_INSENSITIVE);

    // Username Pattern: 4-32 chars, only letters, digits and _
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_]{4,32}$");

    // Password Pattern: 8-50 chars, min. 1 digit and min. 1 letter (with polish letters)
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-zżźćńółęąśŻŹĆŃÓŁĘĄŚ])(?=.*[0-9]).{8,50}$", Pattern.CASE_INSENSITIVE);

    // Email Pattern
    private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // Phone Pattern
    private static final Pattern phoneNumberPattern = Pattern.compile("^[0-9]{9}$"); // nine digits

    // Class Pattern (examples: 1C, 2A, 6F)
    private static final Pattern classPattern = Pattern.compile("^[1-8][A-Z]$", Pattern.CASE_INSENSITIVE);

    // Number of copies for book Pattern: integer between 1-9999
    private static final Pattern copiesPattern = Pattern.compile("^[1-9][0-9]{0,3}$", Pattern.CASE_INSENSITIVE);

    // Year of publication Pattern: year between 100-9999
    private static final Pattern yearPattern = Pattern.compile("^[1-9][0-9]{2,3}$");

    // Number of pages for book Pattern: books pages 1-9999
    private static final Pattern pagesPattern = Pattern.compile("^[1-9][0-9]{0,3}$", Pattern.CASE_INSENSITIVE);

    public static boolean matchName(String name) {
        Matcher matcherName = RegexPatterns.namePattern.matcher(name);
        return matcherName.find();
    }

    public static boolean matchUsername(String username) {
        Matcher matcherUsername = RegexPatterns.usernamePattern.matcher(username);
        return matcherUsername.find();
    }

    public static boolean matchPassword(String password) {
        Matcher matcherPassword = RegexPatterns.passwordPattern.matcher(password);
        return matcherPassword.find();
    }

    public static boolean matchEmail(String email) {
        Matcher matcherEmail = RegexPatterns.emailPattern.matcher(email);
        return matcherEmail.find();
    }

    public static boolean matchPhone(String phone) {
        Matcher matcherPhone = RegexPatterns.phoneNumberPattern.matcher(phone);
        return matcherPhone.find();
    }

    public static boolean matchClass(String studentClass) {
        if (studentClass.equals("")) {
            return false;
        }

        Matcher matcherStudentClass = RegexPatterns.classPattern.matcher(studentClass);
        return matcherStudentClass.find();
    }

    public static boolean matchNrOfCopies(String nrOfCopies) {
        Matcher matcherCopies = RegexPatterns.copiesPattern.matcher(nrOfCopies);
        return matcherCopies.find();
    }

    public static boolean matchYear(String year) {
        Matcher matcherYear = RegexPatterns.yearPattern.matcher(year);
        return matcherYear.find();
    }

    public static boolean matchNrOfPages(String nrOfPages) {
        Matcher matcherPages = RegexPatterns.pagesPattern.matcher(nrOfPages);
        return matcherPages.find();
    }
}
