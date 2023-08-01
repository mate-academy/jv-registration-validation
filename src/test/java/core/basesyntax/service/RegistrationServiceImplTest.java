package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void clearList() {
        Storage.people.clear();
    }

    @Test
    void register_loginLess_NotOk() {
        User userFirst = new User("abc","abcdfg",19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userFirst);
        });
    }

    @Test
    void register_passwordNull_NotOk() {
        User userFirst = new User("abcabcdf",null,19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userFirst);
        });
    }

    @Test
    void register_ageNull_NotOk() {
        User userFirst = new User("abcfddfg","assdfsdfsdf",null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userFirst);
        });
    }

    @Test
    void register_loginNull_NotOk() {
        User userFirst = new User(null,"dlkfgjdlfkg",19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userFirst);
        });
    }

    @Test
    void register_passwordLength_notOk() {
        User userFirst = new User("abcadbvndab","abc",19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userFirst);
        });
    }

    @Test
    void register_lesMinAge_NotOk() {
        User notValidAgeUser = new User("abcdfkl","abcdfkl",10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidAgeUser);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        User userFirst = new User("abcdfkl","abcdfkl",19);
        User userSecond = new User("abcdfkl","abcdfkl",20);
        registrationService.register(userFirst);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userSecond);
        });
    }

    @Test
    void register_user_ok() {
        User userFirst = new User("abcdfkl","abcdfkl",19);
        assertEquals(userFirst,registrationService.register(userFirst));
    }

    @Test
    void register_user_notOk() {
        User userFirst = new User("abcdfkl","abcdfkl",19);
        User userSecond = new User("abcdfkl","abcdfkl",20);
        assertNotEquals(userFirst,registrationService.register(userSecond));
    }
}
