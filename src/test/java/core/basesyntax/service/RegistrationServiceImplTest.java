package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("abcdef");
        user.setPassword("abcdef");
        user.setAge(18);
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userNullValue_NotOK() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userLoginNullValue_NotOK() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordNullValue_NotOK() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNullValue_NotOK() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_suchUserExists_NotOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_suchUserNotExist_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginSizeLessThanSix_notOk() {
        user.setLogin("yyy");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("yyyyy");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginSizeIsEqualToSix_Ok() {
        user.setLogin("abcdef");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginSizeIsMoreThanSix_Ok() {
        user.setLogin("abcdefgh");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordSizeLessThanSix_notOk() {
        user.setPassword("yyy");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordSIzeIsSix_Ok() {
        user.setPassword("abcdef");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordSizeMoreThanSix_Ok() {
        user.setPassword("abcdefgh");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageLessEighteen_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsEighteen_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageMoreEighteen_Ok() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
