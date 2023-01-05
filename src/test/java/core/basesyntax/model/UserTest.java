package core.basesyntax.model;

import core.basesyntax.exeption.InvalidEmailException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final String DEFAULT_EMAIL = "test_email@gmail.com";
    private static User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_EMAIL);
    }

    @AfterEach
    void tearDown() {
        user.setLogin(DEFAULT_EMAIL);
    }

    @Test
    void emailWithoutDogSymbol_NotOk() {
        assertThrows(InvalidEmailException.class, () ->
                user.setLogin("testgmail.com"), "Not valid e-mail value!"
        );
    }

    @Test
    void emailWithUncorrectedMail_NotOk() {
        assertThrows(InvalidEmailException.class, () ->
                user.setLogin("test@gmaill.com"), "Not valid mail address!"
        );
    }

    @Test
    void emailWithNotSymbolOnFirstIndex_NotOk() {
        assertThrows(InvalidEmailException.class, () ->
                user.setLogin("23test@gmail.com"), "Not valid e-mail name!"
        );
    }

    @Test
    void emailChanged_Ok() {
        user.setLogin("newEmail@gmail.com");
        assertEquals(user.getLogin(), "newEmail@gmail.com");
    }
}