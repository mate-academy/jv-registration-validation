package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static User userBob;
    private static User userAnn;
    private static User userRoma;
    private RegistrationService registrationService;

    @BeforeAll
    static void setDataBase() {
        userBob = new User();
        userBob.setAge(34);
        userBob.setLogin("user_bob");
        userBob.setPassword("qwerty1234");
        userAnn = new User();
        userAnn.setAge(18);
        userAnn.setLogin("user_ann");
        userAnn.setPassword("12345678");
        userRoma = new User();
        userRoma.setAge(22);
        userRoma.setLogin("user_roma");
        userRoma.setPassword("bobocode");
        Storage.people.addAll(List.of(userAnn,userBob, userRoma));
    }

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    @Order(1)
    void registrationUser_validData_ok() {
        User expected1 = new User();
        expected1.setAge(32);
        expected1.setPassword("fgjhdn");
        expected1.setLogin("LoGiNoNe");
        User actual1 = registrationService.register(expected1);
        User expected2 = new User();
        expected2.setAge(28);
        expected2.setPassword("234vt7");
        expected2.setLogin("NewLogin");
        User actual2 = registrationService.register(expected2);
        User expected3 = new User();
        expected3.setAge(18);
        expected3.setPassword("adsfrt");
        expected3.setLogin("my_Login");
        User actual3 = registrationService.register(expected3);
        assertEquals(expected1, actual1, "Expected that user will be "
                + "added to the DB with unique data");
        assertEquals(expected2, actual2, "Expected that user will be "
                + "added to the DB with unique data");
        assertEquals(expected3, actual3, "Expected that user will be "
                + "added to the DB with unique data");
    }

    @Test
    @Order(2)
    void registrationUser_existingUser_notOk() {
        assertNull(registrationService.register(userAnn),
                "Expected, that existing user can't be added to the DB, must return null");
        assertNull(registrationService.register(userBob));
        assertNull(registrationService.register(userRoma)); 
    }

    @Test
    @Order(3)
    void registrationUser_loginLength6_ok() {
        User expectedFirst = new User();
        expectedFirst.setAge(23);
        expectedFirst.setLogin("123456");
        expectedFirst.setPassword("password1");
        User expectedSecond = new User();
        expectedSecond.setAge(27);
        expectedSecond.setLogin("asder54");
        expectedSecond.setPassword("password2");
        User expectedThird = new User();
        expectedThird.setAge(25);
        expectedThird.setLogin("/56mlk");
        expectedThird.setPassword("password3");
        User actualFirst = registrationService.register(expectedFirst);
        assertEquals(expectedFirst, actualFirst);
        User actualSecond = registrationService.register(expectedSecond);
        assertEquals(expectedSecond, actualSecond);
        User actualThird = registrationService.register(expectedThird);
        assertEquals(expectedThird, actualThird);
    }

    @Test
    @Order(4)
    void registrationUser_loginLength9_ok() {
        User expectedFirst = new User();
        expectedFirst.setAge(23);
        expectedFirst.setLogin("123456789");
        expectedFirst.setPassword("password1");
        User expectedSecond = new User();
        expectedSecond.setAge(27);
        expectedSecond.setLogin("asder5478");
        expectedSecond.setPassword("password2");
        User expectedThird = new User();
        expectedThird.setAge(25);
        expectedThird.setLogin("/56mlkhg5");
        expectedThird.setPassword("password3");
        User actualFirst = registrationService.register(expectedFirst);
        assertEquals(expectedFirst, actualFirst);
        User actualSecond = registrationService.register(expectedSecond);
        assertEquals(expectedSecond, actualSecond);
        User actualThird = registrationService.register(expectedThird);
        assertEquals(expectedThird, actualThird);
    }

    @Test
    @Order(5)
    void registrationUser_loginLength5_notOk() {
        User userWithInvalidLogin1 = new User();
        userWithInvalidLogin1.setAge(43);
        userWithInvalidLogin1.setLogin("12345");
        userWithInvalidLogin1.setPassword("password");
        User userWithInvalidLogin2 = new User();
        userWithInvalidLogin2.setAge(21);
        userWithInvalidLogin2.setLogin("asdfr");
        userWithInvalidLogin2.setPassword("password");
        User userWithInvalidLogin3 = new User();
        userWithInvalidLogin3.setAge(19);
        userWithInvalidLogin3.setLogin("1ty45");
        userWithInvalidLogin3.setPassword("password");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin2));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin3));

    }

    @Test
    @Order(6)
    void registrationUser_loginLength3_notOk() {
        User userWithInvalidLogin1 = new User();
        userWithInvalidLogin1.setAge(28);
        userWithInvalidLogin1.setLogin("123");
        userWithInvalidLogin1.setPassword("password");
        User userWithInvalidLogin2 = new User();
        userWithInvalidLogin2.setAge(33);
        userWithInvalidLogin2.setLogin("asd");
        userWithInvalidLogin2.setPassword("password");
        User userWithInvalidLogin3 = new User();
        userWithInvalidLogin3.setAge(45);
        userWithInvalidLogin3.setLogin("1t5");
        userWithInvalidLogin3.setPassword("password");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin2));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin3));

    }
}