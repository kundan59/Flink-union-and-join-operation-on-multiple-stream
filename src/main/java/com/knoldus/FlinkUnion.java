package com.knoldus;

import java.util.ArrayList;

import scala.collection.JavaConverters;
import scala.collection.Seq;

import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;

/**
 * FlinkUnion Class contains a method unionStream that perform union operation on two data streams.
 */
public final class FlinkUnion {

    public final void unionStream() {
        final StreamExecutionEnvironment executionEnvironment =
                StreamExecutionEnvironment.getExecutionEnvironment();

        final DataStream<String> stream1 = executionEnvironment
                .socketTextStream("localhost", 9000, '\n', 6);

        final DataStream<String> stream2 = executionEnvironment
                .socketTextStream("localhost", 9009, '\n', 6);
        if(stream1 == null || stream2 == null) {
            System.exit(1);
            return;
        }
        ArrayList<DataStream<String>> dataStreams= new ArrayList<>();
        dataStreams.add(stream1);
        dataStreams.add(stream2);
        Seq<DataStream<String>> dataStreamSeq = JavaConverters
                .asScalaIteratorConverter(dataStreams.iterator())
                .asScala().toSeq();
        DataStream<String> union = stream1.union(dataStreamSeq);
        union.print();

        executionEnvironment.execute("Flink Session window Example");
    }
}
