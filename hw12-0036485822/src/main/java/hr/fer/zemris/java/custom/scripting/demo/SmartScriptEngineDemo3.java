package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngineDemo3 {
  public static void main(String[] args) {
    
    if(args.length != 1) {
      System.out.println("Only 1 argument expected.");
    }
    String documentBody = null;
    try {
      documentBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
    } catch (IOException e1) {
      System.out.println("File " + args[0] + " cannot be read.");
      System.exit(-1);
    }
    Map<String,String> parameters = new HashMap<String, String>();
    Map<String,String> persistentParameters = new HashMap<String, String>();
    List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
    persistentParameters.put("brojPoziva", "3");
    RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
    cookies);
    new SmartScriptEngine(
    new SmartScriptParser(documentBody).getDocumentNode(), rc
    ).execute();
    System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
  }
  
}
