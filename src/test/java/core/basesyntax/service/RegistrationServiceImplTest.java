package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User registeredUser0 = registrationService.register(
                new User("boroda4436", "2Gillette_Pro_glide", 30));
        User registeredUser1 = registrationService.register(new User("Rommelua", "ulemoR", 18));

        assertTrue(Storage.people.contains(registeredUser0));
        assertTrue(Storage.people.contains(registeredUser1));

        User registeredUser2 = registrationService.register(new User("sokima", "a!aa3sokim1", 100));
        User registeredUser3 = registrationService.register(new User("RommelUA", "      ", 69));
        User registeredUser4 = registrationService.register(new User(
                "            ", "123123asdasd", 19));

        assertTrue(Storage.people.contains(registeredUser2));
        assertTrue(Storage.people.contains(registeredUser3));
        assertTrue(Storage.people.contains(registeredUser4));
        assertEquals(5, Storage.people.size());
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_emptyUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User());
        });
    }

    @Test
    public void register_existingUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Kerrusha", "metallica4ever", 25));
            registrationService.register(new User("Kerrushaa", "i_love_KPOP", 24));
            registrationService.register(new User("Kerrushaa", "keyPOP4EVER", 24));
        });
    }

    @Test
    public void register_youngUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("okuzan", "bigGUY3000", 4));
            registrationService.register(new User("notOkuzan", "ImOldEnoguh17", 17));
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("okuzan3", "thirdTry3", -18));
        });
    }

    @Test
    public void register_emptyAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Orest40th3", "thirdTry3", 0));
        });
    }

    @Test
    public void register_emptyLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("", "123123asdasd", 40));
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "asd123", 23));
        });
    }

    @Test
    public void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("leksk", "123123asdasd", 80));
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Rom", "123123asdasd", 66));
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Orest40th", null, 23));
        });
    }

    @Test
    public void register_emptyPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("MENTOR_MA", "", 43));
        });
    }

    @Test
    public void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("olekskov", "12a", 18));
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("olekskov2", "c2345", 18));
        });
    }
}
