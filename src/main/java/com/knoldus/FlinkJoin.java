package com.knoldus;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * FlinkJoin Class contains a method joinStream that perform join operation on two data streams.
 */
public final class FlinkJoin {

        public final void joinStream() throws Exception {

            final StreamExecutionEnvironment executionEnvironment =
                    StreamExecutionEnvironment.getExecutionEnvironment();

            //flink source operator 1
            final DataStream<Tuple3<Integer, String, Double>> salaryStream = executionEnvironment
                    .socketTextStream("localhost", 9000)
                    .map((MapFunction<String, Tuple3<Integer, String, Double>>) salaryTextStream -> {
                        String[] salaryFields = salaryTextStream.split(" ");
                        if (salaryFields.length == 3 &&
                                !(salaryFields[0].isEmpty()
                                        || salaryFields[1].isEmpty()
                                        || salaryFields[2].isEmpty())) {
                            return new Tuple3<>(Integer.parseInt(salaryFields[0]),
                                    salaryFields[1],
                                    Double.parseDouble(salaryFields[2]));
                        } else {
                            throw new Exception("Not valid input passed");
                        }
                    }, TypeInformation.of(new TypeHint<Tuple3<Integer, String, Double>>() {
                    }));

            //flink source operator 2
            final DataStream<Tuple2<Integer, String>> departmentStream = executionEnvironment
                    .socketTextStream("localhost", 9001)
                    .map((MapFunction<String, Tuple2<Integer, String>>) departmentTextStream -> {
                        String[] salaryFields = departmentTextStream.split(" ");
                        if (salaryFields.length == 2 &&
                                !(salaryFields[0].isEmpty()
                                        || salaryFields[1].isEmpty())) {
                            return new Tuple2<>(Integer.parseInt(salaryFields[0]), salaryFields[1]);
                        } else {
                            throw new Exception("Not valid input passed");
                        }
                    }, TypeInformation.of(new TypeHint<Tuple2<Integer, String>>() {
                    }));

            final DataStream<Tuple4<Integer, String, String, Double>> joinedStream =
                    salaryStream.join(departmentStream)
                            .where(getSalaryJoinKey -> getSalaryJoinKey.f0, TypeInformation.of(new TypeHint<Integer>() {}))
                            .equalTo((KeySelector<Tuple2<Integer, String>, Integer>) getDepartmentKey -> getDepartmentKey.f0)
                            .window(TumblingProcessingTimeWindows.of(Time.seconds(30)))
                            .apply((JoinFunction<Tuple3<Integer, String, Double>,
                                            Tuple2<Integer, String>, Tuple4<Integer, String, String, Double>>) (salaryDetail, departmentDetail) ->
                                            new Tuple4<>(salaryDetail.f0, salaryDetail.f1, departmentDetail.f1, salaryDetail.f2),
                                    TypeInformation.of(new TypeHint<Tuple4<Integer, String, String, Double>>() {}));

            //sink
            joinedStream.print();

            executionEnvironment.execute("Flink Join Example");
    }
}
