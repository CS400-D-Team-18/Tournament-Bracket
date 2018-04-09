import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          cs400_p3_201801
// FILES:            Graph.java
//                   GraphProcessor.java
//                   GraphTest.java
//                   WordProcessor.java
//
// USER:             ateng@wisc.edu
//                   tfiedler2@wisc.edu
//                   cdedrick@wisc.edu
//                   yfang57@wisc.edu
//                   mdespe@wisc.edu
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs, but not complete either
//
// 2018 Apr 16, 2018 WordProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
    
    /**
     * Gets a Stream of words from the filepath.
     * 
     * The Stream should only contain trimmed, non-empty and UPPERCASE words.
     * 
     * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
     * 
     * @param filepath
     *            file path to the dictionary file
     * @return Stream<String> stream of words read from the filepath
     * @throws IOException
     *             exception resulting from accessing the filepath
     */
    @SuppressWarnings("resource")
    public static Stream<String> getWordStream(String filepath) throws IOException {
        /**
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
         * 
         *      class Files has a method lines() which accepts an interface Path object and
         *      produces a Stream<String> object via which one can read all the lines from a file as a Stream.
         * 
         *      class Paths has a method get() which accepts one or more strings (filepath),
         *      joins them if required and produces a interface Path object
         * 
         *      Combining these two methods:
         *      Files.lines(Paths.get(<string filepath>))
         *      produces
         *      a Stream of lines read from the filepath
         * 
         *      Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine
         *      multiple pre-processing operations of each line in a single statement.
         * 
         *      Few of these features:
         *      1. map( ) [changes a line to the result of the applied function. Mathematically, line = operation(line)]
         *      - trim all the lines
         *      - convert all the lines to UpperCase
         *      - example takes each of the lines one by one and apply the function toString on them as line.toString()
         *      and returns the Stream:
         *      streamOfLines = streamOfLines.map(String::toString)
         * 
         *      2. filter( ) [keeps only lines which satisfy the provided condition]
         *      - can be used to only keep non-empty lines and drop empty lines
         *      - example below removes all the lines from the Stream which do not equal the string "apple"
         *      and returns the Stream:
         *      streamOfLines = streamOfLines.filter(x -> x != "apple");
         * 
         *      3. collect( ) [collects all the lines into a java.util.List object]
         *      - can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
         *      - example below collects all the elements of the Stream into a List and returns the List:
         *      List<String> listOfLines = streamOfLines.collect(Collectors::toList);
         * 
         *      Note: since map and filter return the updated Stream objects, they can chained together as:
         *      streamOfLines.map(...).filter(a -> ...).map(...) and so on
         */
        Stream<String> stream;
        stream = Files.lines(Paths.get(filepath));
        stream = stream.filter(x -> x != "").map(String::toUpperCase).map(String::trim);
        
        return stream;
    }
    
    /**
     * Adjacency between word1 and word2 is defined by:
     * if the difference between word1 and word2 is of
     * 1 char replacement
     * 1 char addition
     * 1 char deletion
     * then
     * word1 and word2 are adjacent
     * else
     * word1 and word2 are not adjacent
     * 
     * Note: if word1 is equal to word2, they are not adjacent
     * 
     * @param word1
     *            first word
     * @param word2
     *            second word
     * @return true if word1 and word2 are adjacent else false
     */
    public static boolean isAdjacent(String word1, String word2) {
        if (word1.length() > word2.length())
            return isAddition(word2, word1);
        return (isAddition(word1, word2) || isSubstitution(word1, word2));
        
    }
    
    /**
     * Should test:
     * 1. Length of words
     * 2. Character Offset (<= 1)
     * 
     * @param word1
     *            The first word. Assumed smaller word
     * @param word2
     *            The second word. Assumed larger word
     * @return
     *         Whether or not it passes all the tests
     */
    private static boolean isAddition(String word1, String word2) {
        int charOffset = 0; // Tells us the offset. If > 1, then false
        // word2 must be one bigger than word1
        if (word1.length() - word2.length() == -1) {
            for (int i = 0; i < word1.length(); i++) {
                if (word2.charAt(i + charOffset) != word1.charAt(i)) {
                    if (charOffset++ > 0)
                        return false; // Had a substitution
                    i--;
                }
            }
            // If charOffset == 0, added letter must be at the end
            return true; // Passed all tests
        }
        return false;
    }
    
    /**
     * Should test:
     * 1. Length of words
     * 2. Character Offset (== 1)
     * 
     * @param word1
     *            The first word
     * @param word2
     *            The second word
     * @return
     *         Whether or not it passes all the tests
     */
    private static boolean isSubstitution(String word1, String word2) {
        boolean sub = false; // Tells us the offset. If > 1, then false
        // word1 must be same size as word2
        if (word1.length() - word2.length() == 0) {
            for (int i = 0; i < word1.length(); i++) {
                if (word2.charAt(i) != word1.charAt(i)) {
                    if (sub) // Already had one substitution
                        return false;
                    sub = true; // First substitution
                }
            }
        }
        return sub; // Must be true to pass
    }
}
