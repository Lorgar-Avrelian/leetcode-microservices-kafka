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

#### 4. **Delete (if exists)** directories in `kafka` directory:

- `kafka/leetcode`;
- `kafka/logs`.

#### 5. **Open new tab** of the Windows Terminal and insert:

```shell
cd .\kafka\
```

#### 6. **In this tab** insert:

```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

#### 7. **Open new tab** at the Windows Terminal

#### 8. **In this new tab** insert:

```shell
cd .\kafka\
```

```shell
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

#### 9. **Open new tab** at the Windows Terminal

#### 10. **In this new tab** insert:

```shell
cd .\kafka\
```

```shell
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic core-topic --from-beginning
```

#### 11. **For all tasks topics**:

- **Open new tab** at the Windows Terminal;
- **In this new tab** insert:

    * move to `kafka` directory:
  ```shell
  cd .\kafka\
  ```
    * run console consumer for this task (task № 0001 in example):
  ```shell
  .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic task-0001 --from-beginning
  ```