Setup Instructions
--

**Fat-Jar**

The microservice nsw-customer-service has been implemented using gradle wrapper. Hence, to build it, simply run the following command
in a terminal, at the root of the source folder to generate the runnable fat jar:

```
./gradlew clean build
```

This command will run unit and integration tests before packaging
the fat jar. If you wish to speed up the build and discard them,
simply add this `-x test` option to the above command. Also, take note that,
given the list of third party dependencies, the build process might vary from
1 to 2 mins depending on your local gradle repository and your network bandwidth.


| Note that this application should run against any jdk greater or equal to version 8. |
| --- |

Then, to run the application, simply type:

```
java -jar <SOURCE_FOLDER>/build/libs/nsw.jar
```

Alternatively, you could also use the bootRun gradle task to run the application (skipping the jar packaging altogether):

```
./gradlew bootRun
```

The endpoint to display the list of registrations is available at the following URL:

```
GET http://localhost:8080/registrations/
```

Accessing this URL should return the following list of registration as requested from instructions:

```
{"registrations":[{"plate_number":"EBF28E","registration":{"expired":false,"expiry_date":"2021-02-05T23:15:30.000Z"},"vehicule":{"type":"Wagon","make":"BMW","model":"X4 M40i","colour":"Blue","vin":"12389347324","tare_weight":1700,"gross_mass":null},"insurer":{"name":"Allianz","code":32}},{"plate_number":"CXD82F","registration":{"expired":true,"expiry_date":"2020-03-01T23:15:30.000Z"},"vehicule":{"type":"Hatch","make":"Toyota","model":"Corolla","colour":"Silver","vin":"54646546313","tare_weight":1432,"gross_mass":1500},"insurer":{"name":"AAMI","code":17}},{"plate_number":"WOP29P","registration":{"expired":false,"expiry_date":"2020-12-08T23:15:30.000Z"},"vehicule":{"type":"Sedan","make":"Mercedes","model":"X4 M40i","colour":"Blue","vin":"87676676672","tare_weight":1700,"gross_mass":null},"insurer":{"name":"GIO","code":13}},{"plate_number":"QWX55Z","registration":{"expired":false,"expiry_date":"2021-07-20T23:15:30.000Z"},"vehicule":{"type":"SUV","make":"Jaguar","model":"F pace","colour":"Green","vin":"65465466541","tare_weight":1620,"gross_mass":null},"insurer":{"name":"NRMA","code":27}}]}
```

**Docker**


Another option to run this application would consist in packaging it as container
and run it through a Docker installation. Please note this approach will require you
to download and install Docker (refer to the official online documentation for this).

When Docker has been installed on your machine, just run the following gradle task
to generate the docker image:

```
./gradlew buildDocker
```

Then, after a few minutes, if the task run successfully, you should be able to see the application docker image
created in the local docker registry with the following name: "registration/nsw-customer-service".

To run the application, just type in a terminal the following docker command:

```
docker run registration/nsw-customer-service
```


Requirement assumptions
--

Given the provided high-level requirements the following list of assumptions have been made while designing the solution:

- Even though no detailed technical requirements have been provided, the following technical points have been
considered while implementing the solution:

    - Vulnerability of the system through common XSS attacks
    - Performance and scalability of the system
    - Testability of the system from the start through BDD


Design and architecture decisions
--

The solution implemented has been designed in a microservice architecture model,
even though only one fat jar (covering both game resource and related summary details) has been produced.

This type of architecture provides to the application several benefits among, being loosely coupled
with other services, stateless, scalable and resilent to failures.

Hereafter is a shortlist of the other technical aspects characterizing the application:

**Microservice using Spring Boot**

Spring Boot being one of the most mature framework and currently leading the trend in microservice applications,
the decision has been made to use it along with Java given the wide support Spring has for this language.
However, note that Scala or Kotlin could also have been good alternatives.

**Security**

A XSS filter (encoding and stripping out malicious code from request parameter) has been added to the system
and being used to check submitted request and header values.

**Performance**

A particular emphasis was place on the performance aspect of the system through
the use of in-memory database - namely h2, caching the registrations (using guava cache).

**BDD and Testability**

The system has been coded entirely using BDD approach with spring-test and RESTAssured.
