package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class used for testing {@link SmartScriptParser}
 * 
 * @author tin
 *
 */
public class SmartScriptTester {

  /**
   * Method which is called when program starts.
   * @param args
   */
  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Only 1 argument expected.");
    }
    
    
    String docBody = null;
    try {
      docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
    } catch (IOException e1) {
      System.out.println("File args[0] cannot be read.");
      System.exit(-1);
    }
    SmartScriptParser parser = null;
    try {
      parser = new SmartScriptParser(docBody);
    } catch (SmartScriptParserException e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    } catch (Exception e) {
      System.out.println("If this line ever executes, you have failed this class!");
      System.exit(-1);
    }
    DocumentNode document = parser.getDocumentNode();
    // String tree = getTree(document, 1);
    // System.out.println(tree);
    String originalDocumentBody = createOriginalDocumentBody(document);
    System.out.println(originalDocumentBody); // should write something like original
    // content of docBody
  }

  /**
   * Method which reconstructs original document body from {@link SmartScriptParser} tree
   * @param node root node in tree
   * @return document body
   */
  public static String createOriginalDocumentBody(Node node) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(node.getText());
    for (int i = 0; i < node.numberOfChildren(); i++) {
      stringBuilder.append(createOriginalDocumentBody(node.getChild(i)));
    }
    if(node instanceof ForLoopNode) {
      stringBuilder.append("{$end$}");
    }

    return stringBuilder.toString();
  }

  /**
   * Method which prints out document tree recursively.
   * 
   * @param document root node in tree
   * @param level node level
   * @return document tree in string format
   */
  public static String getTree(Node document, int level) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(document.getText());
    if(document instanceof DocumentNode || document instanceof ForLoopNode) {
      stringBuilder.append(" Children : {");
      for (int i = 0; i < document.numberOfChildren(); i++) {
        if(i > 0) {
          stringBuilder.append(", ");
        }
        stringBuilder.append(getTree(document.getChild(i), level + 1));
      }
      stringBuilder.append("}");
    }

    return stringBuilder.toString();
  }

}