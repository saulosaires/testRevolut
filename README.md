# TestRevolut

# Requirements

Design and implement a RESTful API (including data model and the backing implementation)
for money transfers between accounts.

# Explicit requirements:

1. You can use Java, Scala or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 – keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require
a pre-installed container/server).
7. Demonstrate with tests that the API works as expected.



# Notes

1)I ignored the conversion between currencies, like dollar to euro and etc

2)For simplicity, I did not put in the designer features like branches and one user can only have one account, but in a real world this can not be true

3)I only wrote tests for the resources, just to prove that the api is working as expected

4)I decided to only use Jersey 2 just to keep as simple as possible the project 
