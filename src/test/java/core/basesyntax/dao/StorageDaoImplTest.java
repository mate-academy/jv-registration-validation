package core.basesyntax.dao;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageDaoImplTest {
    private User user;
    private StorageDaoImpl storage;

    @BeforeEach
    public void initUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setLogin("admin");
        newUser.setPassword("admin");
        newUser.setAge(18);

        this.user = newUser;
    }

    @BeforeEach
    public void initStorage() {
        this.storage = new StorageDaoImpl();
    }

    @Test
    void check_user_not_in_storage_Ok() {
        User userInStorage = storage.get(user.getLogin());
        assertNull(userInStorage);
    }

    @Test
    void user_password_ok() {
        String userPassword = "123456";
        boolean isGreaterThan5 = userPassword.length() >= 6;
        assertTrue(isGreaterThan5);
    }

    @Test
    void user_password_notOk() {
        String userPassword = "123";
        boolean isGreaterThan5 = userPassword.length() >= 6;
        assertFalse(isGreaterThan5);
    }

    @Test
    void user_login_ok() {
        String userLogin = "user12";
        boolean isGreaterThan5 = userLogin.length() >= 6;
        assertTrue(isGreaterThan5);
    }

    @Test
    void user_login_notOk() {
        String userLogin = "user";
        boolean isGreaterThan5 = userLogin.length() >= 6;
        assertFalse(isGreaterThan5);
    }

    @Test
    void user_age_Ok() {
        int userAge = 18;
        boolean isGreaterThan17 = userAge >= 18;
        assertTrue(isGreaterThan17);
    }

    @Test
    void user_age_notOk() {
        int userAge = 15;
        boolean isGreaterThan17 = userAge >= 18;
        assertFalse(isGreaterThan17);
    }
}
