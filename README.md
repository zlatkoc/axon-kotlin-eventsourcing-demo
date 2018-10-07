#### Investigation of how to use Axon, Kotlin and Spring together

#### Introduction
https://www.youtube.com/watch?v=IhLSwCRyrcw

https://github.com/AxonIQ/axon-quick-start/tree/solution (checkout branch solution)

https://docs.axonframework.org/

https://docs.axonframework.org/part-i-getting-started/quick-start#quick-start

https://docs.axoniq.io/axon-cookbook/basic-recipes/simple-application-using-axon-framework-and-spring-boot


#### Blogs
https://blog.novatec-gmbh.de/event-sourcing-spring-boot-axon/

https://blog.novatec-gmbh.de/testing-event-sourcing-applications/

https://www.foundery.co.za/blog/a-pragmatic-design-for-an-axon-system/

https://github.com/nieldw/ContactsAxonDemo

#### GitHub code referencing AggregateTestFixture
https://github.com/search?l=Kotlin&q=AggregateTestFixture&type=Code


#### Axon framework mailing list
https://groups.google.com/forum/#!forum/axonframework


#### To be read
https://github.com/bassmake/hi-axon

https://github.com/ehnmark/axon-kotlin-om

https://www.michielrook.nl/2017/12/rebuilding-projections-axon-framework/

https://github.com/axon-microservices/user

https://github.com/DarkToast/AxonSpring


#### RabbitMQ quick quickstart
Install rabbitmq by using a package manager for your system:
```bash
$ sudo apt install rabbitmq-server
```
Configure rabbitmq - add web admin gui + user to connect:
```bash
# add web admin gui plugin
$ sudo rabbitmq-plugins enable rabbitmq-management

# configure the administrator so you can connect to web console
$ sudo rabbitmqctl add_user user user
$ sudo rabbitmqctl set_permissions -p user ".*" ".*" ".*"
$ sudo rabbitmqctl set_permissions -p / user ".*" ".*" ".*"
$ sudo rabbitmqctl set_user_tags user administrator
```

then you can connect to http://localhost:15672

#### Demo TODO

The usual Spring run works:
```bash
$ ./gradlew clean build -x test
$ java -jar build/libs/eventsourcing-0.0.1-SNAPSHOT.jar
...
spring console logging snip-snap
...
event received: TenantCreated(id=602224704, name=some tenant)
event received: TenantSuspended(id=602224704)
event received: TenantActivated(id=602224704)
```

1. `DONE` Make tests work. Currently they fail with `org.axonframework.commandhandling.NoHandlerForCommandException`. Tests use AggregateTestFixture.
1. Implement at least one tenant projection
1. `DONE` Implement API controller for tenants
1. Implement another aggregate root (UserProfile)
1. Implement KYC saga:
    1. a new user starts sign-on process
    1. user enters MSISDN
    1. confirmation code is generated
    1. SMS with confirmation code is sent
    1. user presents confirmation code
    1. a new user profile is created 
1. Implement custom aggregate root repository for another aggregate (no event sourcing)
1. Connect everything
    1. postgres
    1. `DONE` ActiveMQ
