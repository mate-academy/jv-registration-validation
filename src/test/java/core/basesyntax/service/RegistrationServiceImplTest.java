package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Alex", "123456", 18);
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_ageLess18_NotOk() {
        user.setAge(17);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-33);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        runtimeExceptionDetection(user);
    }

    @Test
    void register_lessThanMinLengthPassword_NotOk() {
        user.setPassword("55555");
        runtimeExceptionDetection(user);
    }

    @Test
    void register_addNullUser_NotOk() {
        runtimeExceptionDetection(null);
    }

    @Test
    void register_usersWithSameLogins_NotOk() {
        User sameLoginUser = new User(user.getLogin(), "666666", 66);
        registrationService.register(user);
        runtimeExceptionDetection(sameLoginUser);
    }

    private void runtimeExceptionDetection(User user) {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}
