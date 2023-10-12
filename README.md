# Registration Service

Your task is to implement a registration service and write unit tests for it.

Registration service has one method `register(User user)`, that accepts some user (the User.class has already been given to you).
This method should register a user (by adding it to Storage) only if the user meets the following criteria:
- there is no user with such login in the Storage yet
- user's login is at least 6 characters
- user's password is at least 6 characters
- user's age is at least 18 years old


You should create your custom unchecked exception and throw it in case of invalid data.

`Storage` and class `StorageDaoImpl` for working with Storage have already been implemented. It has two methods:
- `User add (User user)`  - which adds a user to the storage
- `User get (String login)` - which gets the user from the storage by login or returns null if there is no such user.

Your task is to write as many tests as possible to cover all possible options.

There are a lot of ways to name your test methods. The main point is that they should have informative 
names and be consistent along with other developers in your team. For this task use such convention:
`<methodUnderTest>_<state>_<expectedBehavior>`; For example, if we are testing the method `register` with a `null`  
user's age the test method name should be `register_nullAge_notOk`. notOk is because
the test expects the register method to throw an exception.

#### [Try to avoid these common mistakes, while solving task](./checklist.md)
