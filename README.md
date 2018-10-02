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

1. Make tests work. Currently they fail with "org.axonframework.commandhandling.NoHandlerForCommandException". Tests use AggregateTestFixture.
2. Implement at least one tenant projection
3. Implement API controller for tenants
4. Implement another aggregate root (UserProfile)
5. Implement saga (coordinate creation of a tenant and 2 users). Expose saga start and progress on the API.
6. Implement custom aggregate root repository for another aggregate (no event sourcing)
7. Connect everything with postgres and activeMQ