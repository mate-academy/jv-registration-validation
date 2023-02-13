package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.model.exception.RegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User validUser = new User("Mitresko", "P@ssw0rd", 30);
    private static final User invalidUserLoginExist = new User("Mitresko", "P@ssw0rd", 30);
    private static final User invalidUserAgeLess18 = new User("Mitresko1", "P@ssw0rd", 17);
    private static final User invalidUserShortPass = new User("Taras", "P@ssw", 30);
    private static final User invalidUserNullLogin = new User(null, "P@ssw", 30);
    private static final User invalidUserNullPass = new User("Mykola", null, 30);
    private static final User invalidUserNullAge = new User("Sashko", "P@ssword", null);
    private static RegistrationService service;

    @BeforeEach
    void clearData() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void addAndGet_validUser_Ok() {
        service.register(validUser);
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void add_loginExists_exceptionThrown() {
        service.register(validUser);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserLoginExist);
        });
        assertEquals("User exists: " + invalidUserLoginExist.getLogin(), exception.getMessage());
    }

    @Test
    void add_loginNull_exceptionThrown() {
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullLogin);
        });
        assertEquals("login can't be null", exception.getMessage());
    }

    @Test
    void add_passwordNull_exceptionThrown() {
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullPass);
        });
        assertEquals("password can't be null", exception.getMessage());
    }

    @Test
    void add_ageNull_exceptionThrown() {
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullAge);
        });
        assertEquals("age can't be null", exception.getMessage());
    }

    @Test
    void add_passwordShort_exceptionThrown() {
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserShortPass);
        });
        assertEquals("password can't be shorter than 6 symbols", exception.getMessage());
    }

    @Test
    void add_ageLess18_exceptionThrown() {
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserAgeLess18);
        });
        assertEquals("user is to young: " + invalidUserAgeLess18.getAge(), exception.getMessage());
    }
}
