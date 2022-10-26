package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String WRONG_PASSWORD = "12345";
    private static final String DEFAULT_LOGIN = "Login";
    private static final int DEFAULT_AGE = 18;
    private static final int WRONG_NEGATIVE_AGE = -1;
    private User user;
    private RegistrationService service;

    @BeforeEach
    void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_tooSmallPassword_NotOk() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @Test
    void register_rightUser_Ok() {
        User actual = service.register(user);
        assertTrue(actual == user);
        User getByLogin = new StorageDaoImpl().get(actual.getLogin());
        assertTrue(getByLogin == user);
    }

    @Test
    void register_ageIsTooSmall_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @Test
    void register_user_Null_NotOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(null)
        );
    }

    @Test
    void register_addSameUser_NotOk() {
        new StorageDaoImpl().add(user);
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @Test
    public void register_negativeAge_NotOk() {
        user.setAge(WRONG_NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> service.register(user));
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> service.register(user));
    }

    @Test
    void register_ageNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> service.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
