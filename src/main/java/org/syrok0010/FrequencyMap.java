package org.syrok0010;

import java.io.*;
import java.util.HashMap;

public class FrequencyMap {

    private final HashMap<Character, Integer> _symbols;

    public FrequencyMap(String inputPath) {
        var inputFile = new File(inputPath);
        _symbols = getSymbolMapFromFile(inputFile);
    }

    public void writeToFile(String outputPath) {
        var outputFile = new File(outputPath);
        if (!tryCreateFile(outputFile)) {
            System.out.println(Strings.OUTPUT_FILE_CREATE_ERROR);
            return;
        }
        writeStats(outputFile, _symbols);
        System.out.print(Strings.ANSI_GREEN + "Created dictionary at " + outputPath + " successfully" + Strings.ANSI_WHITE);
    }

    private void writeStats(File outputFile, HashMap<Character, Integer> symbols) {
        try(var writer = new BufferedWriter(new FileWriter(outputFile))) {
            writeRange('a', 'z', symbols, writer);
            writeRange('A', 'Z', symbols, writer);
        } catch (IOException e) {
            System.out.println(Strings.OUTPUT_FILE_CREATE_ERROR);
        }
    }

    private void writeRange(char from, char to, HashMap<Character, Integer> symbols, BufferedWriter writer)
            throws IOException {
        for (var alphabet = from; alphabet <= to; alphabet++) {
            if (symbols.getOrDefault(alphabet, 0) == 0)
                continue;
            writer.write("'%s' - %d\n".formatted(alphabet, symbols.getOrDefault(alphabet, 0)));
        }
    }

    private HashMap<Character, Integer> getSymbolMapFromFile(File inputFile) {
        var symbols = new HashMap<Character, Integer>();
        try(var br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null)
                for (int i = 0; i < line.length(); i++)
                    symbols.put(line.charAt(i), symbols.getOrDefault(line.charAt(i), 0) + 1);
        } catch (IOException e) {
            System.out.println(Strings.INPUT_FILE_OPEN_ERROR);
            return symbols;
        }
        return symbols;
    }

    private boolean tryCreateFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }
}
