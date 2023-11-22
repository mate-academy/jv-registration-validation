package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User bob;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        bob = new User();
        bob.setAge(20);
        bob.setLogin("bobiik");
        bob.setPassword("hhjfbchbv");
        bob.setId(52698L);
    }

    @Test
    void register_validUser_ok() {

        registrationService.register(bob);

        User alice = new User();
        alice.setAge(18);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf56");
        alice.setId(36898L);
        registrationService.register(alice);

        assertEquals(bob, storageDao.get(bob.getLogin()),
                "No user has been added. Please check the data");
        assertEquals(alice, storageDao.get(alice.getLogin()),
                "No user has been added. Please check the data");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullAge_notOk() {

        bob.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullLogin_notOk() {

        bob.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void register_nullPassword_notOk() {

        bob.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void register_invalidAge_notOk() {

        bob.setAge(17);

        User alice = new User();
        alice.setAge(10);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf562bv");
        alice.setId(36898L);

        User bobAgeNegative = new User();
        bobAgeNegative.setAge(-8);
        bobAgeNegative.setLogin("bobistNegative");
        bobAgeNegative.setPassword("hhjfbchbv596");
        bobAgeNegative.setId(52825L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(alice);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bobAgeNegative);
        }, "An exception should be thrown");
    }

    @Test
    void register_invalidLogin_notOk() {

        bob.setLogin("bobist");

        User john = new User();
        john.setAge(19);
        john.setLogin("bobist");
        john.setPassword("hhfjbcdyb");
        john.setId(1259L);
        registrationService.register(john);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void register_shortPassword_notOk() {

        bob.setPassword("hhjf");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        User john = new User();
        john.setAge(19);
        john.setLogin("johnidd");
        john.setPassword("jo");
        john.setId(1259L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }

    @Test
    void register_shortLogin_notOk() {

        bob.setLogin("bobik");

        User john = new User();
        john.setAge(19);
        john.setLogin("jo");
        john.setPassword("jolop5963");
        john.setId(1259L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }
}
