package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class TreeWriter {
  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Only 1 argument expected.");
    }
    
    
    String docBody = null;
    try {
      docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
    } catch (IOException e1) {
      System.out.println("File " + args[0] + " cannot be read.");
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
    
    WriterVisitor visitor = new WriterVisitor();
    parser.getDocumentNode().accept(visitor);
    System.out.println(visitor.getOriginalBody());
  }
  
  static class WriterVisitor implements INodeVisitor{
    String originalBody = "";
    
    @Override
    public void visitTextNode(TextNode node) {
      originalBody += node.getText();
    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {
      originalBody += node.getText();
      for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
        node.getChild(i).accept(this);
      }
      originalBody += "{$end$}";
    }

    @Override
    public void visitEchoNode(EchoNode node) {
      originalBody += node.getText();
    }

    @Override
    public void visitDocumentNode(DocumentNode node) {
      originalBody += node.getText();
      for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
        node.getChild(i).accept(this);
      }
    }
    
    public String getOriginalBody() {
      return originalBody;
    }
  }
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
  
  
}
