# User Crud application in QUARKUS , LDAP and UnboundID LDAP sdk

## Technologies used

1. Quarkus with RESTeasy and jackson
2. LDAP (389) for database
3. UnboundID LDAP sdk for interacting with ldap server

### User

| Methods | Actions           | Urls                   |
| ------- | ----------------- | ---------------------- |
| POST    | Reigster user     | /api/users/register    |
| POST    | Authenticate user | /api/users/login       |
| PUT     | Full update user  | /api/users/update/{id} |
| DELETE  | Delete a user     | /api/users/delete/{id} |

## Unit test

1. UnboundId Inmemory ldap server for testing
2. Mockito for mocking

`Total 18 unit test written with 94% coverage`
