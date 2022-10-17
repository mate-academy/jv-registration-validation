package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final User user = new User();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    public void setUser() {
        user.setAge(19);
        user.setLogin("Genareb");
        user.setPassword("asdfgh");
    }

    @Test
    public void register_nullUser_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(null),
                "RuntimeException should be thrown in user == null case");
        assertEquals("User shouldn't be a null", exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "RuntimeException should be thrown if age == null");
        assertEquals("Users age should be more than "
                + RegistrationServiceImpl.MIN_USERS_AGE + " y.o", exception.getMessage());
    }

    @Test
    void register_ageLessMinAge_notOk() {
        user.setAge(17);
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "RuntimeException should be thrown if age less then MIN_USERS_AGE");
        assertEquals("Users age should be more than "
                + RegistrationServiceImpl.MIN_USERS_AGE + " y.o", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "RuntimeException should be thrown in user == null case");
        assertEquals("Users login shouldn't be a null", exception.getMessage());
    }

    @Test
    void register_passwordLessMinChars_notOk() {
        user.setPassword("nullo");
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "RuntimeException should be thrown in user "
                        + "password less MIN_PASSWORDS_LENGTH case");
        assertEquals("Users password should have more than "
                + RegistrationServiceImpl.MIN_PASSWORDS_LENGTH
                + " characters", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "RuntimeException should be thrown in user password == null case");
        assertEquals("Users password shouldn't be a null ", exception.getMessage());
    }

    @Test
    void register_existedLogin_notOk() {
        User user2 = new User();
        user2.setAge(18);
        user2.setPassword("yeoyreoyuoe");
        user2.setLogin("Genareb");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user2);
        },
                "RuntimeException should be thrown if user with this login already exist");
        assertEquals("User with such login already exist."
                + " Users login should have unique value", exception.getMessage());
    }

    @Test
    void register_idealUser_Ok() {
        User user2 = new User();
        user2.setAge(23);
        user2.setLogin("Genareb2");
        user2.setPassword("asdfgher");
        registrationService.register(user2);
        StorageDao storageDao = new StorageDaoImpl();
        User gottenUser = storageDao.get(user2.getLogin());
        assertEquals(gottenUser,user2, "Gotten user should be equal added user");
    }
}
