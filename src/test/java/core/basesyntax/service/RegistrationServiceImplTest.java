package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @org.junit.jupiter.api.Test
    void register_validUser_ok() {
        User user = createUser("Yehoria", "YehoriaPasswords", 20);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(user));
    }

    @org.junit.jupiter.api.Test
    void register_existingLogin_notOk() {
        User user = createUser("Yehoria", "YehoriaPasswords", 20);
        new StorageDaoImpl().add(user);

        User another = createUser("Yehoria", "anotherPassword", 25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(another));
    }

    @org.junit.jupiter.api.Test
    void register_shortLogin_notOk() {
        User user = createUser("login", "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_loginLengthSix_ok() {
        User user = createUser("login1", "password", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_shortPassword_notOk() {
        User user = createUser("Yehoria", "pass", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_passwordLengthSix_ok() {
        User user = createUser("Yehoria", "pass12", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_ageLessThan18_notOk() {
        User user = createUser("Yehoria", "password", 17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_ageEquals18_ok() {
        User user = createUser("Yehoria", "password", 18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_negativeAge_notOk() {
        User user = createUser("Yehoria", "password", -1);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @org.junit.jupiter.api.Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @org.junit.jupiter.api.Test
    void register_nullPassword_notOk() {
        User user = createUser("Yehoria", null, 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser("Yehoria", "password", null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}