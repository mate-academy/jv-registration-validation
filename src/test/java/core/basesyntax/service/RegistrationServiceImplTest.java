package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static final User VALID_USER;
    private static final User SAME_VALID_USER;
    private static final User NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER;
    private static final User SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER;

    static {
        VALID_USER = new User();
        VALID_USER.setLogin("anotherLogin");
        VALID_USER.setPassword("trickyPassword");
        VALID_USER.setAge(27);

        SAME_VALID_USER = new User();
        SAME_VALID_USER.setLogin("anotherLogin");
        SAME_VALID_USER.setPassword("trickyPassword");
        SAME_VALID_USER.setAge(27);

        NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER = new User();
        NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.setLogin(null);
        NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.setPassword(null);
        NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.setAge(17);

        SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER = new User();
        SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.setLogin("name");
        SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.setPassword("pass");
        SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.setAge(999);
    }

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    public void add_validUser_Ok() {
        User actualUser = service.register(VALID_USER);
        assertEquals(VALID_USER, actualUser);
    }

    @Test
    public void add_null_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    public void add_nullLoginUser_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setLogin(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.getLogin());
        nullLoginUser.setPassword(VALID_USER.getPassword());
        nullLoginUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(nullLoginUser));
    }

    @Test
    public void add_nullPasswordUser_notOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setLogin(VALID_USER.getLogin());
        nullPasswordUser.setPassword(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.getPassword());
        nullPasswordUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(nullPasswordUser));
    }

    @Test
    public void add_nullLoginAndPasswordUser_notOk() {
        User nullLoginAndPasswordUser = new User();
        nullLoginAndPasswordUser.setLogin(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.getLogin());
        nullLoginAndPasswordUser.setPassword(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.getPassword());
        nullLoginAndPasswordUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(nullLoginAndPasswordUser));
    }

    @Test
    public void add_shortLoginUser_notOk() {
        User shortLoginUser = new User();
        shortLoginUser.setLogin(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.getLogin());
        shortLoginUser.setPassword(VALID_USER.getPassword());
        shortLoginUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(shortLoginUser));
    }

    @Test
    public void add_shortPasswordUser_notOk() {
        User shortPasswordUser = new User();
        shortPasswordUser.setLogin(VALID_USER.getLogin());
        shortPasswordUser.setPassword(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.getPassword());
        shortPasswordUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(shortPasswordUser));
    }

    @Test
    public void add_shortLoginAndPasswordUser_notOk() {
        User shortLoginAndPasswordUser = new User();
        shortLoginAndPasswordUser.setLogin(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.getLogin());
        shortLoginAndPasswordUser.setPassword(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.getPassword());
        shortLoginAndPasswordUser.setAge(VALID_USER.getAge());
        assertThrows(RegistrationException.class,
                () -> service.register(shortLoginAndPasswordUser));
    }

    @Test
    public void add_underageUser_notOk() {
        User underageUser = new User();
        underageUser.setLogin(VALID_USER.getLogin());
        underageUser.setPassword(VALID_USER.getPassword());
        underageUser.setAge(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(underageUser));
    }

    @Test
    public void add_overageUser_notOk() {
        User overageUser = new User();
        overageUser.setLogin(VALID_USER.getLogin());
        overageUser.setPassword(VALID_USER.getPassword());
        overageUser.setAge(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER.getAge());
        assertThrows(RegistrationException.class, () -> service.register(overageUser));
    }

    @Test
    public void add_invalidAllFieldsUsers_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(NULL_LOGIN_AND_PASSWORD_UNDERAGE_USER));
        assertThrows(RegistrationException.class,
                () -> service.register(SHORT_LOGIN_AND_PASSWORD_OVERAGE_USER));
    }

    @Test
    public void add_sameLoginUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(SAME_VALID_USER));
    }
}
