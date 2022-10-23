package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User alice = new User();
    private User bob = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        alice.setLogin("Alice");
        alice.setPassword("123Alice");
        alice.setAge(18);

        bob.setLogin("Bob");
        bob.setPassword("123Bob");
        bob.setAge(22);
    }

    @Test
    void Register_age_less_than_18_notOk() {
        alice.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(alice);
        });
        bob.setAge(-1);
        assertThrows(RuntimeException.class,
                () -> {
                    registrationService.register(bob);
                });
    }

    @Test
    void Register_password_least_6_characters_notOk() {
        bob.setPassword("abcda");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void Register_nullAge_notOk() {
        bob.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void Register_the_same_login_notOk() {
        registrationService.register(alice);
        bob.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        }, "The same login! Exception!");
    }

    @Test
    void Register_user_name_Ok() {
        User actual1 = registrationService.register(alice);
        assertTrue(Storage.people.contains(actual1));
        User actual2 = registrationService.register(bob);
        assertTrue(Storage.people.contains(actual2));
    }

    @AfterEach
    void Register_clean_storage() {
        Storage.people.clear();
    }
}
