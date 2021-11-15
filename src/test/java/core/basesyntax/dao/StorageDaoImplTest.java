package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static RegistrationService registrationService;
    private static User petya;
    private static User vasya;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        petya = new User();
        vasya = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        petya.setAge(23);
        petya.setLogin("mylogin");
        petya.setPassword("SuperPassword666");
        vasya.setAge(40);
        vasya.setLogin("Vasya81");
        vasya.setPassword("Password81");
    }

    @Test
    void register_allFieldsAreValid_Ok() {
        assertTrue(petya.equals(registrationService.register(petya))
                && vasya.equals(registrationService.register(vasya)));
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        petya.setLogin("Vasya81");
        registrationService.register(petya);
        assertThrows(RuntimeException.class, () -> registrationService.register(vasya));
    }

    @Test
    void register_loginIsNull_notOk() {
        petya.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }


    @Test
    void register_loginIsShorterThan6_notOk() {
        petya.setLogin("login");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_loginNotOnlyLettersNumbers_notOk() {
        petya.setLogin("log!@#$%^&*()<>?in");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_ageIsIllegal_notOk() {
        petya.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_ageIsNull_notOk() {
        petya.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_passwordIsShorterThan6_notOk() {
        petya.setPassword("Pass1");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_passwordNotOnlyLettersDigitsCapitals_notOk() {
        petya.setPassword("+=_:;dek83F");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_passwordNoCapsitals_notOk() {
        petya.setPassword("password1");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_passwordNoNumbers_notOk() {
        petya.setPassword("PassWord");
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }

    @Test
    void register_passwordIsNull_notOk() {
        petya.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(petya));
    }
}
