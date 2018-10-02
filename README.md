#### Investigation links

#### Introduction
https://www.youtube.com/watch?v=IhLSwCRyrcw

https://github.com/AxonIQ/axon-quick-start/tree/solution (checkout branch solution)

https://docs.axonframework.org/

https://docs.axonframework.org/part-i-getting-started/quick-start#quick-start

https://docs.axoniq.io/axon-cookbook/basic-recipes/simple-application-using-axon-framework-and-spring-boot


#### Blogs and GitHub code referencing AggregateTestFixture
https://blog.novatec-gmbh.de/event-sourcing-spring-boot-axon/

https://blog.novatec-gmbh.de/testing-event-sourcing-applications/

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
3. Implement saga
4. Implement custom aggregate root repository (no event sourcing)
5. Connect everything with postgres and activeMQ