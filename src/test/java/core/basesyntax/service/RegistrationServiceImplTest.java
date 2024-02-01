package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import exceptions.RegistrationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
        User userFirst = new User();
        userFirst.setLogin("User_First");
        userFirst.setPassword("user1_passworD");
        userFirst.setAge(19);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(userFirst);

    }

    @Test
    void checkForNull_nullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.checkForNull(null); },
                "User can't be null! It should throw a custom exception");
    }

    @Test
    void checkForNull_notNullUser_Ok() {
        User newUser = new User();
        newUser.setLogin("Klyaksa");
        newUser.setPassword("Black-and-white");
        newUser.setAge(21);
        Assertions.assertDoesNotThrow(() -> {
            service.checkForNull(newUser); },
                "It shouldn't throw custom exception: user exists and has all fields filled in.");
    }

    @Test
    void checkForNull_nullAllFields_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.checkForNull(new User()); },
                "User's fields can't be null! It should throw a custom exception");
    }

    @Test
    void checkForNull_nullLogin_NotOk() {
        User newUser = new User();
        newUser.setPassword("password");
        newUser.setAge(20);
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.checkForNull(newUser); });
    }

    @Test
    void checkForNull_nullPassword_NotOk() {
        User newUser = new User();
        newUser.setLogin("someUser23");
        newUser.setAge(23);
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.checkForNull(newUser); });
    }

    @Test
    void checkForNull_nullAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("someUser23");
        newUser.setPassword("forUser23");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.checkForNull(newUser); });
    }

    @Test
    void isValidValue_negativeAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("Klyaksa");
        newUser.setPassword("Black-and-white");
        newUser.setAge(-21);
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); },
                "It should throw custom exception: age can't be negative");
    }

    @Test
    void isValidValue_age_Ok() {
        User newUser = new User();
        newUser.setLogin("Klyaksa");
        newUser.setPassword("Black-and-white");
        newUser.setAge(150);
        Assertions.assertDoesNotThrow(() -> {
            service.isValidValue(newUser); },
                "It shouldn't throw custom exception: age is correct.");
    }

    @Test
    void isValidValue_overAge_NotOk() {
        User newUser = new User();
        newUser.setLogin("Klyaksa");
        newUser.setPassword("Black-and-white");
        newUser.setAge(157);
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); },
                "It should throw custom exception: age is too old");
    }

    @Test
    void isValidValue_lengthLogin_Ok() {
        User newUser = new User();
        newUser.setLogin("admin4_old");
        newUser.setPassword("treret [op9");
        newUser.setAge(86);
        boolean actual = service.isValidValue(newUser);
        Assertions.assertTrue(actual);
        newUser.setLogin("admin4");
        boolean actual2 = service.isValidValue(newUser);
        Assertions.assertTrue(actual2);
        newUser.setLogin("New00Admin1For2Service3");
        boolean actual3 = service.isValidValue(newUser);
        Assertions.assertTrue(actual3);
    }

    @Test
    void isValidValue_lengthLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("admin");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
        newUser.setLogin("admi");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
        newUser.setLogin("adm");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
        newUser.setLogin("a");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
        newUser.setLogin("");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
        newUser.setLogin(" ");
        Assertions.assertThrows(RegistrationException.class, () -> {
            service.isValidValue(newUser); });
    }

    @Test
    void checkCorrectLogin_notCyrillicLogin_Ok() {
        User newUser = new User();
        newUser.setLogin("ivanov_oleg");
        boolean actual = service.checkCorrectLogin(newUser.getLogin());
        Assertions.assertTrue(actual);
    }

    @Test
    void checkCorrectLogin_cyrillicLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("іванов_олег");
        boolean actual2 = service.checkCorrectLogin(newUser.getLogin());
        Assertions.assertFalse(actual2);
    }

    @Test
    void checkCorrectLogin_hasWhitespace_NotOk() {
        User newUser = new User();
        newUser.setLogin("admin4 old");
        boolean actual = service.checkCorrectLogin(newUser.getLogin());
        Assertions.assertFalse(actual);
    }

    @Test
    void checkCorrectLogin_hasExtraSymbol_NotOk() {
        User newUser = new User();
        newUser.setLogin("+ad!min%4_o:ld&");
        boolean actual = service.checkCorrectLogin(newUser.getLogin());
        Assertions.assertFalse(actual);
    }

    @Test
    void register_newUser_Ok() {
        User newUser = new User();
        newUser.setLogin("NewUser");
        newUser.setPassword("newunique_User");
        newUser.setAge(18);
        User actual = service.register(newUser);
        Assertions.assertEquals(newUser, actual);
    }

    @Test
    void register_newUserHasExistedLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("User_First");
        newUser.setPassword("some-password");
        newUser.setAge(18);
        User actual = service.register(newUser);
        Assertions.assertNull(actual);
    }
}
