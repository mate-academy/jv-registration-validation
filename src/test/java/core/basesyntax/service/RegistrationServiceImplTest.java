package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User preparedForCheckingLoginUser1;
    private static User preparedForCheckingLoginUser2;
    private static User preparedForCheckingLoginUser3;
    private static User nullUser;
    private static User invalidPasswordUser1;
    private static User invalidPasswordUser2;
    private static User validPasswordUser1;
    private static User validPasswordUser2;
    private static User nullPasswordUser;
    private static User invalidAgeUser1;
    private static User invalidAgeUser2;
    private static User invalidAgeUser3;
    private static User validAgeUser1;
    private static User validAgeUser2;
    private static User nullLoginUser;
    private static User invalidLoginUser1;
    private static User invalidLoginUser2;
    private static User validLoginUser1;
    private static User validLoginUser2;
    private static User sameNameWithUser1;
    private static User sameNameWithUser2;
    private static User sameNameWithUser3;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void prepare() {
        String reallyStrongPassword = "123456789";
        int reallyCoolAge = 30;
        preparedForCheckingLoginUser1 = new User("Michael", reallyStrongPassword, reallyCoolAge);
        preparedForCheckingLoginUser2 = new User("Patrick", reallyStrongPassword, reallyCoolAge);
        preparedForCheckingLoginUser3 = new User("Jackson", reallyStrongPassword, reallyCoolAge);
        nullUser = null;
        invalidPasswordUser1 = new User("Olivia", "12345", reallyCoolAge);
        invalidPasswordUser2 = new User("Charlotte", "", reallyCoolAge);
        validPasswordUser1 = new User("Sophia", "123456", reallyCoolAge);
        validPasswordUser2 = new User("Isabella", "1234567", reallyCoolAge);
        nullPasswordUser = new User("William", null, reallyCoolAge);
        invalidAgeUser1 = new User("Benjamin", reallyStrongPassword, 16);
        invalidAgeUser2 = new User("Theodore", reallyStrongPassword, 0);
        invalidAgeUser3 = new User("Oliver", reallyStrongPassword, -7);
        validAgeUser1 = new User("Sebastian", reallyStrongPassword, 19);
        validAgeUser2 = new User("Samuel", reallyStrongPassword, 18);
        invalidLoginUser1 = new User("Carl", reallyStrongPassword, reallyCoolAge);
        invalidLoginUser2 = new User("", reallyStrongPassword, reallyCoolAge);
        validLoginUser1 = new User("Polina", reallyStrongPassword, reallyCoolAge);
        validLoginUser2 = new User("Tetiana", reallyStrongPassword, reallyCoolAge);
        nullLoginUser = new User(null, reallyStrongPassword, reallyCoolAge);
        sameNameWithUser1 = new User("Michael", reallyStrongPassword, reallyCoolAge);
        sameNameWithUser2 = new User("Patrick", reallyStrongPassword, reallyCoolAge);
        sameNameWithUser3 = new User("Jackson", reallyStrongPassword, reallyCoolAge);
    }

    @Test
    void register_invalidPasswordUsers_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidPasswordUser1);
            registrationService.register(invalidPasswordUser2);
        });
    }

    @Test
    void register_validPasswordUsers_ok() {
        User actual1 = registrationService.register(validPasswordUser1);
        User actual2 = registrationService.register(validPasswordUser2);
        assertEquals(validPasswordUser1, actual1);
        assertEquals(validPasswordUser2, actual2);
    }

    @Test
    void register_nullPasswordUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_invalidAgeUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidAgeUser1);
            registrationService.register(invalidAgeUser2);
            registrationService.register(invalidAgeUser3);
        });
    }

    @Test
    void register_validAgeUsers_ok() {
        User actual1 = registrationService.register(validAgeUser1);
        User actual2 = registrationService.register(validAgeUser2);
        assertEquals(validAgeUser1, actual1);
        assertEquals(validAgeUser2, actual2);
    }

    @Test
    void register_nullLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_invalidLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidLoginUser1);
            registrationService.register(invalidLoginUser2);
        });
    }

    @Test
    void register_validLoginUsers_ok() {
        User actual1 = registrationService.register(validLoginUser1);
        User actual2 = registrationService.register(validLoginUser2);
        assertEquals(validLoginUser1, actual1);
        assertEquals(validLoginUser2, actual2);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_sameNameWithUser1_notOk() {
        Storage.people.add(preparedForCheckingLoginUser1);
        Storage.people.add(preparedForCheckingLoginUser2);
        Storage.people.add(preparedForCheckingLoginUser3);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameNameWithUser1);
            registrationService.register(sameNameWithUser2);
            registrationService.register(sameNameWithUser3);
        });
    }
}
