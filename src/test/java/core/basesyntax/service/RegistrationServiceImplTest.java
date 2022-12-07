package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int STORAGE_SIZE_FOR_TEST = 2;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    public void defaultUser() {
        user = new User();
        user.setId(123L);
        user.setLogin("vasyl");
        user.setPassword("vasyl55555");
        user.setAge(24);
    }

    @Test
    void register_lowAge_notOk() {
        user.setAge(15);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void check18_years_old_Ok() {
        User actualUser = registrationService.register(user);
        assertTrue(actualUser.getAge() > MIN_AGE,"Fine");
    }

    @Test
    void userAge_is_null() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPassword_is_null() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLogin_is_null() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userId_is_null() {
        user.setId(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_is_null() {
        user = null;
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void correct_password() {
        User actualUser = registrationService.register(user);
        assertTrue(actualUser.getPassword().length() >= MIN_PASSWORD_LENGTH,"Fine");
    }

    @Test
    void password_NotOk() {
        user.setPassword("228");
        Assertions.assertThrows(RuntimeException.class,() -> registrationService.register(user));
    }

    @Test
    void already_HadUser() {
        Storage.people.add(user);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void addNewRegister_User() {
        User user2 = new User();
        user2.setId(354L);
        user2.setLogin("Roman");
        user2.setPassword("roman2281488");
        user2.setAge(31);
        Storage.people.add(user);
        Storage.people.add(user2);
        assertEquals(STORAGE_SIZE_FOR_TEST,Storage.people.size());
    }

    @AfterEach
    public void clear() {
        Storage.people.clear();
    }
}
