# Highload Software Architecture 8 Lesson 13 Homework

Queues: Redis vs Beanstalkd
---

## Test project setup

The demo is written in Kotlin/Quarkus and uses testcontainers to launch docker containers in various modes.

The [`com/example/Demo.kt/runDemo`](src/main/kotlin/com/example/Demo.kt) runs different queues with different load and writes a report in a Markdown format. The run report is located in the [REPORT.md](reports/REPORT.md) file.

### Queue configurations and load profiles

This demo applies load to 6 queue configurations (see [Docker.kt](src/main/kotlin/com/example/util/Docker.kt)):

* Redis NoP - Redis Pub-Sub queue with no persistence, `--save "" --appendonly no`
* Redis AOF - Redis Pub-Sub queue with AOF persistence, `--save "" --appendonly yes`
* Redis RDB - Redis Pub-Sub queue with RDB persistence, `--save "5 1000" --save "1 100" --appendonly no`
* Beanstalk NoP - Beanstalkd queue with no persistence, `-F`
* Beanstalk 0s - Beanstalkd queue with immediate save to file, `-f 0`
* Beanstalk 1s - Beanstalkd queue with save every second, `-f 1`

Redis is tested with Pub-Sub and Lpush/Rpop queues.

Queues are tested with different number of concurrent producers and consumers:

* 10 producers % consumers
* 50 producers & consumers
* 100 producers & consumers

Producer emits as many messages as possible during 2 minutes, then consumer consumes all messages. Resulting time is used to calculate throughput in Operations per second.

Tests also use different message sizes:

* 1-2Kb
* 10-20Kb
* 50-100Kb
* 500Kb-1Mb

All queues run with default settings, so timeouts sometimes happen on large concurrency levels and large message sizes.
These timeouts are also reported in the report as a percentage of total operations. 

To make the testing process less boring and more informative, the demo also prints outputs to console, signalling about messages being sent and received:

* `.` - message generated
* `+` - message sent to queue
* `-` - message received from queue
* `'` - message deleted (applies to Lpush/Rpop and Beanstalkd queues)

## How to run

Build and run demo application (Requires Java 17+)

```shell script
./gradlew build && \
java -jar build/quarkus-app/quarkus-run.jar
```

You can also run application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

## Final results

The data from table with final results from the [REPORT.md](reports/REPORT.md) can also be found in the Google Sheets:

[Spreadsheet with a Chart](https://docs.google.com/spreadsheets/d/1mT3EG-kFeN5FziQG_XqK9_QLeJ-c5N3Xx53N8wu4X7s)
