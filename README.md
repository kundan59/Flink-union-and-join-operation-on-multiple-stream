# flink-windows-example

The project consists of 5 Java Classes:

1. CountWindows - CountWindows Class contains a method CountWindows that contains implementation of word count problem using Count window.
2. SessionWindow - SessionWindow Class contains a method SessionWindow that contains implementation of use case of finding maximum time of a particular page visited by a user within a session using Session window based on processing time.
3. SlidingWindows - SlidingWindows Class contains a method SlidingWindow that contains implementation of word count problem using Sliding window based on processing time.
4. TumblingWindows - TumblingWindows Class contains a method tumblingWindow that contains implementation of word count problem using Tumbling window based on processing time.
5. WindowExampleSelection.java- WindowExampleSelection class gives user flexibility to select a Flink window assigner application.


## Table of contents

1.  [Getting Started]  
2.  [How to Run] 
3.  [How to Use] 
    
## Getting Started
#### Minimum requirements

To run the SDK you will need **Java 1.8+, Scala 2.11,  Flink 1.11.1**.
Installation

The way to use this project is to clone it from github and build it using Maven.
## How to Run
   1. Build the Jar of the application
   2. Go to the direactory where you installed Flink then run
   ./bin/flink run /home/knoldus/Desktop/template/Flink-Wndows/target/flink-windows-1.0-SNAPSHOT.jar

   before running the application open the socekt connection required for each window application accordingly.
   nc -l 9000
   nc -l 9009
## How to Use
    Enter the option number for the the Flink window assigner application example you want to execute
    Output should be visible on the console. Then pass stream text accordingly and tail the log to see the out put.
    tail -f log/flink-*-taskexecutor-*.out

