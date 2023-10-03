package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

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
                "Method should throw an InvalidInputException if user has nulls in the input");
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User firstUser = new User(user);
        firstUser.setLogin("LOGIN1");
        registrationService.register(firstUser);
        User secondUser = new User(firstUser);
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Method should throw an InvalidInputException if user with such login already exist");
    }

    @Test
    void register_loginDoesNotAlreadyExist_ok() {
        User anotherUser = new User(user);
        anotherUser.setLogin("unique");

        try {
            registrationService.register(anotherUser);
        } catch (InvalidInputException e) {
            fail("InvalidInputException should not be thrown if login is unique.");
        }
    }

    @Test
    void register_passwordIsShorterThen6Symbols_notOk() {
        User firstUser = new User(user);
        firstUser.setPassword("l&dg3");
        User secondUser = new User(user);
        secondUser.setPassword("21");
        User thirdUser = new User(user);
        thirdUser.setPassword("hk&^");

        firstUser.setLogin("GGeYHD");
        secondUser.setLogin("^RG#R%");
        thirdUser.setLogin("YRHFR^@$");

        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Password cannot be shorter then 6 symbols");
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Password cannot be shorter then 6 symbols");
        assertThrows(InvalidInputException.class, () -> registrationService.register(thirdUser),
                "Password cannot be shorter then 6 symbols");
    }

    @Test
    void register_passwordIsLongerThen6Symbols_ok() {
        User firstUser = new User(user);
        firstUser.setPassword("l&dg35");
        User secondUser = new User(user);
        secondUser.setPassword("215175723725");
        User thirdUser = new User(user);
        thirdUser.setPassword("hk&^gsr");

        firstUser.setLogin("gG6YHD");
        secondUser.setLogin("^RG23%");
        thirdUser.setLogin("YRH56R^@$");

        try {
            registrationService.register(firstUser);
            registrationService.register(secondUser);
            registrationService.register(thirdUser);
        } catch (InvalidInputException e) {
            fail("InvalidInputException should not be thrown when password is longer or equals to 6.");
        }
    }

    @Test
    void register_loginIsShorterThen6Symbols_notOk() {
        User firstUser = new User(user);
        firstUser.setLogin("46sd@");
        User secondUser = new User(user);
        secondUser.setLogin("g#");
        User thirdUser = new User(user);
        thirdUser.setLogin("h63^");

        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Login cannot be shorter then 6 symbols");
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Login cannot be shorter then 6 symbols");
        assertThrows(InvalidInputException.class, () -> registrationService.register(thirdUser),
                "Login cannot be shorter then 6 symbols");
    }

    @Test
    void register_loginIsLongerOrEquals6Symbols_ok() {
        User firstUser = new User(user);
        firstUser.setLogin("46sd@6");
        User secondUser = new User(user);
        secondUser.setLogin("g#6fur6");
        User thirdUser = new User(user);
        thirdUser.setLogin("631h7gvu!");

        try {
            registrationService.register(firstUser);
            registrationService.register(secondUser);
            registrationService.register(thirdUser);
        } catch (InvalidInputException e) {
            fail("InvalidInputException should not be thrown when login is longer or equals to 6.");
        }
    }

    @Test
    void register_ageIsLessThen18_notOk() {
        User firstUser = new User(user);
        firstUser.setAge(17);
        User secondUser = new User(user);
        secondUser.setAge(13);
        User thirdUser = new User(user);
        thirdUser.setAge(0);

        firstUser.setLogin("5316414");
        secondUser.setLogin("gg4g4422g24g");
        thirdUser.setLogin("gfdnkj4j45");

        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Age has to be 18 or more");
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Age has to be 18 or more");
        assertThrows(InvalidInputException.class, () -> registrationService.register(thirdUser),
                "Age has to be 18 or more");
    }

    @Test
    void register_ageIsMoreOrEquals18_ok() {
        User firstUser = new User(user);
        firstUser.setAge(18);
        User secondUser = new User(user);
        secondUser.setAge(50);
        User thirdUser = new User(user);
        thirdUser.setAge(21);

        firstUser.setLogin("GGeYH634D");
        secondUser.setLogin("^HBG#R%");
        thirdUser.setLogin("YRHFR^^$@");

        try {
            registrationService.register(firstUser);
            registrationService.register(secondUser);
            registrationService.register(thirdUser);
        } catch (InvalidInputException e) {
            fail("InvalidInputException should not be thrown when age is more than 18.");
        }
    }
}