package ca.jrvs.apps.grep;

import java.io.*;
import java.util.*;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse the given rootDir path and return all files in it
   * @param rootDir
   * @return
   */
  List<File> listFiles(String rootDir);

  /**
   * Reads a file and returns all lines in an ArrayList (BufferedReader)
   * @param inputFile
   * @return
   * @throws IOException
   */
  List<String> readLines(File inputFile) throws IOException;

  /**
   * Checks if a line contains the regex pattern given by the user input
   * @param line
   * @return
   */
  boolean containsPattern(String line);

  /**
   * Writes filtered lines to a file, with the filename given by the user (FileOutputStream)
   * @param lines
   * @throws IOException
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
