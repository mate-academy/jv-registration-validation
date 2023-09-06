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
    void register_loginIsnull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void chec_login_length_0_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginlengthLessThanRequired_notOk() {
        User user = new User();
        user.setLogin("euryt");
        user.setPassword("validPassword");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void chec_login_exist_notOk() {
        User user = new User();
        user.setLogin("kolner");
        user.setPassword("kolnerevgen");
        user.setAge(30);
        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void chec_login_length_6_Ok() {
        User user = new User();
        user.setLogin("euryth");
        user.setPassword("validPassword");
        user.setAge(23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void chec_login_length_more_than_6_Ok() {
        User user = new User();
        user.setLogin("fdhgsfdshfdhfdf");
        user.setPassword("validPassword");
        user.setAge(23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void check_password_null_NotOk() {
        User user = new User();
        user.setLogin("sdgfsdgfgsd");
        user.setPassword(null);
        user.setAge(55);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void check_password_small_Length_NotOk() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkk");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void check_password_Length_6_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkk");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void check_password_Length_more_than_6_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void check_age_less_than_18_notOk() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void check_age_18_Ok() {
        User user = new User();
        user.setLogin("sdfsdfwgfv");
        user.setPassword("kkkkkkdfgbadagdsda");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void check_age_more_than_18_Ok() {
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
