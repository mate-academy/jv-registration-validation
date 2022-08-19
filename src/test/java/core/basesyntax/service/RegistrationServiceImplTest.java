package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("ALice");
        user.setPassword("456lfkdgj");
        user.setAge(22);
    }

    @Test
    void register_emptyUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_NotOk() {
        registrationService.register(user);
        User secondUser = new User();
        secondUser.setLogin(user.getLogin());
        secondUser.setPassword("slkgnUR12");
        secondUser.setAge(20);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(secondUser));
    }

    @Test
    void register_lowAge_NotOk() {
        user.setAge(MIN_AGE - 1);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAcceptableAge_Ok() {
        user.setAge(MIN_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_moreThanMinAcceptableAge_Ok() {
        user.setAge(MIN_AGE + 1);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("pass");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAcceptablePassLength_Ok() {
        user.setPassword("passwo");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_moreThanMinAcceptablePassLength_Ok() {
        user.setPassword("passwo1");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
