package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Nested
    class StorageMethodsNegativeScenarioTests {
        @Test
        void storageGet_StorageIsEmpty_Null() {
            String anotherLogin = "jhonSnow";
            User actual = storageDao.get(anotherLogin);
            assertNull(actual);
        }

        @Test
        void storageAdd_UserNotFound_Null() {
            String validLogin = "misterBean";
            String validPassword = "mistafsaf";
            int validAge = 23;
            String anotherLogin = "jhonSnow";
            User testUser = new User(validLogin, validPassword, validAge);
            storageDao.add(testUser);
            User actual = storageDao.get(anotherLogin);
            assertNull(actual);
        }
    }

    @Nested
    class RegisterMethodPositiveScenarioTests {
        @Test
        void register_UserIsRegistered_True() {
            String validLogin = "misterHolms";
            String validPassword = "misterWatson";
            int validAge = 23;
            User testUser = new User(validLogin, validPassword, validAge);
            registrationService.register(testUser);
            User actual = storageDao.get(testUser.getLogin());
            assertEquals(testUser, actual);
        }

        @Test
        void register_ValidUser_NotThrow() {
            String validLogin = "misterBean";
            String validPassword = "misterBean";
            int validAge = 23;
            User testUser = new User(validLogin,
                    validPassword, validAge);

            assertDoesNotThrow(() -> registrationService.register(testUser));
        }

        @Test
        void register_UserAgeIs18_NotThrow() {
            String validLogin = "misterBean";
            String validPassword = "misterBean";
            int validAge = 18;
            User testUser = new User(validLogin,
                    validPassword, validAge);

            assertDoesNotThrow(() -> registrationService.register(testUser));
        }

        @Test
        void register_UserIsTooOld_NotThrow() {
            String validLogin = "misterBean";
            String validPassword = "misterBean";
            int validAge = 121;
            User testUser = new User(validLogin, validPassword, validAge);
            assertDoesNotThrow(() -> registrationService.register(testUser));
        }
    }

    @Nested
    class RegisterMethodNegativeScenarioTests {
        @Test
        void register_UserIsNotRegistered_Throw() {
            String validPassword = "misterBean";
            int validAge = 23;
            String invalidLogin = "Bean";
            User testUser = new User(invalidLogin, validPassword, validAge);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.register(testUser));
        }

        @Nested
        class UserFieldsTest {
            @Test
            void register_UserIsNull_Throw() {
                assertThrows(InvalidUserDataException.class,
                        () -> registrationService.register(null));
            }

            @Test
            void register_UserFieldsIsEmpty_Throw() {
                User testUser = new User("",
                        "", 0);

                assertThrows(InvalidUserDataException.class,
                        () -> registrationService.register(testUser));
            }

            @Nested
            class UserLoginFieldTests {
                @Test
                void register_UserWithShortLogin_Throw() {
                    String validPassword = "misterBean";
                    int validAge = 23;
                    String invalidLogin = "Bean";
                    User testUser = new User(invalidLogin,
                            validPassword, validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserLoginIsNull_Throw() {
                    String validPassword = "misterBean";
                    int validAge = 23;
                    User testUser = new User(null,
                            validPassword, validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserLoginIsEmpty_Throw() {
                    String validPassword = "misterBean";
                    int validAge = 23;
                    User testUser = new User("",
                            validPassword, validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }
            }

            @Nested
            class UserPasswordFieldTests {
                @Test
                void register_UserWithShortPassword_Throw() {
                    String validLogin = "misterBean";
                    int validAge = 23;
                    String invalidPassword = "Bean";
                    User testUser = new User(validLogin,
                            invalidPassword, validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserPasswordIsNull_Throw() {
                    String validLogin = "misterBean";
                    int validAge = 23;
                    User testUser = new User(validLogin,
                            null, validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserPasswordIsEmpty_Throw() {
                    String validLogin = "misterBean";
                    int validAge = 23;
                    User testUser = new User(validLogin,
                            "", validAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }
            }

            @Nested
            class UserAgeFieldTests {
                @Test
                void register_UserAgeIsZero_Throw() {
                    String validLogin = "misterBean";
                    String validPassword = "misterBean";
                    int invalidAge = 0;
                    User testUser = new User(validLogin,
                            validPassword, invalidAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserUnderAge18_Throw() {
                    String validLogin = "misterBean";
                    String validPassword = "misterBean";
                    int invalidAge = 12;
                    User testUser = new User(validLogin,
                            validPassword, invalidAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserAgeIsNegative_Throw() {
                    String validLogin = "misterBean";
                    String validPassword = "misterBean";
                    int negativeAge = -2;
                    User testUser = new User(validLogin,
                            validPassword, negativeAge);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }

                @Test
                void register_UserAgeIsNull_Throw() {
                    String validLogin = "misterBean";
                    String validPassword = "misterBean";
                    User testUser = new User(validLogin, validPassword, null);

                    assertThrows(InvalidUserDataException.class,
                            () -> registrationService.register(testUser));
                }
            }
        }
    }
}
