package core.basesyntax;

public interface Constant {
    String VALID_LOGIN = "123456";
    String VALID_LOGIN2 = "12345678";
    String VALID_PASSWORD = "123456";
    String VALID_PASSWORD2 = "12345678";
    int VALID_AGE = 18;
    int VALID_AGE2 = 36;

    String INVALID_LOGIN = "";
    String INVALID_LOGIN2 = "123";
    String INVALID_PASSWORD = "";
    String INVALID_PASSWORD2 = "123";
    String INVALID_PASSWORD3 = "12345";
    int INVALID_AGE = 17;
    int INVALID_AGE2 = -17;

    int MIN_LENGTH_LOGIN = 6;
    int MIN_LENGTH_PASSWORD = 6;
    int MIN_AGE = 18;
}
