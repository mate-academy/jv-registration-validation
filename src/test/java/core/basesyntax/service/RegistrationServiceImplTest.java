package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_loginIsNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLlengthZero_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthLessThanRequired_notOk() {
        User user = new User();
        user.setLogin("euryt");
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginExist_notOk() {
        User user = new User();
        user.setLogin("kolner");
        user.setPassword("kolnerevgen");
        user.setAge(30);
        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthIsMinPossible_Ok() {
        User user = new User();
        user.setLogin("euryth");
        user.setPassword("validPassword");
        user.setAge(23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginLengthMmoreThanMinPossible_Ok() {
        User user = new User();
        user.setLogin("fdhgsfdshfdhfdf");
        user.setPassword("validPassword");
        user.setAge(23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User user = new User();
        user.setLogin("sdgfsdgfgsd");
        user.setPassword(null);
        user.setAge(55);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordSmallLength_NotOk() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkk");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthIsMinPossible_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkk");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordLengthMoreThanMinPossible_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageIsNull_NotOk() {
        User user = new User();
        user.setLogin("sdgfsdgfgsd");
        user.setPassword("gfdgdfggd");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanRequired_notOk() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsMinPossible_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageMoreThanMinPossible_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(25);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }
}
