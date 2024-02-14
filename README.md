## Config

Edit the config file (e.g. matc.conf). Do not forget to update the *jwt.password* property. For a clustered setup, you need to keep
the passwords the same. If you keep the password blank, a random password is generated at startup

```javascript
{
  "debug" : false,
  "http.port" : 8080, // The server port
  "http.host": "your.server.com", // The domain name of your server. This is important for the mail that will be send. Otherwise links will not work
  "image.folder.user" : "test/user", // folder where user images will be stored
  "image.folder.apps" : "test/apps", // folder where app images will be stored
  "image.size" : 50000000, // max image size for uploads
  "mongo.db_name": "MATC", // mongo DB to use
  "mongo.connection_string": "mongodb://localhost:27017", // connection string, might include password and username
  "mail.user": "", // POP user name for mail sending
  "mail.password" : "", // password or token
  "mail.host": "", // URL of mail server
  "mail.port": 587, //  optional, port of mail server, default is 587
  "mail.ssl": 'required', //  possible values 'required','optional' or 'disabled'. required is default.
  "admin": "admin@onepersonai.cn", // Internal mails will be send to this persons
  "jwt.password": "test", // JWT password
  "auth.service": "", // 'keycloak' or ''
  "auth.keycloak.server": "",
  "auth.keycloak.realm": "",
  "auth.keycloak.claim.lastname": "",
  "auth.keycloak.claim.name": "",
  "auth.keycloak.claim.id": "",
  "auth.keycloak.claim.email": "",
  "user.allowSignUp": true,
  "user.allowedDomains": "*"
}
```

You can also provide the configuration through ENV variables. The following variables are supported, and map 
to the JSON definitions.

```

    OPAI_HTTP_HOST

    OPAI_HTTP_PORT
    
    OPAI_MONGO_DB_NAME

    OPAI_MONGO_TABLE_PREFIX

    OPAI_MONGO_CONNECTION_STRING

    OPAI_MAIL_USER

    OPAI_MAIL_PASSWORD

    OPAI_MAIL_HOST
    
    OPAI_MAIL_PORT

    OPAI_MAIL_SSL
    
    OPAI_JWT_PASSWORD

    OPAI_IMAGE_FOLDER_USER

    OPAI_IMAGE_FOLDER_APPS
    
    OPAI_AUTH_SERVICE

    OPAI_KEYCLOAK_REALM

    OPAI_KEYCLOAK_SERVER

    OPAI_KEY_CLOAK_CLAIM_ROLE

    OPAI_KEY_CLOAK_ISSUER

    OPAI_KEY_CLOAK_CLAIM_ID

    OPAI_KEY_CLOAK_CLAIM_EMAIL

    OPAI_KEY_CLOAK_CLAIM_NAME

    OPAI_KEY_CLOAK_CLAIM_LASTNAME
    
    OPAI_USER_ALLOW_SIGNUP
    
    OPAI_USER_ALLOWED_DOMAINS

```

Please note that we have replaced the old config of nested objects with a straight dot notation. 

### Private deployments

You can limit the domains from which user can sign up by setting the 'user.allowedDomains' 
parameter in the config file, or by setting the ENV variable 'OPAI_USER_ALLOWED_DOMAINS'. The value
must be a comma separated list of domains, e.g. 'my-server.com,your-host.com'. Please be aware of <SPACES>. Sub-domains
will be allowed.


## Mongo optimization

Start the `mongo` shell and run the following commands to set the correct mongo indexes
```

use MATC
db.app.createIndex({"isPublic":1})
db.app.createIndex({"isDirty":1})

db.event.createIndex({"appID":1, "type":1})
db.event.createIndex({"appID":1})

db.mouse.createIndex({"appID":1})
db.team.createIndex({"userID": 1})
db.team.createIndex({"appID":1})
db.image.createIndex({"appID":1})
db.team.createIndex({"userID": 1, "appID":1,"permission":1 })
db.content.createIndex({key:1})
db.appevent.createIndex({"created":1})

db.invitation.createIndex({"hash": 1})
db.invitation.createIndex({"appID":1})
db.commandstack.createIndex({"appID":1})
db.comment.createIndex({"appID":1})
db.testsetting.createIndex({"appID":1})
db.user.createIndex({"email":1})
db.user.createIndex({"id":1})

```

## Development

You might need a mongo server. The simplest way is to use Docker.

```
docker run -p 27017:27017 --name opaimongo2 -d mongo:4.4   

```

## Start server

java -jar server-3.20.0-fat.jar -conf matc.conf -instances 4

## Dev Setup

In InteliJ create a new runner with the following parameters:

- *Main Class*: io.vertx.core.Starter

- *Program Arguments*: run com.opai.MATC -conf matc.conf



## Connection with KeyCloak

```
docker run -p 8081:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -v $(pwd)/test/keycloak:/tmp --name opai-keycloak jboss/keycloak 
```

```
docker run -p 8081:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e KEYCLOAK_IMPORT=/tmp/example-realm.json -v  $(pwd)/test/keycloak/example-realm.json:/tmp/example-realm.json --name opai-keycloak  jboss/keycloak
```

