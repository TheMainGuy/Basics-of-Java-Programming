package hr.fer.zemris.java.hw16.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.math.Vector;

/**
 * Search engine program which allows user to search files using tfidf scheme.
 * When the program is started, vocabulary, document vectors and other
 * parameters are set based on documents located in directory given as command
 * line argument. Also, stop words document defines words which are ignored
 * during search process.
 * 
 * After initialization, user can use query command to pass words as arguments,
 * which will then be compared with documents and results will be printed out.
 * 
 * After using query command, user can use results and type command to display
 * results again or print out whole document respectively.
 * 
 * Exit command will terminate program.
 * 
 * @author tin
 *
 */
public class Console {
  /**
   * Relative path to stop words file.
   */
  private static final String STOP_WORDS_FILE_PATH = "./src/main/resources/stopWords.txt";

  /**
   * Set of all words in documents except stop words.
   */
  private static Set<String> vocabulary = new LinkedHashSet<>();

  /**
   * Map of document vectors. Each vector is mapped to its document.
   */
  private static Map<String, Vector> documentVectors = new HashMap<>();
  
  /**
   * Set of stop words.
   */
  private static Set<String> stopWords = new HashSet<>();
  
  /**
   * Idf vector.
   */
  private static Vector idf;
  
  /**
   * List of maximum ten results which are best match to last query.
   */
  private static List<QueryResult> topTen = new ArrayList<>();

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if(args.length != 1) {
      throw new IllegalArgumentException("There must be one and no more than one command line argument.");
    }
    fillVocabulary(args[0]);
    System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");
    createDocumentVectors(args[0]);
    createIdf(args[0]);
    calculateTfidfVectors();

    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      String[] words = scanner.nextLine().split(" ");
      if(words.length == 0) {
        continue;
      } else if(words.length == 1) {
        if(words[0].equals("results")) {
          printTopTen();
        } else if(words[0].equals("exit")) {
          break;
        } else {
          System.out.println("Command does not exist or does not have correct number of arguments.");
        }
      } else if(words.length > 1) {
        if(words[0].equals("query")) {
          performQuery(words);
        } else if(words[0].equals("type") && words.length == 2) {
          printFile(words[1]);
        } else {
          System.out.println("Command does not exist or does not have correct number of arguments.");
        }
      }
    }
    scanner.close();
  }

  /**
   * Prints file from given top ten index to standard output.
   * 
   * @param string top ten index used to get file
   */
  private static void printFile(String topTenIndex) {
    if(topTen.size() == 0) {
      System.out.println("There are no results stored.");
      return;
    }
    List<String> lines = null;
    String document = null;
    try {
      int index = Integer.parseInt(topTenIndex);
      document = topTen.get(index).getDocument();
      lines = Files.readAllLines(Paths.get(document));
    } catch (IOException e) {
      System.out.println("Problem with reading file.");
      return;
    } catch (NumberFormatException e) {
      System.out.println("Could not parse given argument as number.");
      return;
    }

    System.out.println("----------------------------------------------------------------");
    System.out.println("Dokument: " + document);
    System.out.println("----------------------------------------------------------------");
    for (String line : lines) {
      System.out.println(line);
    }

  }

  /**
   * Prints top ten results from last query.
   */
  private static void printTopTen() {
    int i = 0;
    NumberFormat formatter = new DecimalFormat("#0.0000");
    for (QueryResult topTenResult : topTen) {
      if(topTenResult.getSimilarity() < 0.01)
        continue;

      System.out.println(
          "[ " + i + "] (" + formatter.format(topTenResult.getSimilarity()) + ") " + topTenResult.getDocument());
      i++;
    }
  }

  /**
   * Changes documentVectors map values from frequency vectors values to tdidf
   * vectors values.
   */
  private static void calculateTfidfVectors() {
    Map<String, Vector> tfidfDocumentVectors = new HashMap<>();
    for (String document : documentVectors.keySet()) {
      List<Double> newVector = new ArrayList<>();
      for (int i = 0, n = vocabulary.size(); i < n; i++) {
        newVector.add(documentVectors.get(document).getElementAt(i) * idf.getElementAt(i));
      }
      tfidfDocumentVectors.put(document, new Vector(newVector));
    }
    documentVectors = tfidfDocumentVectors;
  }

  /**
   * Performs query. Calculates tfidf vector for given words and uses it to find
   * the best result. After sorting thsoe results, prints them in standard output
   * and saves them to topTen.
   * 
   * @param words words from which the query is consisted
   */
  private static void performQuery(String[] words) {
    List<String> query = Arrays.asList(Arrays.copyOfRange(words, 1, words.length));
    List<Double> vector = new ArrayList<>();
    Map<String, Integer> queryMap = getWordsFromList(query);
    for (String word : vocabulary) {
      if(!queryMap.containsKey(word)) {
        vector.add(0.0);
      } else {
        double numberOfWordRepetition = queryMap.get(word);
        vector.add(numberOfWordRepetition);
      }
    }
    Vector frequencyVector = new Vector(vector);
    List<Double> tfidfValues = new ArrayList<>();
    for (int i = 0, n = vocabulary.size(); i < n; i++) {
      tfidfValues.add(frequencyVector.getElementAt(i) * idf.getElementAt(i));
    }
    Vector tfidfVector = new Vector(tfidfValues);
    getTopTen(tfidfVector);
    for (int i = 0; i < query.size(); i++) {
      if(i == 0) {
        System.out.print("Query is: [" + query.get(i));
      } else {
        System.out.print(", " + query.get(i));
      }
    }
    System.out.println("]");
    System.out.println("Najboljih 10 rezultata:");
    printTopTen();
  }

  /**
   * Method gets top ten results from given tfidf vector and fills topTen list
   * with them.
   * 
   * @param tfidfVector vector which will be used to determine topTen
   */
  private static void getTopTen(Vector tfidfVector) {
    List<QueryResult> results = new ArrayList<>();
    for (String document : documentVectors.keySet()) {
      double similarity = tfidfVector.dot(documentVectors.get(document))
          / (tfidfVector.norm() * documentVectors.get(document).norm());
      results.add(new QueryResult(document, similarity));
    }
    results.sort((a, b) -> Double.compare(b.getSimilarity(), a.getSimilarity()));
    topTen = new ArrayList<>();
    for (int i = 0, n = Math.min(results.size() - 1, 10); i < n; i++) {
      topTen.add(results.get(i));
    }
  }

  /**
   * Goes through all documents and counts the number of repetition of each word.
   * Using that information, calculates and stores idf vector.
   * 
   * @param string
   */
  private static void createIdf(String path) {
    List<Double> vector = new ArrayList<>();

    for (int i = 0, n = vocabulary.size(); i < n; i++) {
      vector.add(Math.log(documentVectors.keySet().size() * 1.0 / numberOfDocumentsWithWord(path, i)));
    }
    idf = new Vector(vector);
  }

  /**
   * Helper method used to count the number of documents containing given word.
   * Word is given using its index.
   * 
   * @param path
   * @param i
   * @return
   */
  private static int numberOfDocumentsWithWord(String path, int i) {
    int numberOfDocuments = 0;
    for (String document : documentVectors.keySet()) {
      if(documentVectors.get(document).getElementAt(i) != 0) {
        numberOfDocuments++;
      }
    }
    return numberOfDocuments;
  }

  /**
   * Creates document frequency vectors based upon the number of times which each
   * word from vocabulary is repeated in each one document in given file.
   * 
   * @param path path to directory from which the documents are read
   */
  private static void createDocumentVectors(String path) {
    File directory = new File(path);
    for (File file : directory.listFiles()) {
      addDocumentVector(file.getAbsolutePath());
    }
  }

  /**
   * Helper method which creates and adds one document frequency vector in
   * documentVectors map.
   * 
   * @param absolutePath path to file for which will be created document frequency
   *          vector
   */
  private static void addDocumentVector(String absolutePath) {
    List<Double> vector = new ArrayList<>();
    Map<String, Integer> words = getWordsFromFile(absolutePath);
    for (String word : vocabulary) {
      if(words.containsKey(word)) {
        int numberOfRepetition = words.get(word);
        double doubleNumber = numberOfRepetition;
        vector.add(doubleNumber);
      } else {
        vector.add(0.0);
      }
    }
    documentVectors.put(absolutePath, new Vector(vector));
  }

  /**
   * Fills vocabulary set with words from all text files located in given
   * directory.
   * 
   * @param path path to directory
   */
  private static void fillVocabulary(String path) {
    stopWords = new HashSet<>(getWordsFromFile(STOP_WORDS_FILE_PATH).keySet());

    File directory = new File(path);
    if(!directory.isDirectory()) {
      throw new IllegalArgumentException("Given file is not a directory.");
    }
    for (File file : directory.listFiles()) {
      addWordsToVocabulary(file.getAbsolutePath());
    }
  }

  /**
   * Helper method used to add all words from given file to vocabulary set. Words
   * from stopWords set will not be added to vocabulary.
   * 
   * @param absolutePath path to file from which the words will be extracted
   * @param stopWords words which will be excluded from adding to vocabulary
   */
  private static void addWordsToVocabulary(String absolutePath) {
    Map<String, Integer> words = getWordsFromFile(absolutePath);
    for (String word : words.keySet()) {
      vocabulary.add(word);
    }
  }

  /**
   * Helper method which extracts all words from given file and stores them in
   * {@link HashMap}. Each different word is map key and the number of its
   * repetition is map value.
   * 
   * @param absolutePath path to file from which the words will be extracted
   * @return map of all words from given file
   */
  private static Map<String, Integer> getWordsFromFile(String absolutePath) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(absolutePath), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalArgumentException("Can not read file " + absolutePath);
    }

    return getWordsFromList(lines);

  }

  /**
   * Helper method which extracts all words from given list of strings and stores
   * them in {@link HashMap}. Each different word is map key and the number of its
   * repetition is map value.
   * 
   * @param lines list of strings from which words will be read
   * @return map of all words from given list
   */
  private static Map<String, Integer> getWordsFromList(List<String> lines) {
    Map<String, Integer> words = new HashMap<>();
    for (String line : lines) {
      StringBuilder word = new StringBuilder();
      for (int i = 0, n = line.length(); i < n; i++) {
        char c = line.charAt(i);
        if(Character.isAlphabetic(c)) {
          word.append(c);
        } else {
          if(!stopWords.contains(word.toString().toLowerCase()) && !word.toString().equals("")) {
            String wordString = word.toString().toLowerCase();
            if(words.containsKey(wordString)) {
              words.put(wordString, words.get(wordString) + 1);
            } else {
              words.put(wordString, 1);
            }
          }
          word = new StringBuilder();
        }
      }
      if(!stopWords.contains(word.toString().toLowerCase()) && !word.toString().equals("")) {
        String wordString = word.toString().toLowerCase();
        if(words.containsKey(wordString)) {
          words.put(wordString, words.get(wordString) + 1);
        } else {
          words.put(wordString, 1);
        }
      }
    }
    return words;
  }
}