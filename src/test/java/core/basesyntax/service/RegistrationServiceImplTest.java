package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationFailureException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void setup() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void initializeValidUser() {
        user.setAge(18);
        user.setLogin("someLogin");
        user.setPassword("somePassword");
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userAgeLessThanMinValue_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userAgeNegative_NotOK() {
        user.setAge(-3214);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userPasswordIsEmptyString_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userLoginTooShort_NotOk() {
        user.setLogin("login");
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userLoginIsEmptyString_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userPasswordTooShort_NotOk() {
        user.setPassword("passw");
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userWithAllValidFields_Ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_userAlreadyExistsInStorage_NotOk() {
        registrationService.register(user);
        assertThrows(RegistrationFailureException.class, () ->
                registrationService.register(user));
    }
}