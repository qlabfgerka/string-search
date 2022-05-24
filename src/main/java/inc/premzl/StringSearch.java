package inc.premzl;

import inc.premzl.Models.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static inc.premzl.Files.FileOperations.readFile;
import static inc.premzl.Lists.ListOperations.findIndexes;

public class StringSearch {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) throw new Exception("Invalid arguments size");

        String fileText = readFile(args[1]);
        List<String> keywords = List.of(Arrays.copyOfRange(args, 2, args.length));
        List<Integer> indexes = new ArrayList<>();

        if (Objects.equals(args[0], "kwt")) {
            Tree tree = new Tree();
            for (String keyword : keywords) tree.addWord(keyword, tree.getChildren(), 0);

            for (int i = 0; i < fileText.length(); i++)
                tree.findIndexes(fileText.substring(i), i, tree.getChildren(), indexes);

            //tree.displayTree();

            printIndexes(indexes, keywords, fileText);
        } else if (Objects.equals(args[0], "pt")) {
            Tree tree = new Tree();
            for (int i = 0; i < fileText.length(); i++)
                tree.addWord(fileText.substring(i),
                        tree.getChildren(),
                        false,
                        null,
                        i,
                        0,
                        true);

            //tree.displayTree();

            for (String keyword : keywords) tree.findIndexes(keyword, tree.getChildren(), false, indexes);
        } else if ((Objects.equals(args[0], "testPT")) || (Objects.equals(args[0], "testKWT"))) {
            long avgTime, start, end;
            int iterations = 1000;
            int buildIterations = 1000;
            avgTime = 0;
            Tree tree = new Tree();

            if (Objects.equals(args[0], "testPT")) {
                for (int i = 0; i < buildIterations; i++) {
                    start = System.nanoTime();
                    tree = new Tree();

                    for (int j = 0; j < fileText.length(); j++)
                        tree.addWord(fileText.substring(j),
                                tree.getChildren(),
                                false,
                                null,
                                j,
                                0,
                                true);

                    end = System.nanoTime();
                    avgTime += (end - start);
                }
                System.out.println((double) TimeUnit.MILLISECONDS.convert(avgTime, TimeUnit.NANOSECONDS) / buildIterations);
            } else {
                for (String keyword : keywords) tree.addWord(keyword, tree.getChildren(), 0);
            }

            avgTime = 0;
            for (int i = 0; i < iterations; i++) {
                start = System.nanoTime();
                if (Objects.equals(args[0], "testKWT")) {
                    for (int j = 0; j < fileText.length(); j++)
                        tree.findIndexes(fileText.substring(j), j, tree.getChildren(), indexes);
                } else {
                    for (String keyword : keywords) tree.findIndexes(keyword, tree.getChildren(), false, indexes);
                }
                end = System.nanoTime();
                avgTime += (end - start);
            }
            System.out.println((double) TimeUnit.MILLISECONDS.convert(avgTime, TimeUnit.NANOSECONDS) / iterations);
        } else throw new Exception("Invalid algorithm argument");
    }

    private static void printIndexes(List<Integer> indexes, List<String> needles, String haystack) {
        System.out.print("Keyword tree indexes:\t");
        for (Integer index : indexes) System.out.print(index + " ");

        System.out.print("\nJava search indexes:\t");
        indexes = new ArrayList<>();
        for (String needle : needles) indexes.addAll(findIndexes(haystack, needle));

        Collections.sort(indexes);

        for (Integer index : indexes) System.out.print(index + " ");
    }
}
