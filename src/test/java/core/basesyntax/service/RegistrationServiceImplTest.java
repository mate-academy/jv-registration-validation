package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User actualUser;

    @BeforeEach
    void setUp() {
        actualUser = new User();
        actualUser.setLogin("Bob");
        actualUser.setPassword("123456");
        actualUser.setAge(18);
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        runAssertThrows(null);
    }

    @Test
    void register_nullUserLogin_notOk() {
        actualUser.setLogin(null);
        runAssertThrows(actualUser);
    }

    @Test
    void register_emptyUserLoginString_notOk() {
        actualUser.setLogin("");
        runAssertThrows(actualUser);
    }

    @Test
    void register_whitespaceOnlyUserLoginString_notOk() {
        actualUser.setLogin(" ");
        runAssertThrows(actualUser);
        actualUser.setLogin("  \n");
        runAssertThrows(actualUser);
        actualUser.setLogin("  \t\n");
        runAssertThrows(actualUser);
    }

    @Test
    void register_sameUserAlreadyExistAndRegistered_notOk() {
        Storage.people.add(actualUser);
        runAssertThrows(actualUser);
    }

    @Test
    void register_nullPassword_notOk() {
        actualUser.setPassword(null);
        runAssertThrows(actualUser);
    }

    @Test
    void register_passwordMinLength_notOk() {
        actualUser.setPassword("0");
        runAssertThrows(actualUser);
        actualUser.setPassword("12345");
        runAssertThrows(actualUser);
        actualUser.setPassword("abcde");
        runAssertThrows(actualUser);
        actualUser.setPassword("!@#$%");
        runAssertThrows(actualUser);
    }

    @Test
    void register_minAge_notOk() {
        for (int i = -9999; i < 17; i++) {
            actualUser.setAge(i);
            runAssertThrows(actualUser);
        }
        actualUser.setAge(Integer.MIN_VALUE);
        runAssertThrows(actualUser);
    }

    @Test
    void register_maxAge_notOk() {
        for (int i = 121; i < 9999; i++) {
            actualUser.setAge(i);
            runAssertThrows(actualUser);
        }
        actualUser.setAge(Integer.MAX_VALUE);
        runAssertThrows(actualUser);
    }

    @Test
    void register_everything_isOk() {
        assertDoesNotThrow(() -> registrationService.register(actualUser));
    }

    private void runAssertThrows(User actualUser) {
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }
}
