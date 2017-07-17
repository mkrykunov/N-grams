/* 
* This program reads sentences from the input file
* and creates N-grams. It stores them as N integer
* numbers arrays. These numbers correspond to the 
* indices of words in the vocabulary created from
* the same set of sentences. The default input file
* name is "raw_sentences.txt", and the default value
* of N is 4. However, you can specify different
* parameters in the command line as:
*
* java N_grams myfile.txt 3
*/
import java.io.*;
import java.util.*;

public class N_grams {

   public static void main (String[] args) {

      String inputFile = (args.length > 0) ? args[0] : "raw_sentences.txt";

      System.out.println("Input file: " + inputFile);

      int N = (args.length > 0) ? Integer.parseInt(args[1]) : 4;

      ArrayList<String> rawLines = new ArrayList<String>();
      LinkedHashMap<String,Integer> vocabulary = new LinkedHashMap<String,Integer>();
      ArrayList<String> ngramList = new ArrayList<String>();
      ArrayList<int[]> ngramInt = new ArrayList<int[]>();

      readRawLines(inputFile, rawLines);

      for (String str: rawLines) {
         entries2Vocabulary(str.toLowerCase(), vocabulary);
      }
      System.out.println("Number of raw lines = " + rawLines.size());

      for (String str: vocabulary.keySet()) {
         System.out.print(str + " ");
      }
      System.out.println("\nVocabulary size = " + vocabulary.size());

      int icount = 0;
      for (String str: rawLines) {
         generateNgrams(N, str.toLowerCase(), ngramList);
         icount++;
         if (icount % 1000 == 0) 
            System.out.println("" + icount + " line processed out of " + rawLines.size());
      }

      icount = 0;
      int firstNgrams = 25;
      System.out.println("\nFirst " + firstNgrams + " n-grams:");
      for (String str: ngramList) {
         if (++icount < firstNgrams) {
            System.out.print(str + " ");
            int[] tmp = convert2Integers(str, vocabulary);
            System.out.print(" ... ");
            for (int i = 0; i < tmp.length; i++)
               System.out.print(" " + tmp[i]);
            System.out.println("");
         }

         ngramInt.add(convert2Integers(str, vocabulary));
      }

      System.out.println("\nNumber of n-grams = " + ngramList.size());

      ngramList = new ArrayList<String>(new LinkedHashSet<String>(ngramList));

      System.out.println("\nNumber of unique n-grams = " + ngramList.size() + "\n");

      // You need to set this parameter to true
      // if you need to store only unique N-grams.
      //
      boolean needUniqueNgrams = false;

      if (needUniqueNgrams) {
         ngramInt.clear();
         for (String str: ngramList) {
            ngramInt.add(convert2Integers(str, vocabulary));
         }
      }

      writeVocabulary("vocabulary.txt", vocabulary);

      writeNgramInt("ngramInt.txt", ngramInt);
   }

   public static void entries2Vocabulary(String tmp, LinkedHashMap<String,Integer> words) {
      // trim() is necessary to get rid of spaces in the beginning
      //
      String[] tokens = tmp.trim().split("\\s+");

      for (int i = 0; i < tokens.length; i++) {
         int index = words.size() + 1;
         if (!words.containsKey(tokens[i])) {
             words.put(tokens[i], index);
         }
      }
   }

   public static void generateNgrams(int N, String sentence, ArrayList<String> ngramList) {
      String[] tokens = sentence.trim().split("\\s+");

      for (int k = 0; k < (tokens.length - N + 1); k++) {
         String s = "";
         int start = k;
         int end = k + N;

         for (int j = start; j < end; j++) {
            s = s + " " + tokens[j];
         }

         ngramList.add(s.trim());
      }
   }

   public static int[] convert2Integers(String tmp, LinkedHashMap<String,Integer> words) {
      String[] tokens = tmp.trim().split("\\s+");
      int[] array = new int [tokens.length];

      for (int i = 0; i < tokens.length; i++) {
         array[i] = words.get(tokens[i]);
      }

      return array;
   }

   public static void readRawLines(String inputFile, ArrayList<String> rawLines) {
      BufferedReader in = null;

      try {
         in = new BufferedReader(new FileReader(inputFile));
         String str;

         while ((str = in.readLine()) != null) {
            rawLines.add(str);
         }
      } 
      catch (IOException ioe) {
         ioe.printStackTrace();
      }
      finally { 
         try {
            if (in != null) in.close();
         }
         catch (Exception ex) {
	    System.out.println("Error in closing the BufferedReader" + ex);
         }
      }
   }

   public static void writeVocabulary(String fileName, LinkedHashMap<String,Integer> words) {
      BufferedWriter bw = null;

      try {
	 File file = new File(fileName);

         if (!file.exists()) {
	    file.createNewFile();
	 }

         FileWriter fw = new FileWriter(file);
	 bw = new BufferedWriter(fw);

         for (String str: words.keySet()) {
            bw.write(str);
            bw.newLine();
         }
         System.out.println("File " + fileName + " written successfully");
      } 
      catch (IOException ioe) {
         ioe.printStackTrace();
      }
      finally { 
         try {
            if (bw != null) bw.close();
         }
         catch (Exception ex) {
	    System.out.println("Error in closing the BufferedWriter" + ex);
         }
      }
   }

   public static void writeNgramInt(String fileName, ArrayList<int[]> ngramInt) {
      BufferedWriter bw = null;

      try {
	 File file = new File(fileName);

         if (!file.exists()) {
	    file.createNewFile();
	 }

         FileWriter fw = new FileWriter(file);
	 bw = new BufferedWriter(fw);

         for (int[] array: ngramInt) {
            String str = "";
            for (int i = 0; i < array.length; i++)
               str = str + array[i] + " ";
            bw.write(str);
            bw.newLine();
         }
         System.out.println("File " + fileName + " written successfully");
      } 
      catch (IOException ioe) {
         ioe.printStackTrace();
      }
      finally { 
         try {
            if (bw != null) bw.close();
         }
         catch (Exception ex) {
	    System.out.println("Error in closing the BufferedWriter" + ex);
         }
      }
   }
}