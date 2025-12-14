# socialClone

## Authentication

### Registration
New users can create an account by providing an email address and a password.

- The system validates the input.
- The email must be unique.
- The password is securely hashed before storage.
- A user account is created upon successful validation.

### Login
Registered users can log in using their email and password.

- Credentials are validated against stored data.
- Passwords are verified using secure hash comparison.
- On success, the user is authenticated and granted access.
- Invalid credentials are rejected.
