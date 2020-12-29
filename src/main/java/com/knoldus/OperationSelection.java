package com.knoldus;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * WindowExampleSelection gives user flexibility to select a Flink operation on multiple streams.
 */
public final class OperationSelection {

    public static void main(String[] args) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("Press 1 for join operation");
        System.out.println("Press 2 for union operation");
        System.out.println("---------------------------------------------------");


        System.out.println("Enter a Number for which window operation want to execute");
        final Scanner myInput = new Scanner( System.in );

        switch(myInput.nextInt()) {

            case 1: {
                System.out.println("Running Tumbling window application");
                FlinkJoin flinkJoin = new FlinkJoin();
                flinkJoin.joinStream();
            }

            case 2: {
                System.out.println("Running Sliding window application");
                FlinkUnion flinkUnion = new FlinkUnion();
                flinkUnion.unionStream();
            }

            default:
                System.out.println("No such operation available for this number, please enter only " +
                        "provided numbers");
                break;
        }

    }

}
