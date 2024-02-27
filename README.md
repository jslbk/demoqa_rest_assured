# Reqres.in API Tests

Set of Java tests using the [RestAssured](https://rest-assured.io) library for various endpoints of the [Reqres.in](https://reqres.in/) API. The tests cover registration scenarios as well as user retrieval scenarios.

---

## Registration Tests

### 1. Successful Registration
- Validates that a user can successfully register with a valid email and password.
- Checks the response **status code is 200**, user ID, and token value in not null.

### 2. Invalid Email Registration
- Verifies that registration fails with an invalid email.
- Ensures the response **status code is 400** and checks the expected error message.

### 3. Empty Email Registration
- Tests the case where registration fails with an empty email.
- Ensures the response **status code is 400** and checks the expected error message.

### 4. Empty Password Registration
- Tests the case where registration fails with an empty password.
- Ensures the response **status code is 400** and checks the expected error message.

---

## User Retrieval Tests

### 5. **Single User Not Found**
- Verifies that attempting to retrieve a non-existent user returns a **404 status code**.

### 6. **Single User Email**
- Validates that the email of a specific user can be retrieved successfully.
- Checks the response **status code is 200** and compares the retrieved email.

---

## Usage

1. Ensure you have the necessary dependencies installed.
2. Update the base URI in the test file to match your Reqres.in API endpoint.
3. Run the tests and observe the results.
