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
  private DocumentNode documentNode;
  private RequestContext requestContext;
  private ObjectMultistack multistack = new ObjectMultistack();
  private INodeVisitor visitor = new INodeVisitor() {
    private final String SIN_FUNCTION = "sin";
    private final String DECIMAL_NUMBER_FORMAT_FUNCTION = "decfmt";
    private final String DUPLICATE_FUNCTION = "dup";
    private final String PUSH_FUNCTION = "push";
    private final String SWAP_FUNCTION = "swap";
    private final String POP_FUNCTION = "pop";
    private final String SET_MIME_TYPE_FUNCTION = "setMimeType";
    private final String PARAM_GET_FUNCTION = "paramGet";
    private final String PERSISTENT_PARAM_GET_FUNCTION = "pparamGet";
    private final String PERSISTENT_PARAM_SET_FUNCTION = "pparamSet";
    private final String PERSISTENT_PARAM_DELETE_FUNCTION = "pparamDel";
    private final String TEMPORARY_PARAM_GET_FUNCTION = "tparamGet";
    private final String TEMPORARY_PARAM_SET_FUNCTION = "tparamSet";
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
      double maxValue = Double.parseDouble(node.getEndExpression().asText());
      double incrementValue = Double.parseDouble(node.getStepExpression().asText());
      multistack.push(variable, new ValueWrapper(node.getStartExpression().asText()));
      while (true) {
        double i = Double.parseDouble(multistack.peek(variable).getValue().toString());
        if(i >= maxValue) {
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
            x = Math.sin(x);
            temporaryStack.push(x);
          } else if(functionName.equals(DECIMAL_NUMBER_FORMAT_FUNCTION)) {
            DecimalFormat df = new DecimalFormat(temporaryStack.pop().toString());
            temporaryStack.push(df.format(Double.parseDouble(temporaryStack.pop().toString())));
          } else if(functionName.equals(SET_MIME_TYPE_FUNCTION)) {
            requestContext.setMimeType(temporaryStack.pop().toString());
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

  public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
    this.documentNode = documentNode;
    this.requestContext = requestContext;
  }

  public void execute() {
    documentNode.accept(visitor);
  }
}