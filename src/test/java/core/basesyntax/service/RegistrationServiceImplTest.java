package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(62);
        user.setId(1234235446568L);
        user.setLogin("Jezza");
        user.setPassword("ham0ndDeer#MayGrandma");
    }

    @Test
    void register_nullLogin_notOk() {
        //user.setAge(62);
        //user.setId(1234235446568L);
        //user.setLogin(null);
        //user.setPassword("ham0ndDeer#MayGrandma");
        //assertNotNull(user.getLogin());
        //RegistrationService registrationService = new RegistrationServiceImpl();
        user.setLogin(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}