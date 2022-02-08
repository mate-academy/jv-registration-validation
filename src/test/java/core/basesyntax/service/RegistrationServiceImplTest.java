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
    private User userForTest1;
    private User userForTest2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
    }

    @BeforeEach
    void setUp() {
        userForTest1 = new User();
        userForTest1.setLogin("Use1_Name");
        userForTest1.setPassword("password1");
        userForTest1.setAge(20);

        userForTest2 = new User();
        userForTest2.setLogin("User2_Name");
        userForTest2.setPassword("password2");
        userForTest2.setAge(21);
    }

    @AfterEach
    void tearDown() {
        storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void checkLogin_nullLogin_notOk() {
        userForTest1.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void checkLogin_nonUniqueLogin_notOk() {
        userForTest2.setLogin(userForTest1.getLogin());
        storage.people.add(userForTest1);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest2));
    }

    @Test
    void checkPassword_nullPassword_notOk() {
        userForTest1.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void checkPassword_shortPassword_notOk() {
        userForTest1.setPassword("test");
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void checkAge_ageNull_notOk() {
        userForTest1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void checkAge_smallAge_notOk() {
        userForTest1.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void checkAge_bigAge_notOk() {
        userForTest1.setAge(101);
        assertThrows(RuntimeException.class, () -> registrationService.register(userForTest1));
    }

    @Test
    void register_conteinsUser_Ok() {
        assertEquals(userForTest1,registrationService.register(userForTest1));
    }

    @Test
    void size_checkSize_Ok() {
        registrationService.register(userForTest1);
        registrationService.register(userForTest2);
        assertEquals(2,storage.people.size());
    }
}
