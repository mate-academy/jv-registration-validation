package core.basesyntax.service;

public enum Errors {
    Login_NotNull("Login cannot be null"),
    Password_NotNull("Password cannot be null"),
    Age_NotNull("Age cannot be null"),

    Login_inUse("This login is already in use"),

    Short_UserLogin("User login is too short"),
    Short_UserPassword("User password is too short"),

    User_AgeYounger("User age is under 18 years");

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
