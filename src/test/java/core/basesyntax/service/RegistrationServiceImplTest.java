package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User user;
    private StorageDaoImpl storageDao;

    @BeforeAll
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void createValidUser() {
        user = new User();
        user.setPassword("PASSWORD");
        user.setAge(18);
    }

    @Test
    void register_addedUserToStorage_ok() {
        User newUser = new User(user);
        newUser.setLogin("123456");
        registrationService.register(newUser);
        assertTrue(storageDao.get(newUser.getLogin()) != null);
    }

    @Test
    void register_returnsRightUser_ok() {
        User newUser = new User(user);
        newUser.setLogin("RandomLogin");

        assertTrue(registrationService.register(newUser) == newUser);
    }

    @Test
    void register_inputsHaveNulls_notOk() {
        User newUser = new User();
        assertThrows(InvalidInputException.class, () -> registrationService.register(newUser),
                "Method should throw an InvalidInputException "
                        + "if user has nulls in the input");
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User firstUser = new User(user);
        firstUser.setLogin("LOGIN1");
        registrationService.register(firstUser);
        User secondUser = new User(firstUser);
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Method should throw an InvalidInputException "
                        + "if user with such login already exist");
    }

    @Test
    void register_passwordIsShorterThen6Symbols_notOk() {
        User firstUser = new User(user);
        firstUser.setPassword("l&dg3");
        firstUser.setLogin("GGeYHD");

        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Password cannot be shorter then 6 symbols");
    }

    @Test
    void register_loginIsShorterThen6Symbols_notOk() {
        User firstUser = new User(user);
        firstUser.setLogin("46sd@");

        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Login cannot be shorter then 6 symbols");
    }

    @Test
    void register_ageIsLessThen18_notOk() {
        User firstUser = new User(user);
        firstUser.setAge(17);
        firstUser.setLogin("5316414");
        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Age has to be 18 or more");
    }
}
