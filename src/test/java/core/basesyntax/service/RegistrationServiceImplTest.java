package core.basesyntax.service;


import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;


class RegistrationServiceImplTest {
    RegistrationService usersRegistration = new RegistrationServiceImpl();   // access
    private User userTest;


    @BeforeEach
    void setUp() {
    userTest = new User();
    userTest.setAge(22);
    userTest.setPassword("password");
    userTest.setLogin("login");
    userTest.setId(1234567L);
    }

    @Test
    void register_correctRegister_Ok() {
        User actual = usersRegistration.register(userTest);
        assertTrue(people.contains(actual));
    }


    @Test
    void register_unicLogin_NotOK() {
      usersRegistration.register(userTest);
      assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_passwordIsSafe_notOk() {
        userTest.setPassword("111");
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));
    }

    @Test
    void register_isAdult_NotOk() {
        userTest.setAge(17);
        assertThrows(RuntimeException.class, () -> usersRegistration.register(userTest));

    }


    @AfterEach
    void tearDown() {
        people.clear();
    }
}
