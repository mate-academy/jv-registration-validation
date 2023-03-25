package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static List<User> userList;
    private static User nullUser;
    private static User userWithInvalidAge;
    private static User userWithValidData;
    private static User userWithValidDateSameLogin;
    private static User userWithInvalidPassword;
    private static User userWithValidData1;
    private static User userWithValidData2;
    private static User userWithValidDateSameLogin2;
    private static User userWithNullAge;
    private static User userWithNullLogin;
    private static User userWithNullPassword;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        userList = Storage.people;
    }

    @BeforeAll
    static void beforeAll() {
        userList = Storage.people;
    }

    @Test
    void register_invalidAge_NotOk() {
        userWithInvalidAge = new User();
        userWithInvalidAge.setPassword("1234556");
        userWithInvalidAge.setLogin("user1");
        userWithInvalidAge.setAge(10);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void register1_validData_Ok() {
        userWithValidData = new User();
        userWithValidData.setPassword("12345678");
        userWithValidData.setLogin("user");
        userWithValidData.setAge(21);

        userWithValidData1 = new User();
        userWithValidData1.setLogin("user8");
        userWithValidData1.setPassword("1235874589");
        userWithValidData1.setAge(29);

        userWithValidData2 = new User();
        userWithValidData2.setLogin("user6");
        userWithValidData2.setPassword("587884894986");
        userWithValidData2.setAge(31);

        User user = registrationService.register(userWithValidData);
        assertEquals(userWithValidData, user);
        assertEquals(1, userList.size());

        User user1 = registrationService.register(userWithValidData1);
        assertEquals(userWithValidData1, user1);
        assertEquals(2, userList.size());

        User user2 = registrationService.register(userWithValidData2);
        assertEquals(userWithValidData2, user2);
        assertEquals(3, userList.size());
    }

    @Test
    void register2_sameLogin_NotOk() {
        userWithValidDateSameLogin = new User();
        userWithValidDateSameLogin.setLogin("user");
        userWithValidDateSameLogin.setPassword("123456958");
        userWithValidDateSameLogin.setAge(25);

        userWithValidDateSameLogin2 = new User();
        userWithValidDateSameLogin2.setLogin("user8");
        userWithValidDateSameLogin2.setPassword("87456985");
        userWithValidDateSameLogin2.setAge(42);

        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithValidDateSameLogin));
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithValidDateSameLogin2));
    }

    @Test
    void register_invalidPassword_NotOk() {
        userWithInvalidPassword = new User();
        userWithInvalidPassword.setLogin("user4");
        userWithInvalidPassword.setPassword("123");
        userWithInvalidPassword.setAge(23);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_nullAge_notOk() {
        userWithNullAge = new User();
        userWithNullAge.setLogin("user7");
        userWithNullAge.setPassword("123456879");
        userWithNullAge.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullAge));
    }

    @Test
    void register_nullLogin_NotOk() {
        userWithNullLogin = new User();
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword("12387456879");
        userWithNullLogin.setAge(31);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        userWithNullPassword = new User();
        userWithNullPassword.setLogin("user9");
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void register_nullUser_notOk() {
        nullUser = null;
        assertThrows(RegistrationException.class, () ->
                registrationService.register(nullUser));
    }
}
