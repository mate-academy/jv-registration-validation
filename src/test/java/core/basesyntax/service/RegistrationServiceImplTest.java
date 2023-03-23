package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceImplTest {
    private static List<User> userList;
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

        userWithInvalidAge = new User();
        userWithInvalidAge.setPassword("1234556");
        userWithInvalidAge.setLogin("user1");
        userWithInvalidAge.setAge(10);

        userWithValidData = new User();
        userWithValidData.setPassword("12345678");
        userWithValidData.setLogin("user");
        userWithValidData.setAge(21);

        userWithValidDateSameLogin = new User();
        userWithValidDateSameLogin.setLogin("user");
        userWithValidDateSameLogin.setPassword("123456958");
        userWithValidDateSameLogin.setAge(25);

        userWithInvalidPassword = new User();
        userWithInvalidPassword.setLogin("user4");
        userWithInvalidPassword.setPassword("123");
        userWithInvalidPassword.setAge(23);

        userWithValidData1 = new User();
        userWithValidData1.setLogin("user8");
        userWithValidData1.setPassword("1235874589");
        userWithValidData1.setAge(29);

        userWithValidData2 = new User();
        userWithValidData2.setLogin("user6");
        userWithValidData2.setPassword("587884894986");
        userWithValidData2.setAge(31);

        userWithValidDateSameLogin2 = new User();
        userWithValidDateSameLogin2.setLogin("user8");
        userWithValidDateSameLogin2.setPassword("87456985");
        userWithValidDateSameLogin2.setAge(42);

        userWithNullAge = new User();
        userWithNullAge.setLogin("user7");
        userWithNullAge.setPassword("123456879");
        userWithNullAge.setAge(null);

        userWithNullLogin = new User();
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword("12387456879");
        userWithNullLogin.setAge(31);

        userWithNullPassword = new User();
        userWithNullPassword.setLogin("user9");
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(28);

    }

    @Test
    @Order(1)
    void register_invalidAge_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    @Order(2)
    void register_validData_Ok() {
        User user = registrationService.register(userWithValidData);
        assertEquals(userWithValidData, user);
        assertEquals(1, userList.size());
    }

    @Test
    @Order(3)
    void register_sameLogin_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithValidDateSameLogin));
    }

    @Test
    @Order(4)
    void register_invalidPassword_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithInvalidPassword));
    }

    @Test
    @Order(5)
    void register_validData1_Ok() {
        User user = registrationService.register(userWithValidData1);
        assertEquals(userWithValidData1, user);
        assertEquals(2, userList.size());
    }

    @Test
    @Order(6)
    void register_validData2_Ok() {
        User user = registrationService.register(userWithValidData2);
        assertEquals(userWithValidData2, user);
        assertEquals(3, userList.size());
    }

    @Test
    @Order(7)
    void register_sameLogin2_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithValidDateSameLogin2));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullAge));
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userWithNullPassword));
    }
}
