package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static RegistrationService rs;

    private User testUser;

    @BeforeAll
    static void beforeAll() {
        rs = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(34);
        testUser.setLogin("example1@gmail.com");
        testUser.setPassword("qwerty");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_AddToBase_Ok() {
        rs.register(testUser);
        Assertions.assertTrue(Storage.people.contains(testUser));
    }

    @Test
    void register_loginExistInBase_notOk() {
        Storage.people.add(testUser);
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_ageUnderMinAge_NotOk() {
        testUser.setAge(MIN_AGE - 1);
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_passwordUnderMinSize_NotOk() {
        testUser.setPassword("qwert");
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> rs.register(testUser));
    }

}
