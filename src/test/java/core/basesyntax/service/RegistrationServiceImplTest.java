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
        actualUser = null;
        runAssertThrows();
    }

    @Test
    void register_nullUserLogin_notOk() {
        actualUser.setLogin(null);
        runAssertThrows();
    }

    @Test
    void register_emptyUserLoginString_notOk() {
        actualUser.setLogin("");
        runAssertThrows();
    }

    @Test
    void register_whitespaceOnlyUserLoginString_notOk() {
        actualUser.setLogin("  \t\n");
        runAssertThrows();
    }

    @Test
    void register_sameUserAlreadyExistAndRegistered_notOk() {
        Storage.people.add(actualUser);
        runAssertThrows();
    }

    @Test
    void register_nullPassword_notOk() {
        actualUser.setPassword(null);
        runAssertThrows();
    }

    @Test
    void register_passwordMinLength_notOk() {
        actualUser.setPassword("abcde");
        runAssertThrows();
    }

    @Test
    void register_minAge_notOk() {
        actualUser.setAge(17);
        runAssertThrows();
        actualUser.setAge(Integer.MIN_VALUE);
        runAssertThrows();
    }

    @Test
    void register_maxAge_notOk() {
        actualUser.setAge(121);
        runAssertThrows();
        actualUser.setAge(Integer.MIN_VALUE);
        runAssertThrows();
    }

    @Test
    void register_everything_isOk() {
        assertDoesNotThrow(() -> registrationService.register(actualUser));
    }

    private void runAssertThrows() {
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }
}
