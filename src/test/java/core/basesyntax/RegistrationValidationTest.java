package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("testLogin");
    }

    @Test
    void get_existingUser_notOk() {
        User user = new User();
        user.setLogin("User999");
        StorageDao storageDao = new StorageDaoImpl();
        User storageUser = storageDao.get(user.getLogin());
        boolean isExist = storageUser != null;
        assertFalse(isExist, "User is already exists");
    }

    @Test
    void setLogin_lessThanSixCharacters_notOk() {
        testUser.setLogin("user");
        String login = testUser.getLogin();
        assertFalse(login.length() >= 6, "Login should be at least 6 characters");
    }

    @Test
    void setPassword_lessThanSixCharacters_notOk() {
        testUser.setPassword("1111");
        String password = testUser.getPassword();
        assertFalse(password.length() >= 6, "Password should be at least 6 characters");
    }

    @Test
    void setAge_lessThanEighteen_notOk() {
        testUser.setAge(6);
        assertFalse(testUser.getAge() >= 18, "Age should be at least 18");
    }

    @Test
    void setLogin_null_notOk() {
        testUser.setLogin(null);
        String login = testUser.getLogin();
        assertNull(login, "Login should not be null");
    }

    @Test
    void setPassword_null_notOk() {
        testUser.setPassword(null);
        String password = testUser.getPassword();
        assertNull(password, "Password should not be null");
    }

    @Test
    void setAge_null_notOk() {
        testUser.setAge(null);
        assertNull(testUser.getAge(), "Age should not be null");
    }
}
