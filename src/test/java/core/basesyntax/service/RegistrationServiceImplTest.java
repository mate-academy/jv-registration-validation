package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
	private RegistrationService registrationService = new RegistrationServiceImpl();
	private int size;
	
	@BeforeEach
	void setUp() {
		Storage.people.add(new User(123456789L, "Bob123", "111111111", 19));
		Storage.people.add(new User(123456789L, "Alice123", "222222222", 22));
		Storage.people.add(new User(123456789L, "John123", "333333333", 25));
		size = Storage.people.size();
	}
	
	@Test
	void notContainsUserInStorage_Ok() throws ValidationException {
		User actual = registrationService.register(new User(123456789L, "Kate123", "111111111", 19));
		assertEquals(size + 1, Storage.people.size());
		assertNull(actual);
	}
	
	@Test
	void containsUserInStorage_NotOk() throws ValidationException {
		User actual = registrationService.register(new User(123456789L, "Bob123", "111111111", 19));
		assertEquals(size, Storage.people.size());
		assertNotNull(actual);
	}
	
	@Test
	void loginAtLeastSixSymbol_NotOk() throws ValidationException {
		User actual = registrationService.register(new User(555555555L, "Bob", "11", 19));
		assertEquals(size, Storage.people.size());
		assertNotNull(actual);
	}
	
	@Test
	void passwordAtLeastSixSymbol_NotOk() throws ValidationException {
		User actual = registrationService.register(new User(554654555555L, "Clark123", "11", 19));
		assertEquals(size, Storage.people.size());
		assertNotNull(actual);
	}
	
	@Test
	void ageIsAtLeast18Years_NotOk() throws ValidationException {
		User actual = registrationService.register(new User(666666L, "Ivan123", "111111111", 17));
		assertEquals(size, Storage.people.size());
		assertNotNull(actual);
	}
	
	@Test
	void idAtLeastEightSymbol_NotOk() throws ValidationException {
		User actual = registrationService.register(new User(666666L, "Luis123", "111111111", 17));
		assertEquals(size, Storage.people.size());
		assertNotNull(actual);
	}
	
	@Test
	void nullValue_NotOk() {
		assertThrows(ValidationException.class, () -> {
			registrationService.register(new User(666666L, null, "111111111", 22));
		});
	}
	
}