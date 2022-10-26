package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void register_oneRegisteredUser_isReturnedSameAsPassed_ok() {
        assertEquals(actualUser, registrationService.register(actualUser));
    }

    @Test
    void register_coupleOfRegisteredUsers_areReturnedSameAsPassed_ok() {
        User validUser = new User();
        validUser.setLogin("Rick");
        validUser.setPassword("pickle");
        validUser.setAge(65);
        Storage.people.add(validUser);
        assertEquals(actualUser, registrationService.register(actualUser));
    }

    @Test
    void register_oneUserStoredCorrectly_ok() {
        Storage.people.add(actualUser);
        assertTrue(Storage.people.contains(actualUser));
        assertEquals(actualUser, Storage.people.get(0));
    }

    @Test
    void register_coupleOfUsersStoredCorrectly_ok() {
        User validUser1 = new User();
        validUser1.setLogin("Rick");
        validUser1.setPassword("pickle");
        validUser1.setAge(65);
        Storage.people.add(validUser1);
        Storage.people.add(actualUser);
        User validUser2 = new User();
        validUser1.setLogin("Rudeus");
        validUser1.setPassword("quagmire");
        Storage.people.add(validUser2);
        assertTrue(Storage.people.contains(actualUser));
        assertEquals(actualUser, Storage.people.get(1));
    }

    @Test
    void register_everything_isOk() {
        assertDoesNotThrow(() -> registrationService.register(actualUser));
    }

    private void runAssertThrows() {
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }
}
