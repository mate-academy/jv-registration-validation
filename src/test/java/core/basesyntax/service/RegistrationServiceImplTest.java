package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.model.UserRegisterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    public static final User USER = new User();
    private static RegistrationService registration;
    private static StorageDaoImpl dao;

    static {
        USER.setLogin("Vasya12345");
        USER.setPassword("Vasya$#145");
        USER.setAge(21);
    }

    @BeforeEach
    public void init() {
        Storage.people.clear();
        registration = new RegistrationServiceImpl();
        dao = new StorageDaoImpl();

    }

    @Test
    void register_NewValidUser_Ok() {
        registration.register(USER);
        User saved = dao.get(USER.getLogin());
        assertNotNull(saved);
        assertEquals(USER, saved);
        assertEquals(USER.getPassword(), saved.getPassword());
        assertEquals(USER.getAge(), saved.getAge());
    }

    @Test
    void register_ExistedUser_Fail() {
        User user = new User();
        user.setLogin("Vasya2345");
        user.setPassword("Vasya$#145");
        user.setAge(21);
        dao.add(user);

        assertThrows(UserRegisterException.class,
                () -> registration.register(user));
    }

    @Test
    void register_NullUser_Fail() {
        assertThrows(UserRegisterException.class,
                () -> registration.register(null));
    }

    @Test
    void register_EmptyUser_Fail() {
        assertThrows(UserRegisterException.class,
                () -> registration.register(new User()));
    }

    @Test
    void register_ShortNameUser_Fail() {
        User shortNameUser = new User();
        shortNameUser.setLogin("Vasya");
        shortNameUser.setPassword("Vasya$#147");
        shortNameUser.setAge(21);
        // registration.register(shortNameUser);
        assertThrows(UserRegisterException.class,
                () -> registration.register(shortNameUser));
    }

    @Test
    void register_LoginLength6_OK() {
        User login6 = new User();
        login6.setLogin("Vasya6");
        login6.setPassword("Vasya$#147");
        login6.setAge(21);

        registration.register(login6);
        User saved = dao.get(login6.getLogin());
        assertNotNull(saved);
        assertEquals(login6, saved);
    }

    @Test
    void register_ShortPasswordUser_Fail() {
        User login = new User();
        login.setLogin("Vasya7");
        login.setPassword("Vasya");
        login.setAge(21);

        assertThrows(UserRegisterException.class,
                () -> registration.register(login));
    }

    @Test
    void register_18Age_OK() {
        User login = new User();
        login.setLogin("Vasya12");
        login.setPassword("Vasya79879");
        login.setAge(18);

        registration.register(login);
        User saved = dao.get(login.getLogin());
        assertNotNull(saved);
        assertEquals(login, saved);
    }

    @Test
    void register_NotAdultUser_Fail() {
        User login = new User();
        login.setLogin("Vasya8");
        login.setPassword("Vasya79879");
        login.setAge(17);
        assertThrows(UserRegisterException.class,
                () -> registration.register(login));
    }

    @Test
    void register_NullAge_Fail() {
        User login = new User();
        login.setLogin("Vasya9");
        login.setPassword("Vasya79879");
        login.setAge(null);
        assertThrows(UserRegisterException.class,
                () -> registration.register(login));
    }

    @Test
    void register_NegativeAge_Fail() {
        User login = new User();
        login.setLogin("Vasya10");
        login.setPassword("Vasya79879");
        login.setAge(-1000);
        assertThrows(UserRegisterException.class,
                () -> registration.register(login));
    }
}
