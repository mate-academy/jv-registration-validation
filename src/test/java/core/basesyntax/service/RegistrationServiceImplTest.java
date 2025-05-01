package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User NULL_USER = null;
    private static final Integer NULL_AGE = null;
    private static final String NULL_LOGIN = null;
    private static final String NULL_PASSWORD = null;
    private static final String VALID_LOGIN1 = "Andrew";
    private static final String VALID_LOGIN2 = "Penelopa";
    private static final String VALID_PWD = "1234567";
    private static final int VALID_AGE = 20;
    private static final String INVALID_LOGIN = "And";
    private static final String EDGE_INVALID_LOGIN = "Andre";
    private static final String EMPTY_LOGIN = "";
    private static final String INVALID_PWD = "1234";
    private static final String EDGE_INVALID_PWD = "12345";
    private static final String EMPTY_PWD = "";
    private static final int INVALID_AGE = 10;
    private static final int EDGE_INVALID_AGE = 17;
    private static final int EDGE_ZERO_INVALID_AGE = 0;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;

    private RegistrationServiceImpl regService = new RegistrationServiceImpl();
    private StorageDao storage = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_Exception_notOk() {
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(NULL_USER));
        assertEquals("User can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullPassword_Exception_notOk() {
        User nullPassworUser = new User();
        nullPassworUser.setLogin(VALID_LOGIN1);
        nullPassworUser.setPassword(NULL_PASSWORD);
        nullPassworUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(nullPassworUser));
        assertEquals("Password can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullLogin_Exception_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setLogin(NULL_LOGIN);
        nullLoginUser.setPassword(VALID_PWD);
        nullLoginUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(nullLoginUser));
        assertEquals("Login can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_nullAge_Exception_notOk() {
        User nullAgeUser = new User();
        nullAgeUser.setLogin(VALID_LOGIN1);
        nullAgeUser.setPassword(VALID_PWD);
        nullAgeUser.setAge(NULL_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(nullAgeUser));
        assertEquals("Age can't be null", invalidDataException.getMessage());
    }

    @Test
    void register_invalidLogin_Exception_notOk() {
        User invalidLoginUser = new User();
        invalidLoginUser.setLogin(INVALID_LOGIN);
        invalidLoginUser.setPassword(VALID_PWD);
        invalidLoginUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidLoginUser));
        assertEquals("Not valid login length: "
                + invalidLoginUser.getLogin().length()
                + ". Min allowed login length is " + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_invalidEdgeLogin_Exception_notOk() {
        User invalidEdgeLoginUser = new User();
        invalidEdgeLoginUser.setLogin(EDGE_INVALID_LOGIN);
        invalidEdgeLoginUser.setPassword(VALID_PWD);
        invalidEdgeLoginUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidEdgeLoginUser));
        assertEquals("Not valid login length: "
                + invalidEdgeLoginUser.getLogin().length()
                + ". Min allowed login length is " + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_emptyLogin_Exception_notOk() {
        User emptyLoginUser = new User();
        emptyLoginUser.setLogin(EMPTY_LOGIN);
        emptyLoginUser.setPassword(VALID_PWD);
        emptyLoginUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(emptyLoginUser));
        assertEquals("Not valid login length: "
                + emptyLoginUser.getLogin().length()
                + ". Min allowed login length is " + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_existUser_Exception_notOk() {
        User validUser = new User();
        validUser.setLogin(VALID_LOGIN1);
        validUser.setPassword(VALID_PWD);
        validUser.setAge(VALID_AGE);
        Storage.people.add(validUser);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(validUser));
        assertEquals("User with this login already exists", invalidDataException.getMessage());
    }

    @Test
    void register_invalidPassword_Exception_notOk() {
        User invalidPwdUser = new User();
        invalidPwdUser.setLogin(VALID_LOGIN1);
        invalidPwdUser.setPassword(INVALID_PWD);
        invalidPwdUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidPwdUser));
        assertEquals("Not valid password length: "
                + invalidPwdUser.getPassword().length()
                + ". Min allowed password length is "
                + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_invalidEdgePassword_Exception_notOk() {
        User invalidEdgePwdUser = new User();
        invalidEdgePwdUser.setLogin(VALID_LOGIN1);
        invalidEdgePwdUser.setPassword(EDGE_INVALID_PWD);
        invalidEdgePwdUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidEdgePwdUser));
        assertEquals("Not valid password length: "
                + invalidEdgePwdUser.getPassword().length()
                + ". Min allowed password length is "
                + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_emptyPassword_Exception_notOk() {
        User emptyPwdUser = new User();
        emptyPwdUser.setLogin(VALID_LOGIN1);
        emptyPwdUser.setPassword(EMPTY_PWD);
        emptyPwdUser.setAge(VALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(emptyPwdUser));
        assertEquals("Not valid password length: "
                + emptyPwdUser.getPassword().length()
                + ". Min allowed password length is "
                + MIN_LENGTH, invalidDataException.getMessage());
    }

    @Test
    void register_invalidAge_Exception_notOk() {
        User invalidAgeUser = new User();
        invalidAgeUser.setLogin(VALID_LOGIN1);
        invalidAgeUser.setPassword(VALID_PWD);
        invalidAgeUser.setAge(INVALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidAgeUser));
        assertEquals("Not valid age: "
                + invalidAgeUser.getAge()
                + ". Min allowed age is " + MIN_AGE, invalidDataException.getMessage());
    }

    @Test
    void register_invalidEdgeAge_Exception_notOk() {
        User invalidEdgeAgeUser = new User();
        invalidEdgeAgeUser.setLogin(VALID_LOGIN1);
        invalidEdgeAgeUser.setPassword(VALID_PWD);
        invalidEdgeAgeUser.setAge(EDGE_INVALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidEdgeAgeUser));
        assertEquals("Not valid age: "
                + invalidEdgeAgeUser.getAge()
                + ". Min allowed age is " + MIN_AGE, invalidDataException.getMessage());
    }

    @Test
    void register_invalidZeroAge_Exception_notOk() {
        User invalidZeroAgeUser = new User();
        invalidZeroAgeUser.setLogin(VALID_LOGIN1);
        invalidZeroAgeUser.setPassword(VALID_PWD);
        invalidZeroAgeUser.setAge(EDGE_ZERO_INVALID_AGE);
        RegistrationException invalidDataException = assertThrows(RegistrationException.class,
                () -> regService.register(invalidZeroAgeUser));
        assertEquals("Not valid age: "
                + invalidZeroAgeUser.getAge()
                + ". Min allowed age is " + MIN_AGE, invalidDataException.getMessage());
    }

    @Test
    void register_oneUser_Ok() {
        User validUser = new User();
        validUser.setLogin(VALID_LOGIN1);
        validUser.setPassword(VALID_PWD);
        validUser.setAge(VALID_AGE);
        User regValidUser = regService.register(validUser);
        assertEquals(regValidUser, storage.get(VALID_LOGIN1));
    }

    @Test
    void register_twoUsers_Ok() {
        User validUser2 = new User();
        validUser2.setLogin(VALID_LOGIN1);
        validUser2.setPassword(VALID_PWD);
        validUser2.setAge(VALID_AGE);
        User regValidUser2 = regService.register(validUser2);
        assertEquals(regValidUser2, storage.get(VALID_LOGIN1));
        User validUser3 = new User();
        validUser3.setLogin(VALID_LOGIN2);
        validUser3.setPassword(VALID_PWD);
        validUser3.setAge(VALID_AGE);
        User regValidUser3 = regService.register(validUser3);
        assertEquals(regValidUser3, storage.get(VALID_LOGIN2));
    }
}
