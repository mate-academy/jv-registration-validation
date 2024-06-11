package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();
    private static final StorageDaoImpl STORAGE_DAO = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void addUser_LoginLessThanMinLength_NotOk() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("qwerty1234");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithAllInvalidFields_NotOk() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("qwert");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithTheSameFields_NotOk() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User user1 = new User();
        user1.setLogin("Alice123");
        user1.setPassword("qwerty1234");
        user1.setAge(19);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user1);
        });
    }

    @Test
    void addUser_CheckSizeOfArrayAfterAdd_Ok() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        int sizeBefore = Storage.people.size();
        REGISTRATION_SERVICE.register(user);
        int sizeAfter = Storage.people.size();
        assertEquals(sizeAfter, sizeBefore + 1);
    }

    @Test
    void checkSize_AfterAdd10Users_Ok() {
        String login = "Alice123";
        String password = "qwerty123";
        int age = 19;
        StringBuilder passwordBuilder = new StringBuilder().append(password);
        StringBuilder loginBuilder = new StringBuilder().append(login);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setLogin(loginBuilder.append(i).toString());
            user.setPassword(passwordBuilder.append(i).toString());
            user.setAge(age + i);
            REGISTRATION_SERVICE.register(user);
        }
        int expectedSize = 10;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void addUser_WithAgeLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithLoginNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithPasswordNull_NotOk() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithAgeHigherThanMaxInteger_NotOk() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword(null);
        user.setAge(Integer.MAX_VALUE * 2);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_PasswordLessThanMinLength_NotOk() {
        User user = new User();
        user.setPassword("qwert");
        user.setLogin("Bob1256");
        user.setAge(26);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void addUser_WithAllNormalFields_Ok() {
        User user = new User();
        user.setPassword("qwerty123");
        user.setLogin("Alex1976");
        user.setAge(27);
        User returnedUser = REGISTRATION_SERVICE.register(user);
        assertEquals(user, returnedUser);
    }

    @Test
    void addUser_WithNegativeValueOfAge_NotOk() {
        User user = new User();
        user.setLogin("abcdr22");
        user.setPassword("jjjjjjj");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            REGISTRATION_SERVICE.register(user);
        });
    }

    @Test
    void getUserByLogin_Ok() {
        User user = new User();
        user.setLogin("abcdr22");
        user.setPassword("jjjjjjj");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User getUser = STORAGE_DAO.get(user.getLogin());
        assertEquals(user, getUser);
    }

    @Test
    void getUser_ByNoExistLogin_NotOk() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User getUSer = STORAGE_DAO.get("Alice1234");
        assertTrue(getUSer == null);
    }

    @Test
    void getUser_WithoutUserGetterMethod_Ok() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        REGISTRATION_SERVICE.register(user);
        User getUSer = STORAGE_DAO.get("Alice123");
        assertEquals(user, getUSer);
    }

    @Test
    void getUser_AfterAddFromStorageClass_Ok() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(19);
        Storage.people.add(user);
        User getUser = STORAGE_DAO.get(user.getLogin());
        assertEquals(user, getUser);
    }
}
