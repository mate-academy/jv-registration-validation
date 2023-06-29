package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.utility.RegistrationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static List<User> storage;
    private static User initialUser;
    private static User properToRegistUser;

    @BeforeAll
    static void initializeRegistrationService() {
        registrationService = new RegistrationServiceImpl();
        storage = new ArrayList<>();
        properToRegistUser = new User();
    }

    @BeforeEach
    void setUp() {
        initialUser = new User();
        properToRegistUser.setLogin("dummy@37");
        properToRegistUser.setAge(18);
        properToRegistUser.setPassword("password1234");
    }

    @AfterEach
    void tearDown() {
        Storage.people.remove(properToRegistUser);
    }

    @Test
    void register_userIsNull_notOk() {
        initialUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        initialUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_loginLessThanSixSymbols_notOk() {
        initialUser.setLogin("dummy");
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_loginHasSixOrMoreSymbols_ok() {
        initialUser.setLogin("dummy@37");
        initialUser.setAge(18);
        initialUser.setPassword("password1234");
        User userToCheck = registrationService.register(initialUser);
        boolean expected = userToCheck.getLogin().length() > 5;
        assertTrue(expected);
    }

    @Test
    void register_loginIsEmptyString_notOk() {
        initialUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_ageLessThan18Years_notOk() {
        initialUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_ageIsNegativeNumber_notOk() {
        initialUser.setAge(-12);
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_ageIsEqualTo18YearsOrMore_ok() {
        initialUser.setLogin("dummy@37");
        initialUser.setAge(18);
        initialUser.setPassword("password1234");
        User userToCheck = registrationService.register(initialUser);
        boolean expected = userToCheck.getAge() > 17;
        assertTrue(expected);
    }

    @Test
    void register_ageIsNull_notOk() {
        initialUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_passwordIsNull_notOk() {
        initialUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_passwordLessThanSixSymbols_notOk() {
        initialUser.setPassword("dum");
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        initialUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(initialUser));
    }

    @Test
    void register_userIsAlreadyExistInStorage_notAddToStorage() {
        Storage.people.add(properToRegistUser);
        int expected = Storage.people.size();
        registrationService.register(properToRegistUser);
        assertEquals(expected, Storage.people.size());
    }

    @Test
    void register_userIsNotExistInStorage_addToStorage() {
        int expected = Storage.people.size() + 1;
        registrationService.register(properToRegistUser);
        assertEquals(expected, Storage.people.size());
    }
}
