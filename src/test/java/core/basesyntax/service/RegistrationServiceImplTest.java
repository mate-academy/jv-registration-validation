package core.basesyntax.service;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static User user1;
    private static User user2;
    private static User user3;
    private static User invalidPasswordUser;
    private static User invalidAgeUser;
    private static User invalidLoginUser;
    private static User nullUser;
    private static User sameNameWithUser1;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void prepare() {
        String reallyStrongPassword = "123456789";
        user1 = new User("Michael", reallyStrongPassword, 18);
        user2 = new User("Patrick", reallyStrongPassword, 24);
        user3 = new User("Jackson", reallyStrongPassword, 30);
        invalidPasswordUser = new User("Vladislav", "12345", 26);
        invalidAgeUser = new User("Alexander", reallyStrongPassword, 16);
        invalidLoginUser = new User("Carl", reallyStrongPassword, 20);
        nullUser = null;
        sameNameWithUser1 = new User("Michael", reallyStrongPassword, 21);
    }

    @Test
    void register_validUsers_ok() {
        User actual1 = registrationService.register(user1);
        User actual2 = registrationService.register(user2);
        User actual3 = registrationService.register(user3);
        assertEquals(user1, actual1);
        assertEquals(user2, actual2);
        assertEquals(user3, actual3);
    }

    @Test
    void register_invalidPasswordUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidPasswordUser);
        });
    }

    @Test
    void register_invalidAgeUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidAgeUser);
        });
    }

    @Test
    void register_invalidLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidLoginUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_sameNameWithUser1_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameNameWithUser1);
        });
    }
}
