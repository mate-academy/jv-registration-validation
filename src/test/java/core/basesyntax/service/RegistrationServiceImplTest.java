package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static final User invalidLoginUser = new User("Jack", "Qwerty123", 19);
    private static final User invalidPasswordUser = new User("Sviatoslav", "123", 19);
    private static final User invalidAgeUser = new User("Andrey", "Qwerty321", 17);
    private static final User nullValueUser = new User("null", "null", 1);
    private static final User validUser1 = new User("Arcturus", "qwerty654", 18);
    private static final User validUser2 = new User("Aleksey", "Qwerty", 57);
    private static final User validUser3 = new User("Oppenheimer", "Qwerty", 25);
    private static final User emptyUser = new User("", "", 18);

    @BeforeAll
    static void run() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void clean_storage() {
        Storage.people.clear();
    }

    @Test
    void invalid_login_test() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLoginUser));
    }

    @Test
    void null_value_test() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullValueUser));
    }

    @Test
    void invalid_password_test() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void invalid_age_test() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void add_user_test() {
        registrationService.register(validUser1);
        assertEquals(validUser1, Storage.people.get(0));
    }

    @Test
    void add_many_users_test() {
        registrationService.register(validUser1);
        registrationService.register(validUser2);
        registrationService.register(validUser3);
        assertEquals(validUser1, Storage.people.get(0));
        assertEquals(validUser2, Storage.people.get(1));
        assertEquals(validUser3, Storage.people.get(2));
    }

    @Test
    void check_size_test() {
        registrationService.register(validUser1);
        registrationService.register(validUser2);
        registrationService.register(validUser3);
        assertEquals(3, Storage.people.size(),
                "You method add() work incorrect"
                        + "Size of Storage must be " + 3
                        + " but was " + Storage.people.size());
    }

    @Test
    void add_duplicate_user_test() {
        registrationService.register(validUser1);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void add_null_test() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void add_empty_user_test() {
        assertThrows(RegistrationException.class, () -> registrationService.register(emptyUser));
    }
}
