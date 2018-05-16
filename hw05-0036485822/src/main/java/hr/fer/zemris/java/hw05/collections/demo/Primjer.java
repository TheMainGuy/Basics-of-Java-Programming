package hr.fer.zemris.java.hw05.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class Primjer {
  public static void main(String[] args) {
    // create collections:
    SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
    SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
    // fill data:
    examMarks.put("Ivana", 2);
    examMarks.put("Ante", 2);
    examMarks.put("Jasna", 2);
    examMarks.put("Kristina", 5);
    examMarks.put("Ivana", 5); // overwrites old grade for Ivana

    examMarks2.put("Ivana", 2);
    examMarks2.put("Ante", 2);
    examMarks2.put("Jasna", 2);
    examMarks2.put("Kristina", 5);
    examMarks2.put("Ivana", 5); // overwrites old grade for Ivana
    for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
      System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
    }

    for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
      for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
        System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
            pair2.getValue());
      }
    }

    removeWithoutException(examMarks);
    try {
      removeWithIllegalStateException(examMarks2);
    } catch (IllegalStateException e) {
      System.out.println("Exception caught!");
      examMarks2.put("Ivana", 5);
    }
    try {
      removeWithConcurrentModificationException(examMarks2);
    } catch (ConcurrentModificationException e) {
      System.out.println("Exception caught!");
    }

    printAndRemove(examMarks2);
  }

  private static void printAndRemove(SimpleHashtable<String, Integer> examMarks) {
    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
      iter.remove();
    }
    System.out.printf("Veličina: %d%n", examMarks.size());

  }

  private static void removeWithIllegalStateException(SimpleHashtable<String, Integer> examMarks) {
    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      if(pair.getKey().equals("Ivana")) {
        iter.remove();
        iter.remove();
      }
    }

  }

  private static void removeWithoutException(SimpleHashtable<String, Integer> examMarks) {
    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      if(pair.getKey().equals("Ivana")) {
        iter.remove(); // sam iterator kontrolirano uklanja trenutni element
      }
    }

    if(examMarks.containsKey("Ivana")) {
      System.out.println("Ivana still here.");
    }
    else {
      System.out.println("Ivana successfully removed.");
    }
  }

  private static void removeWithConcurrentModificationException(SimpleHashtable<String, Integer> examMarks) {
    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      if(pair.getKey().equals("Ivana")) {
        examMarks.remove("Ivana");
      }
    }
  }

}