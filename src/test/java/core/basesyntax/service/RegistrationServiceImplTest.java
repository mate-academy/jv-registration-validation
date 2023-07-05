package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @BeforeAll
    static void initializeRegistrationService() {
        registrationService = new RegistrationServiceImpl();
        storage = new ArrayList<>();

    }

    @BeforeEach
    void setUp() {
        initialUser = new User();

    }

    @AfterEach
    void tearDown() {
        Storage.people.remove(initialUser);
    }

    @Test
    void register_userIsNull_notOk() {
        initialUser = null;
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        initialUser.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_loginLessThanSixSymbols_notOk() {
        assertAll("Login checking",
                () -> {
                    initialUser.setLogin("dummy");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                },
                () -> {
                    initialUser.setLogin("du");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                },
                () -> {
                    initialUser.setLogin("");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                }
        );
    }

    @Test
    void register_validUser_ok() {
        initialUser.setLogin("dummy@37");
        initialUser.setAge(18);
        initialUser.setPassword("password1234");
        int expected = Storage.people.size() + 1;
        registrationService.register(initialUser);
        assertEquals(expected, Storage.people.size());
    }

    @Test
    void register_ageLessThan18Years_notOk() {
        initialUser.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_ageIsNegativeNumber_notOk() {
        initialUser.setAge(-12);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        initialUser.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_passwordIsNull_notOk() {
        initialUser.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

    @Test
    void register_passwordLessThanSixSymbols_notOk() {
        assertAll("Password checking",
                () -> {
                    initialUser.setPassword("dummy");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                },
                () -> {
                    initialUser.setPassword("du");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                },
                () -> {
                    initialUser.setPassword("");
                    assertThrows(RegistrationException.class,
                            () -> registrationService.register(initialUser));
                }
        );
    }

    @Test
    void register_userAlreadyExistInStorage_notAddToStorageAndThrowException() {
        initialUser.setLogin("dummy@37");
        initialUser.setAge(18);
        initialUser.setPassword("password1234");
        Storage.people.add(initialUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(initialUser));
    }

}
