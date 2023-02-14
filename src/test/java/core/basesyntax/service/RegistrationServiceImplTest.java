package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.model.exception.RegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service = new RegistrationServiceImpl();

    @BeforeEach
    void clearData() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User("Mitresko", "P@ssw0rd", 30);
        service.register(validUser);
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    public void register_loginExists_exceptionThrown() {
        User validUser = new User("Mitresko", "P@ssw0rd", 30);
        User invalidUserLoginExist = new User("Mitresko", "anotherP@ssw0rd", 28);
        service.register(validUser);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserLoginExist);
        });
        assertEquals("User exists: " + invalidUserLoginExist.getLogin(), exception.getMessage());
    }

    @Test
    public void register_loginNull_exceptionThrown() {
        User invalidUserNullLogin = new User(null, "P@ssw", 30);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullLogin);
        });
        assertEquals("login can't be null", exception.getMessage());
    }

    @Test
    public void register_passwordNull_exceptionThrown() {
        User invalidUserNullPass = new User("Mykola", null, 30);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullPass);
        });
        assertEquals("password can't be null", exception.getMessage());
    }

    @Test
    public void register_ageNull_exceptionThrown() {
        User invalidUserNullAge = new User("Sashko", "P@ssword", null);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserNullAge);
        });
        assertEquals("age can't be null", exception.getMessage());
    }

    @Test
    public void register_passwordShort_exceptionThrown() {
        User invalidUserShortPass = new User("Taras", "P@ssw", 30);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserShortPass);
        });
        assertEquals("password can't be shorter than 6 symbols", exception.getMessage());
    }

    @Test
    public void register_ageTooYoung_exceptionThrown() {
        User invalidUserAgeTooYoung = new User("Mitresko1", "P@ssw0rd", 17);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            service.register(invalidUserAgeTooYoung);
        });
        assertEquals("user is to young: "
                + invalidUserAgeTooYoung.getAge(), exception.getMessage());
    }

    @Test
    public void register_ageMinimalOk_ok() {
        User validUser = new User("Mitresko", "P@ssw0rd", 18);
        service.register(validUser);
        assertEquals(validUser, Storage.people.get(0));
    }
}
