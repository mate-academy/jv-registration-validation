package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private User actual;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        actual = new User();
    }

    @Test
    void register_validData_Ok() {
        actual.setAge(20);
        actual.setLogin("validLogin");
        actual.setPassword("validPass");
        User registered = registrationService.register(actual);
        assertEquals(actual.getLogin(), registered.getLogin());
        assertEquals(actual.getPassword(), registered.getPassword());
        assertEquals(actual.getAge(), registered.getAge());
    }

    @Test
    void register_ageUnder18_notOk() {
        actual.setAge(16);
        actual.setLogin("youngUser");
        actual.setPassword("password");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_loginAlreadyExistsInStorage_notOk() {
        actual.setAge(20);
        actual.setLogin("duplicateLogin");
        actual.setPassword("password");
        registrationService.register(actual);

        User duplicate = new User();
        duplicate.setAge(25);
        duplicate.setLogin("duplicateLogin");
        duplicate.setPassword("anotherPass");

        assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicate));
    }

    @Test
    void register_loginTooShort_notOk() {
        actual.setAge(20);
        actual.setLogin("abc");
        actual.setPassword("validPass");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_passwordTooShort_notOk() {
        actual.setAge(20);
        actual.setLogin("validLogin");
        actual.setPassword("123");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_nullFields_notOk() {
        User emptyUser = new User();
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyUser));
    }

}
