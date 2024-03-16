package org.syrok0010;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Input file path:");
        var input = scanner.nextLine();
        System.out.println("Output file path:");
        var output = scanner.nextLine();
        var frequencyDictionary = new FrequencyMap(input);
        frequencyDictionary.writeToFile(output);
    }
}