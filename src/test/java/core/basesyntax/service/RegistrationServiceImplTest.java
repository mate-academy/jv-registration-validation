package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("Bob");
        user.setPassword("45678asdf");
        user.setAge(31);
    }

    @Test
    void register_addNullUser_NotOk() {
        User user0 = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user0),
                "Empty data");
    }

    @Test
    void register_addNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Login is null");
    }

    @Test
    void register_addNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Password is null");
    }

    @Test
    void register_addNullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Age is null");
    }

    @Test
    void register_newUserRegistration_Ok() {
        assertEquals(user, registrationService.register(user),
                "user fields was changed. Chek code!");
    }

    @Test
    void newUserAddToDataBase_Ok() {
        int size = Storage.people.size();
        registrationService.register(user);
        assertEquals(size + 1, Storage.people.size(), "data didn't add to storage");
    }

    @Test
    void register_addExistUser_NotOk() {
        Storage.people.add(user);
        User user2 = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void userAgeUnderAllowedValue_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordIsToShort_NotOk() {
        user.setPassword("45ad");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
