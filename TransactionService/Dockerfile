FROM openjdk
WORKDIR usr/lib
ADD ./target/TransactionService-0.0.1-SNAPSHOT.jar /usr/lib/TransactionService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar" ,"TransactionService-0.0.1-SNAPSHOT.jar"]