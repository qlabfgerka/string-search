package inc.premzl;

import inc.premzl.Models.Tree;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static inc.premzl.Files.FileOperations.readFile;

public class StringSearch {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) throw new Exception("Invalid arguments size");

        String fileText = readFile(args[1]);
        List<String> keywords = List.of(Arrays.copyOfRange(args, 2, args.length));

        if (Objects.equals(args[0], "kwt")) {
            Tree tree = new Tree();
            //for (String keyword : keywords)
            for (int i = 0; i < fileText.length(); i++)
                tree.addWord(fileText.substring(i), tree.getChildren(), 0);

            tree.displayTree();
        } else if (Objects.equals(args[0], "pt")) {
            Tree tree = new Tree();
            for (int i = 0; i < fileText.length(); i++)
                tree.addWordCollapse(fileText.substring(i),
                        tree.getChildren(),
                        0,
                        false,
                        null,
                        0,
                        0,
                        i,
                        true);

            tree.displayTree();
        } else throw new Exception("Invalid algorithm argument");
    }
}
