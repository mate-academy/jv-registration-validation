package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final int minAge = 18;
    private final int minPasswordLength = 6;
    private final String login = "Ivanov";
    private final String password = "Hello_World";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User defaultUser = new User();

    @BeforeEach
    void setUp() {
        defaultUser.setAge(minAge);
        defaultUser.setLogin(login);
        defaultUser.setPassword(password);
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser),
                "User's password can not be null");
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser),
                "User's age can not be null");
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser),
                "User's login can not be null");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "User can not be null");
    }

    @Test
    void register_LessLimitAge_notOk() {
        int age = 15;
        defaultUser.setAge(age);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser),
                "User age not be less " + minAge);
    }

    @Test
    void register_LessLimitPasswordLength_notOk() {
        String notValidpassword = "hai";
        defaultUser.setPassword(notValidpassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser),
                "User's password  length less " + minPasswordLength);
    }

    @Test
    void register_AddNewUserToStorage_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        registrationService.register(defaultUser);
        assertEquals(defaultUser, storageDao.get(defaultUser.getLogin()),"User "
                + defaultUser + " don't add to storage");
    }

    @Test
    void register_AddTwoUsersSameLogin_NotOk() {
        User userOne = defaultUser;
        registrationService.register(userOne);
        User userTwo = new User();
        userTwo.setPassword("MyNameIs");
        userTwo.setAge(32);
        userTwo.setLogin(login);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTwo),
                "Can not be to same User's login be add");
    }

    @Test
    void register_AddTwentyUsers_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        int countUsers = 19;
        User[] users = new User[countUsers];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User();
            users[i].setLogin("User's_login_" + i);
            users[i].setAge(32);
            users[i].setPassword("User's_password_" + i);
        }
        for (User user: users) {
            registrationService.register(user);
            assertEquals(user, storageDao.get(user.getLogin()),"User with login "
                    + user.getLogin() + "don't add to the Storage");
        }
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
