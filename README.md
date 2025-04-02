## Dancing with a tambourine to run a Kafka Server on Windows

#### 1. **Rename**

```text 
kafka_2.13-3.9.0
``` 

directory to

```text
kafka
```

#### 2. **Change**

```text
leetcode/kafka/config/zookeeper.properties
``` 

property:

```properties
dataDir=/tmp/zookeeper
```

to

```properties
dataDir=leetcode/zookeeper-data
```

#### 3. **Change**

```text
leetcode/kafka/config/server.properties 
```

property:

```properties
log.dirs=/tmp/kafka-logs
```

to

```properties
log.dirs=leetcode/kafka-logs
```

#### 4. **Open new tab** of the Windows Terminal and insert:

```shell
cd .\kafka\
```

#### 5. **In this tab** insert:

```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

#### 6. **Open new tab** at the Windows Terminal

#### 7. **In this new tab** insert:

```shell
cd .\kafka\
```

```shell
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

#### 8. **Open new tab** at the Windows Terminal

#### 9. **In this new tab** insert:

```shell
cd .\kafka\
```

```shell
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic core-topic --from-beginning
```