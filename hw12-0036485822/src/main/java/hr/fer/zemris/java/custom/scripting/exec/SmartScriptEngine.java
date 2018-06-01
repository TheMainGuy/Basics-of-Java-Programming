package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine which runs code written in SmartScript. Each for loop tag is
 * interpreted as a for loop having variable with start and end value. When
 * variable value becomes greaten than end value, for loop ends. Text content is
 * delegated to {@link RequestContext} write method. Echo tags are interpreted
 * as code which is run consequently.
 * 
 * @author tin
 *
 */
public class SmartScriptEngine {
  /**
   * Document node in which the script is stored.
   */
  private DocumentNode documentNode;

  /**
   * Context which is used to store, read and write data.
   */
  private RequestContext requestContext;

  /**
   * Multistack used to save variables their scope.
   */
  private ObjectMultistack multistack = new ObjectMultistack();

  /**
   * Visitor which visits all nodes and runs their code.
   */
  private INodeVisitor visitor = new INodeVisitor() {

    /**
     * Constant that defines function which calculates sin from top of the stack.
     */
    private final String SIN_FUNCTION = "sin";

    /**
     * Constant that defines function which uses first object on top of the stack as
     * decimal number format to format second value on top of the stack as decimal
     * number.
     */
    private final String DECIMAL_NUMBER_FORMAT_FUNCTION = "decfmt";

    /**
     * Constant that defines function which duplicates value on top of the stack
     * creating to objects on top of the stack.
     */
    private final String DUPLICATE_FUNCTION = "dup";

    /**
     * Constant that defines function which swaps first 2 values on top of the
     * stack.
     */
    private final String SWAP_FUNCTION = "swap";

    /**
     * Constant that defines function which sets context's mime type.
     */
    private final String SET_MIME_TYPE_FUNCTION = "setMimeType";

    /**
     * Constant that defines function which gets parameter from context's parameters
     * map and puts it on top of the stack. It uses first value on top of the stack
     * to define default value and second value as key for getting value from
     * parameters map.
     */
    private final String PARAM_GET_FUNCTION = "paramGet";

    /**
     * Constant that defines function which gets persistent parameter from context's
     * persistentParameters map and puts it on top of the stack. It uses first value
     * on top of the stack to define default value and second value as key for
     * getting value from persistentParameters map.
     */
    private final String PERSISTENT_PARAM_GET_FUNCTION = "pparamGet";

    /**
     * Constant that defines function which sets persistent parameter in context's
     * persistentParameters map. It uses first value on top of the stack as key and
     * second value as value for putting value in persistentParameters map.
     */
    private final String PERSISTENT_PARAM_SET_FUNCTION = "pparamSet";

    /**
     * Constant that defines function which removes key value pair from context's
     * persistentParameters map using value from top of the stack as key.
     */
    private final String PERSISTENT_PARAM_DELETE_FUNCTION = "pparamDel";

    /**
     * Constant that defines function which gets temporary parameter from context's
     * temporaryParameters map and puts it on top of the stack. It uses first value
     * on top of the stack to define default value and second value as key for
     * getting value from temporaryParameters map.
     */
    private final String TEMPORARY_PARAM_GET_FUNCTION = "tparamGet";

    /**
     * Constant that defines function which sets temporary parameter in context's
     * temporaryParameters map. It uses first value on top of the stack as key and
     * second value as value for putting value in temporaryParameters map.
     */
    private final String TEMPORARY_PARAM_SET_FUNCTION = "tparamSet";

    /**
     * Constant that defines function which removes key value pair from context's
     * temporaryParameters map using value from top of the stack as key.
     */
    private final String TEMPORARY_PARAM_DELETE_FUNCTION = "tparamDel";

    @Override
    public void visitTextNode(TextNode node) {
      try {
        requestContext.write(node.getText());
      } catch (IOException e) {
        System.out.println("Problem with writing to given output stream.");
      }
    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {
      String variable = node.getVariable().asText();
      int maxValue = Integer.parseInt(node.getEndExpression().asText());
      int incrementValue = Integer.parseInt(node.getStepExpression().asText());
      multistack.push(variable, new ValueWrapper(node.getStartExpression().asText()));
      while (true) {
        int i = Integer.parseInt(multistack.peek(variable).getValue().toString());
        if(i > maxValue) {
          multistack.pop(variable);
          break;
        }

        for (int j = 0, n = node.numberOfChildren(); j < n; j++) {
          node.getChild(j).accept(this);
        }
        multistack.peek(variable).add(incrementValue);
      }
    }

    @Override
    public void visitEchoNode(EchoNode node) {
      Stack<Object> temporaryStack = new Stack<>();
      Element[] elements = node.getElements();
      for (int i = 0; i < elements.length; i++) {
        if(elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementConstantInteger
            || elements[i] instanceof ElementString) {
          temporaryStack.push(elements[i].asText());
        } else if(elements[i] instanceof ElementVariable) {
          if(elements[i].asText().equals("=")) {
            continue;
          }
          temporaryStack.push(multistack.peek(elements[i].asText()).getValue());
        } else if(elements[i] instanceof ElementOperator) {
          char symbol = elements[i].asText().charAt(0);
          ValueWrapper operand2 = new ValueWrapper(temporaryStack.pop());
          ValueWrapper operand1 = new ValueWrapper(temporaryStack.pop());
          if(symbol == '+') {
            operand1.add(operand2.getValue());
          } else if(symbol == '-') {
            operand1.subtract(operand2.getValue());
          } else if(symbol == '*') {
            operand1.multiply(operand2.getValue());
          } else if(symbol == '/') {
            operand1.divide(operand2.getValue());
          } else {
            throw new UnsupportedOperationException("Operation for symbol " + symbol + " not supported.");
          }
          temporaryStack.push(operand1.getValue());
        } else if(elements[i] instanceof ElementFunction) {
          String functionName = elements[i].asText().substring(1);
          if(functionName.equals(SIN_FUNCTION)) {
            double x = Double.parseDouble(temporaryStack.pop().toString());
            x = Math.sin(x * Math.PI / 180);
            temporaryStack.push(x);
          } else if(functionName.equals(DECIMAL_NUMBER_FORMAT_FUNCTION)) {
            DecimalFormat df = new DecimalFormat(temporaryStack.pop().toString());
            temporaryStack.push(df.format(Double.parseDouble(temporaryStack.pop().toString())));
          } else if(functionName.equals(SET_MIME_TYPE_FUNCTION)) {
            requestContext.setMimeType(temporaryStack.pop().toString());
          } else if(functionName.equals(DUPLICATE_FUNCTION)) {
            Object x = temporaryStack.pop();
            temporaryStack.push(x);
            temporaryStack.push(x);
          } else if(functionName.equals(SWAP_FUNCTION)) {
            Object x = temporaryStack.pop();
            Object y = temporaryStack.pop();
            temporaryStack.push(x);
            temporaryStack.push(y);
          } else if(functionName.equals(PARAM_GET_FUNCTION)) {
            Object defaultValue = temporaryStack.pop();
            String name = temporaryStack.pop().toString();
            String value = requestContext.getParameter(name);
            if(value == null) {
              temporaryStack.push(defaultValue);
            } else {
              temporaryStack.push(value);
            }
          } else if(functionName.equals(PERSISTENT_PARAM_GET_FUNCTION)) {
            Object defaultValue = temporaryStack.pop();
            String name = temporaryStack.pop().toString();
            String value = requestContext.getPersistentParameter(name);
            if(value == null) {
              temporaryStack.push(defaultValue);
            } else {
              temporaryStack.push(value);
            }
          } else if(functionName.equals(TEMPORARY_PARAM_GET_FUNCTION)) {
            Object defaultValue = temporaryStack.pop();
            String name = temporaryStack.pop().toString();
            String value = requestContext.getTemporaryParameter(name);
            if(value == null) {
              temporaryStack.push(defaultValue);
            } else {
              temporaryStack.push(value);
            }
          } else if(functionName.equals(PERSISTENT_PARAM_SET_FUNCTION)) {
            String name = temporaryStack.pop().toString();
            String value = temporaryStack.pop().toString();
            requestContext.setPersistentParameter(name, value);
          } else if(functionName.equals(TEMPORARY_PARAM_SET_FUNCTION)) {
            String name = temporaryStack.pop().toString();
            String value = temporaryStack.pop().toString();
            requestContext.setTemporaryParameter(name, value);
          } else if(functionName.equals(PERSISTENT_PARAM_DELETE_FUNCTION)) {
            String name = temporaryStack.pop().toString();
            requestContext.removePersistentParameter(name);
          } else if(functionName.equals(TEMPORARY_PARAM_DELETE_FUNCTION)) {
            String name = temporaryStack.pop().toString();
            requestContext.removeTemporaryParameter(name);
          }
        }
      }

      Stack<Object> temporaryStack2 = new Stack<>();
      while (!temporaryStack.isEmpty()) {
        temporaryStack2.push(temporaryStack.pop());
      }
      while (!temporaryStack2.isEmpty()) {
        try {
          requestContext.write(temporaryStack2.pop().toString());
        } catch (IOException e) {
          System.out.println("Problem with writing to output stream.");
        }
      }
    }

    @Override
    public void visitDocumentNode(DocumentNode node) {
      for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
        node.getChild(i).accept(this);
      }
    }

  };

  /**
   * Constructor.
   * 
   * @param documentNode node in which the script is stored
   * @param requestContext context which will be used for storing, getting and
   *          writing data
   */
  public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
    this.documentNode = documentNode;
    this.requestContext = requestContext;
  }

  /**
   * Executes smart script engine which effectively runs the script given in
   * document node. Output from run script is written using given
   * {@link RequestContext} object.
   */
  public void execute() {
    documentNode.accept(visitor);
    try {
      requestContext.write("\n");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}