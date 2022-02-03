package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Daniil";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static final Integer DEFAULT_AGE = 25;
    private RegistrationService registrationService;
    private User testedUser;

    @BeforeEach
    void setUp() {
        testedUser = new User();
        testedUser.setPassword(DEFAULT_PASSWORD);
        testedUser.setAge(DEFAULT_AGE);
        testedUser.setLogin(DEFAULT_LOGIN);
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_allDataValid_Ok() {
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
    void register_normalPassword_Ok() {
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
    void register_ageLowerEighteen() {
        testedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullAge_notOk() {
        testedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_normalAge_Ok() {
        testedUser.setAge(37);
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_ageMoreThenEighteen_Ok() {
        testedUser.setAge(20);
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_ageMoreThanMaxAge_NotOk() {
        testedUser.setAge(561);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_normalLogin_Ok() {
        testedUser.setLogin("Alucard");
        registrationService.register(testedUser);
        assertTrue(Storage.people.contains(testedUser));
    }

    @Test
    void register_withExistedLogin_NotOk() {
        testedUser.setLogin("admin");
        registrationService.register(testedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        testedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }
}
