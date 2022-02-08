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
    private static RegistrationService registrationService;
    private static Storage storage;
    private User user1;
    private User user2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
    }

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setLogin("Use1_Name");
        user1.setPassword("password1");
        user1.setAge(20);

        user2 = new User();
        user2.setLogin("User2_Name");
        user2.setPassword("password2");
        user2.setAge(21);
    }

    @AfterEach
    void tearDown() {
        storage.people.clear();
    }

    @Test
    void putNullUse_Not_Ok() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void putNullLogin_Not_Ok() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void putNonUniqueLogin_Not_Ok() {
        user2.setLogin(user1.getLogin());
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void putNullPassword_Not_Ok() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void putPasswordNotValidLength_Not_Ok() {
        user1.setPassword("test");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void putAgeNull_Not_Ok() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void putUserWithNotValidAge_Not_Ok() {
        user1.setAge(11);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
        user1.setAge(111);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void conteinsUser() {
        assertEquals(user1,registrationService.register(user1));
    }

    @Test
    void checkSize() {
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(2,storage.people.size());
    }
}
