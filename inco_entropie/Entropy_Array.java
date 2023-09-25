// ============================================================================
//
//      Entropy 
//
// ============================================================================
//
//      Version      using Arrays
//      Date         2019-10-07
//      Author       J. M. Stettbacher / Muth
//
//      System       Java (tested on version Windows / Eclipse)
//
// ============================================================================
//
//      Build class file:
//      (1) Compile using Eclipse or other.
//      (2) Compile using Makefile. Type on command line:
//          >> make
//      (3) Directly on command line:
//          >> javac Entropy.java
//
//      Execute class file (filename is a valid data file in ASCII format):
//      (1) Run from Eclipse by specifying the filename command line argument.
//      (2) Run from command line:
//          >> java Entropy filename
//
//      Description:
//      Reads symbols from the data file and determines:
//      - number of different character types in file.
//      - total number of characters in file.
//      - probability of each character type.
//      - information of each character type.
//      - entropy of entire file.
//
// ============================================================================

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.lang.*;

// ----------------------------------------------------------------------------
// Main class:
// ----------------------------------------------------------------------------

public class Entropy_Array {
	
	static int[] chars = new int[256]; // Limited to 8-Bit-Characters

	// Global counter variable:
	static int    fileCharactersCount = 0;
	static int    fileSymbolCount = 0;
	static double fileEntropy = 0;
	

	// Main method. The program starts here.
	public static void main(String[] args) {
		
		
		// Print hello message:
		System.out.println("======================================================");
		System.out.println("Starting ...");
		
		// ------------------------------------------------------------
		// Check if a valid filename has been supplied on command line:
		// ------------------------------------------------------------
		// Is there a command line argument at all?
		if (args.length <= 0) {
			System.out.println("ERROR: You have to supply a filename on the command line!");
			System.out.println();
			System.exit(-1);
		}
		// Yes, there is at least one argument:
		String s = args[0];
		File file = new File(s);
		if (!file.exists()) { // Check if file with that name exists:
			System.out.println("ERROR: Data file: " + s + " does not exist!");
			System.out.println();
			System.exit(-1);
		}
		
		System.out.println( "Data file " + file.getName() + " exists.");
		
		// ************************************************************************
		// Call each method:
		// ************************************************************************
		ReadInputTextFileCharacters(s);
		CountSymbols();
		ComputeEntropy();
		PrintOutCharProps();
		
		System.out.println("Done.");
		System.out.println("======================================================");
	}
	
	
	// --------------------------------------------------------------------
	// Read character from file and count them:
	// --------------------------------------------------------------------
	static void ReadInputTextFileCharacters(String relativeFilePath) {
	    
	    System.out.println("Reading file ...");
	    Arrays.fill(chars, 0);
	    
		// Open file and read character by character:
		try (BufferedReader in = new BufferedReader(new FileReader(relativeFilePath))) {
			
			int c;
			// Read characters c until there are no more:
			while ((c = in.read()) != -1) {
				if (c > ' ')  {
					/* 
					 * ToDo:  H�ufigkeit des Symbols nachf�hren
					 * */

					 chars[c] += 1;
					/* 
					 * ToDo: Gesamtzahl der Zeichen im File nachf�hren
					 * */
					fileCharactersCount += 1;
				}
			}
		} 
		catch(IOException ioe) {System.out.print("oops");}
	}

	// --------------------------------------------------------------------
	// Base 2 logarithm:
	// --------------------------------------------------------------------
	static double log2(double d) {
		
		/* 
		 * ToDo: Logarithmus zur Basis 2 berechnen
		 * 		 mit Math.log()
		 * */
		return (Math.log(d) / Math.log(2));

		// return result;
	}	
	
	// --------------------------------------------------------------------
	// Compute entropy of all characters:
	// --------------------------------------------------------------------
	static void ComputeEntropy() {
		
		System.out.println( "Computing entropy...");
		
		/* 
		 * ToDo: Entropie H der Quelle berechnen
		 * */
		
		for (int occurence : chars) { 
		// Fancy for-Loop von Java; Alternative siehe unten 

			if(occurence != 0) {
				double symbolprobability = (double) occurence / fileCharactersCount;

				double charInformation = log2(1.0 / symbolprobability);

				fileEntropy += symbolprobability * charInformation;

			}
		}
		//fileEntropy = (double) Arrays.stream(chars

	}

	
	// --------------------------------------------------------------------
	// Compute entropy of all characters:
	// --------------------------------------------------------------------
	static void CountSymbols() {
		
		System.out.println( "Computing entropy...");
		
		/* 
		 * ToDo: Anzahl der unterschiedlichen Symbole bestimmen
		 * */
		
		fileSymbolCount = 0;
		//for (int occurence : chars) {
		// Fancy for-Loop von Java; Alternative siehe unten 

				//fileSymbolCount ;

			
		//}
		//fileSymbolCount = (int) Arrays.stream(chars).distinct().count();
		fileSymbolCount = Arrays.stream(chars).reduce(0, (sum, i) -> (i > 0 ? sum + 1 : sum));
	}


	
	// --------------------------------------------------------------------
	// Print result table with occurence, probability and information:
	// --------------------------------------------------------------------
	static void PrintOutCharProps() {
	
		// Print general statistics
		System.out.println("Number of symbols in file: " + fileSymbolCount);
		System.out.println("Number of character in file: " + fileCharactersCount);
		System.out.println("Entropy of file: " + fileEntropy);
		System.out.println(" ");
		
		// Print character statistics:
		String chr;
		for (int c=0; c < chars.length; c++) {
				if ( Character.isWhitespace(c) ) {
					chr = "(" + c + ")";
				} else {
					chr = "" + (char) c;
				}
				
				/* 
				 * ToDo: Wahrscheinlichkeit des Symbols bestimmen
				 * */
				double p = (double) chars[c] / fileCharactersCount;
				
				/* 
				 * ToDo: Informationsgehalt des Symbols bestimmen
				 * */
				double i = log2(1.0 / p);
				
				System.out.format("  %5s : o=%8d  p=%14.10f  i=%14.10f%n", chr, chars[c], p, i);
			}
		}
	}
