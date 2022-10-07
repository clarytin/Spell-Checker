/*
    Spell-Checking Program
    by Clarissa Sandejas
    8th February 2021
*/

package main.java;
import static main.java.Colours.*;
import java.util.ArrayList;

public class Main
{
    private final IOHandler handler = new IOHandler();
    private SpellChecker checker;

    public static void main(String[] args)
    {
        new Main().go();
    }

    private void go()
    {
        printWelcomeMessage();
        int ifFile = getFileOrString();
        getLanguageChoice();
        String in = getUserInput(ifFile);

        StringArray input = handler.extractWords(in, ifFile);
        StringArray words = handler.cleanUpInput(input);
        ArrayList<Integer> wrongs = checker.getMisspellings(words);

        printWrongWords(words, wrongs);
        applyCorrections(input, wrongs, getCorrections(words, wrongs));
        outputCorrectVersion(input, ifFile);
    }

    private void printWelcomeMessage()
    {
        System.out.println(BLUE +   "-------------------------------------" + RESET);
        System.out.println(PURPLE + "Welcome to the spell-checking program" + RESET);
        System.out.println(BLUE +   "-------------------------------------" + RESET);
    }

    private int getFileOrString()
    {
        System.out.println(YELLOW + "How will you input your text?" + RESET);
        System.out.println("0: As a String (I'll type it)");
        System.out.println("1: As a File");

        return handler.getIntInput(0,1);
    }

    private void getLanguageChoice()
    {
        System.out.println(YELLOW + "\nWhat language would you like to check?" + RESET);
        System.out.println("0: English");
        System.out.println("1: Spanish");
        System.out.println("2: French");

        int language = handler.getIntInput(0,2);

        System.out.println("\nLoading dictionary... (This may take a few seconds)");

        checker = new SpellChecker(language);
    }

    private String getUserInput(int ifFile)
    {
        if (ifFile == 1)
        {
            System.out.println(YELLOW + "\nPlease type the name or path of the file to be checked: " + RESET);
            System.out.println("Hint: Try src/test/resources/english1.txt");
        }
        else
            System.out.println(YELLOW + "\nPlease input the string to be checked: " + RESET);

        return handler.getStrInput(ifFile);
    }

    private void printWrongWords(StringArray words, ArrayList<Integer> wrongs)
    {
        System.out.println(BLUE + "-------------------------------------" + RESET);

        if (wrongs.isEmpty())
            System.out.print(GREEN + "Congrats! There are no misspelled words!" + RESET);
        else
        {
            System.out.println(RED + "The wrongly spelt words are:" + RESET);

            for (int index : wrongs)
                System.out.print(words.get(index) + " ");
        }

        System.out.print(BLUE + "\n-------------------------------------" + RESET);
    }

    private void printSuggestions(String word, StringArray suggestions)
    {
        System.out.println(PURPLE + "\nSelect an alternative for the word " +
                           YELLOW +  word + PURPLE + ":" + RESET);

        if (suggestions.isEmpty()) suggestions.add(word);
        else suggestions.insert(0, word);

        System.out.println("0: Keep '" + word + "'");

        for (int c = 1; c < suggestions.size(); c++)
            System.out.println(c + ": " + suggestions.get(c));
    }

    private StringArray getCorrections(StringArray words, ArrayList<Integer> wrongs)
    {
        StringArray chosenOnes = new StringArray();

        for (int index : wrongs)
        {
            String word = words.get(index);
            StringArray suggestions = checker.getSuggestions(word);

            printSuggestions(word, suggestions);
            int input = handler.getIntInput(0, suggestions.size() - 1);

            chosenOnes.add(suggestions.get(input));
        }

        return chosenOnes;
    }

    private void applyCorrections(StringArray input, ArrayList<Integer> wrongs, StringArray chosenOnes)
    {
        int c = 0;
        for (int index : wrongs)
        {
            if (input.get(index).contains("\n"))
                input.set(index, (chosenOnes.get(c++) + "\n"));
            else
                input.set(index, chosenOnes.get(c++));
        }
    }

    private void outputCorrectVersion(StringArray output, int ifFile)
    {
        if (ifFile == 1)
        {
            handler.outputToFile(output);
            System.out.print(GREEN + "\nYour spell-checked file can now be viewed " +
                                     "at src/main/output/output.txt :)" + RESET);
        }
        else
        {
            System.out.print(GREEN + "\nHere's your spell-checked string! :>\n" + RESET);
            output.print();
        }
    }
}