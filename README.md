# User Crud application in QUARKUS , LDAP and UnboundID LDAP sdk

## Technologies used

1. Quarkus with RESTeasy and jackson
2. LDAP (389) for database
3. UnboundID LDAP sdk

### Users

| Methods | Actions          | Urls                   |
| ------- | ---------------- | ---------------------- |
| POST    | Reigster user    | /api/users/register    |
| POST    | Login user       | /api/users/login       |
| PUT     | Full update user | /api/users/update/{id} |
| DELETE  | Delete a user    | /api/users/delete/{id} |

## More things to implement

1. TOKEN based authentication
2. Implement DTOs
3. More Error handling
4. Optimize transactions
