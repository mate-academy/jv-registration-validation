package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class RegistrationServiceImplTest {
    private static final String PASSWORD_TEST = "123123";
    private static final String LOGIN_TEST = "MyTestLogin";
    private static final int AGE_TEST = 34;
    private static RegistrationService service;

    @BeforeAll
    static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUserSuccessfulRegistration_Ok() {
        User validUser = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);
        assertEquals(validUser, service.register(validUser));
    }

    @Test
    void register_LoginNull_NotOk() {
        User user = new User(null, PASSWORD_TEST, AGE_TEST);
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(user);
            }
        });
    }

    @Test
    void register_PasswordNull_NotOk() {
        User user = new User(LOGIN_TEST, null, AGE_TEST);
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(user);
            }
        });
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        User user = new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST);

        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        service.register(new User(LOGIN_TEST, PASSWORD_TEST, AGE_TEST));
                    }
                });
    }

    @Test
    void register_LoginLengthLessThan6_notOk() {
        User userLoginCharZero = new User("", PASSWORD_TEST, AGE_TEST);
        User userLoginCharThree = new User("Ooo", PASSWORD_TEST, AGE_TEST);
        User userLogonCharFive = new User("Thhhh", PASSWORD_TEST, AGE_TEST);

        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userLoginCharZero);
            }
        });
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userLoginCharThree);
            }
        });
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userLogonCharFive);
            }
        });
    }

    @Test
    void register_PasswordLengthLessThan6_notOk() {
        User userPasswordCharZero = new User(LOGIN_TEST, "", AGE_TEST);
        User userPasswordCharThree = new User(LOGIN_TEST, "ooo", AGE_TEST);
        User userPasswordCharFive = new User(LOGIN_TEST, "hhhhh", AGE_TEST);

        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userPasswordCharZero);
            }
        });
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userPasswordCharThree);
            }
        });
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userPasswordCharFive);
            }
        });
    }

    @Test
    void register_AgeLessThan18_notOk() {
        User userAgeLess18 = new User(LOGIN_TEST,PASSWORD_TEST, 16);
        assertThrows(RegistrationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.register(userAgeLess18);
            }
        });
    }

    @AfterEach
    void cleanStorage() {
        Storage.PEOPLE.clear();
    }
}
