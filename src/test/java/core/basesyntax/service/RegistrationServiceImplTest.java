package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Ferruccio";
    private static final String DEFAULT_PASSWORD = "df89(exZ45";
    private static final int DEFAULT_AGE = 18;
    private User user;
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_ShortLogin_NotOk() {
        String shortLogin = "mario";
        user.setLogin(shortLogin);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when login is shorter than 6 characters");
    }

    @Test
    void register_ShortPassword_NotOk() {
        String shortPassword = "proXX";
        user.setPassword(shortPassword);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when password is shorter than 6 characters");
    }

    @Test
    void register_improperAge_NotOk() {
        int improperAge = 16;
        user.setAge(improperAge);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when age is less than 18 years old");
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when login is \"null\"");
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when password is \"null\"");
    }

    @Test
    void register_negativeAge_NotOk() {
        int negativeAge = -34;
        user.setAge(negativeAge);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when age is negative");
    }

    @Test
    void register_noughtAge_NotOk() {
        int noughtAge = 0;
        user.setAge(noughtAge);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when age is negative");
    }

    @Test
    void register_existingUser_NotOk() {
        registrationService.register(user);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when the user is already in storage");
    }

    @Test
    void register_emptyLogin_NotOk() {
        String emptyLogin = "";
        user.setLogin(emptyLogin);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when login is empty");
    }

    @Test
    void register_emptyPassword_NotOk() {
        String emptyPassword = "";
        user.setPassword(emptyPassword);
        try {
            registrationService.register(user);
        } catch (InvalidInputException e) {
            return;
        }
        fail("\"InvalidInputException\" must be thrown when password is empty");
    }

    @Test
    void register_singleUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;

        assertEquals(expected, actual);
    }

    @Test
    void register_pluralUsers_ok() {
        User user1 = new User();
        user1.setLogin("vendetta");
        user1.setAge(50);
        user1.setPassword("formEE9090");
        User user2 = new User();
        user2.setLogin("Leopard");
        user2.setAge(48);
        user2.setPassword("Sf87*lls");

        registrationService.register(user);
        registrationService.register(user1);
        registrationService.register(user2);

        int expectedSize = 3;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void get_user_Ok() {
        registrationService.register(user);
        User expected = user;
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }
}
