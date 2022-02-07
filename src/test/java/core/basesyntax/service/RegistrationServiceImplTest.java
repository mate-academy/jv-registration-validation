package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Daniil";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static final Integer DEFAULT_AGE = 25;
    private static RegistrationService registrationService;
    private static User testedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        testedUser = new User();
    }

    @BeforeEach
    void setUp() {
        testedUser.setPassword(DEFAULT_PASSWORD);
        testedUser.setAge(DEFAULT_AGE);
        testedUser.setLogin(DEFAULT_LOGIN);
        Storage.people.clear();
    }

    @Test
    void register_allDataValid_ok() {
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_tooShortPassword_notOk() {
        testedUser.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_normalPassword_ok() {
        testedUser.setPassword("12345678");
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_ageLowerEighteen_notOk() {
        testedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullAge_notOk() {
        testedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_normalAge_ok() {
        testedUser.setAge(37);
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_ageMoreThanMaxAge_notOk() {
        testedUser.setAge(561);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_normalLogin_ok() {
        testedUser.setLogin("Alucard");
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_withExistedLogin_notOk() {
        testedUser.setLogin("admin");
        Storage.people.add(testedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }
}
