package main.java;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// algorithm inspired by http://norvig.com/spell-correct.html

public class SpellChecker
{
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final StringArray dict; // dictionary sorted alphabetically
    private final StringArray freq; // words sorted by frequency

    private StringArray stringPermutes;

    public SpellChecker(int language)
    {
        IOHandler handler = new IOHandler();

        switch (language)
        {
            case 1 -> {
                this.dict = handler.extractFromDict("src/main/resources/spanish_words.txt");
                this.freq = handler.extractFromDict("src/main/resources/spanish_freq.txt");
            }
            case 2 -> {
                this.dict = handler.extractFromDict("src/main/resources/french_words.txt");
                this.freq = handler.extractFromDict("src/main/resources/french_freq.txt");
            }
            default -> {
                this.dict = handler.extractFromDict("src/main/resources/english_words.txt");
                this.freq = handler.extractFromDict("src/main/resources/english_freq.txt");
            }
        }
    }

    public ArrayList<Integer> getMisspellings(StringArray words)
    {
        ArrayList<Integer> wrongs = new ArrayList<>();
        for (int c = 0; c < words.size(); c++)
        {
            if (!dict.sortedContains(words.get(c)))
                wrongs.add(c);
        }
        return wrongs;
    }

    public StringArray getSuggestions(String word)
    {
        stringPermutes = new StringArray();
        applyEdits(word);

        StringArray oneEditAway = getValidPermutes(stringPermutes);
        if (oneEditAway.size() >= 3) return new StringArray(oneEditAway, 3);

        StringArray twoEditsAway = applyAnotherEdit();
        oneEditAway.addArray(getValidPermutes(twoEditsAway));

        return new StringArray(oneEditAway, 3);
    }

    private StringArray getValidPermutes(StringArray array)
    {
        StringArray aux;

        aux = removeMisspellings(array);
        aux.removeDuplicates();
        aux = sortByFrequency(aux);

        return aux;
    }

    private void applyEdits(String word)
    {
        int length = word.length();

        for (int c = 0; c < length; c++)
        {
            String sub1 = word.substring(0, c);
            String sub2 = word.substring(c);

            addInsertions(sub1, sub2);

            if (length - c > 1)
            {
                addDeletions(sub1, sub2);
                addReplacements(sub1, sub2);
            }

            if (length - c > 2)
                addTranspositions(sub1, sub2);
        }
    }

    private StringArray applyAnotherEdit()
    {
        int initSize = stringPermutes.size();

        for (int c = 0; c < initSize; c++)
            applyEdits(stringPermutes.get(c));

        return stringPermutes.getSubArray(initSize, stringPermutes.size() - 1);
    }

    private void addInsertions(String sub1, String sub2)
    {
        for (int j = 0; j < 26; j++)
            stringPermutes.add(sub1 + alphabet.charAt(j) + sub2);
    }

    private void addDeletions(String sub1, String sub2)
    {
        stringPermutes.add(sub1 + sub2.substring(1));
    }

    private void addReplacements(String sub1, String sub2)
    {
        for (int j = 0; j < 26; j++)
            stringPermutes.add(sub1 + alphabet.charAt(j) + sub2.substring(1));
    }

    private void addTranspositions(String sub1, String sub2)
    {
        stringPermutes.add(sub1 + sub2.charAt(1) + sub2.charAt(0) + sub2.substring(2));
    }

    private StringArray removeMisspellings(StringArray words)
    {
        StringArray rights = new StringArray();

        for (int c = 0; c < words.size(); c++)
        {
            if (dict.sortedContains(words.get(c)))
                rights.add(words.get(c));
        }

        return rights;
    }

    private StringArray sortByFrequency(StringArray array)
    {
        StringArray aux = new StringArray();
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int c = 0; c < array.size(); c++)
        {
            String word = array.get(c);
            map.put(getFrequency(word), word);
        }
        for (Map.Entry<Integer, String> entry : map.entrySet())
            aux.add(entry.getValue());

        return aux;
    }

    private int getFrequency(String word)
    {
        int frequency = freq.indexOf(word);
        if (frequency == -1)
            return 100000;
        return frequency;
    }
}

