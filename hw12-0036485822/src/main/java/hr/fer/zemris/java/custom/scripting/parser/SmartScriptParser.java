package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class represents script parser. It parses through text document and checks
 * for syntax errors.
 * 
 * @author tin
 *
 */
public class SmartScriptParser {
  private Lexer lexer;
  private DocumentNode tree;
  private ObjectStack stack = new ObjectStack();

  /**
   * Constructor. Creates instance of lexer and creates tree which can be used to
   * get document body.
   * 
   * @param documentBody document body
   * @throws SmartScriptParserException if any error occurs during parsing
   */
  public SmartScriptParser(String documentBody) {
    lexer = new Lexer(documentBody);
    try {
      createTree();
    } catch (Exception e) {
      if(!e.getMessage().equals("No tokens left.")) {
        throw new SmartScriptParserException(e.getMessage());
      }
    }

  }

  /**
   * Creates document tree.
   * 
   * @throws SmartScriptParserException if there are not enough end tags to close
   *           all for tags
   */
  private void createTree() {
    tree = new DocumentNode();
    parseText();
    if(!stack.isEmpty()) {
      throw new SmartScriptParserException("Every FOR tag must be closed with end tag.");
    }
  }

  /**
   * Parses text until first open tag.
   * 
   * @throws SmartScriptParserException if close tag is expected
   */
  private void parseText() {
    while (lexer.nextToken().getType() != TokenType.EOF) {
      if(lexer.getToken().getType() == TokenType.TEXT) {
        addNodeToTree(new TextNode(lexer.getToken().getValue().toString()));
      }
      else if(lexer.getToken().getType() == TokenType.TAG_OPEN) {
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        parseOpenTag();
        if(lexer.getToken().getType() == TokenType.TAG_CLOSE) {
          lexer.setState(LexerState.TEXT);
        }
        else {
          throw new SmartScriptParserException("Close tag expected.");
        }
      }
    }
  }

  /**
   * Checks first token in tag and parses tag accordingly.
   * 
   * @throws SmartScriptParserException if tag does not have and is not a for tag.
   */
  private void parseOpenTag() {
    if(lexer.getToken().getValue().toString().toLowerCase().equals("for")) {
      parseForLoop();
    }
    else if(lexer.getToken().getValue().toString().toLowerCase().equals("end")) {
      parseEnd();
    }
    else if(lexer.getToken().getType() == TokenType.NAME) {
      parseEcho();
    }
    else {
      throw new SmartScriptParserException("Tag must have a name.");
    }
  }

  /**
   * Parses for loop tag.
   * 
   * @throws SmartScriptParserException if there are too few or illegal arguments
   *           in for loop.
   */
  private void parseForLoop() {
    ElementVariable elementVariable = new ElementVariable(lexer.nextToken().getValue().toString());
    Element[] elements = new Element[3];
    for (int i = 0; i < 3; i++) {
      lexer.nextToken();
      if(lexer.getToken().getType() == TokenType.EOF) {
        throw new SmartScriptParserException("Close tag expected.");
      }
      if(lexer.getToken().getType() == TokenType.TAG_CLOSE) {
        if(i < 2) {
          throw new SmartScriptParserException("Too few arguments in for loop.");
        }
        break;
      }
      else if(lexer.getToken().getType() == TokenType.NAME && !lexer.getToken().getType().toString().equals("=")) {
        elements[i] = new ElementVariable(lexer.getToken().getValue().toString());
      }
      else if(lexer.getToken().getType() == TokenType.STRING) {
        elements[i] = new ElementString(lexer.getToken().getValue().toString());
      }
      else if(lexer.getToken().getType() == TokenType.INTEGER_NUMBER) {
        // Double number = (double) lexer.getToken().getValue();
        elements[i] = new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString()));
      }
      else if(lexer.getToken().getType() == TokenType.DOUBLE_NUMBER) {
        elements[i] = new ElementConstantDouble((double) lexer.getToken().getValue());
      }
      else {
        throw new SmartScriptParserException("Illegal for loop argument");
      }
    }
    if(elements[2] != null) {
      lexer.nextToken();
    }

    ForLoopNode forLoopNode = new ForLoopNode(elementVariable, elements[0], elements[1], elements[2]);
    addNodeToTree(forLoopNode);
    stack.push(forLoopNode);
  }

  /**
   * Parses end tag.
   * 
   * @throws SmartScriptParserException if there are other elements in end tag
   */
  private void parseEnd() {
    if(lexer.nextToken().getType() != TokenType.TAG_CLOSE) {
      throw new SmartScriptParserException("End tag must be empty.");
    }
    stack.pop();
  }

  /**
   * Parses echo tag.
   * 
   * @throws SmartScriptParserException if there is illegal argument in echo tag
   */
  private void parseEcho() {
    ArrayIndexedCollection elementsCollection = new ArrayIndexedCollection();
    elementsCollection.add(lexer.getToken());
    while (lexer.nextToken().getType() != TokenType.TAG_CLOSE) {
      if(lexer.getToken().getType() == TokenType.EOF) {
        throw new SmartScriptParserException("Close tag expected.");
      }
      elementsCollection.add(lexer.getToken());
    }
    Element[] elements = new Element[elementsCollection.size()];
    for (int i = 0; i < elementsCollection.size(); i++) {
      Token token = (Token) elementsCollection.get(i);
      if(token.getType() == TokenType.FUNCTION) {
        elements[i] = new ElementFunction(token.getValue().toString());
      }
      else if(token.getType() == TokenType.DOUBLE_NUMBER) {
        elements[i] = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
      }
      else if(token.getType() == TokenType.INTEGER_NUMBER) {
        // Double number = (double) lexer.getToken().getValue();
        elements[i] = new ElementConstantDouble(Integer.parseInt(token.getValue().toString()));
      }
      else if(token.getType() == TokenType.NAME) {
        elements[i] = new ElementVariable(token.getValue().toString());
      }
      else if(token.getType() == TokenType.STRING) {
        elements[i] = new ElementString(token.getValue().toString());
      }
      else if(token.getType() == TokenType.OPERATOR) {
        elements[i] = new ElementOperator(token.getValue().toString());
      }
      else {
        throw new SmartScriptParserException("Illegal echo argument.");
      }
    }
    addNodeToTree(new EchoNode(elements));

  }

  /**
   * Adds node to tree.
   * 
   * @param node node to be added to tree
   */
  private void addNodeToTree(Node node) {
    if(stack.isEmpty()) {
      tree.addChildNode(node);
    }
    else {
      ForLoopNode parent = (ForLoopNode) stack.peek();
      parent.addChildNode(node);
    }
  }

  /**
   * Returns parser document tree.
   * 
   * @return document tree
   */
  public DocumentNode getDocumentNode() {
    return tree;
  }

}
