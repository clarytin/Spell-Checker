/*
    String-Array Class
    by Clarissa Sandejas
    8th February 2021
*/

package main.java;
import java.util.Arrays;

public class StringArray
{
    private String[] strArray;
    private int strArraySize;

    public StringArray()
    {
        this.strArray = new String[128];
        this.strArraySize = 0;
    }

    public StringArray(StringArray a)
    {
        this.strArraySize = a.size();
        this.strArray = Arrays.copyOf(a.getArray(), (a.size() * 2));
    }

    public StringArray(StringArray a, int maxSize)
    {
        int size = Math.min(a.size(), maxSize);
        this.strArraySize = size;
        this.strArray = Arrays.copyOf(a.getArray(), Math.max(128, (size * 2)));
    }

    public int size()
    {
        return strArraySize;
    }

    public boolean isEmpty()
    {
        return strArraySize == 0;
    }

    public String get(int index)
    {
        if (index >= strArraySize || index < 0)
            return null;

        return strArray[index];
    }

    public void set(int index, String s)
    {
        if (index >= strArraySize || index < 0)
            return;

        strArray[index] = s;
    }

    public void add(String s)
    {
        strArray[strArraySize] = s;
        increaseSize();
    }

    public void insert(int index, String s)
    {
        if (index >= strArraySize || index < 0)
            return;

        System.arraycopy(strArray, index, this.strArray, index + 1, strArraySize - index);

        set(index, s);
        increaseSize();
    }

    public void remove(int index)
    {
        if (index >= strArraySize || index < 0)
            return;

        if (strArraySize - 1 - index >= 0)
            System.arraycopy(strArray, index + 1, this.strArray, index, strArraySize - 1 - index);

        decreaseSize();
    }

    public boolean contains(String s)
    {
        if (s == null)
            return getNullIndex() != -1;

        return getIndex(s, 0) != -1;
    }

    public boolean containsMatchingCase(String s)
    {
        if (s == null)
            return getNullIndex() != -1;

        return getIndex(s, 1) != -1;
    }

    public int indexOf(String s)
    {
        if (s == null)
            return getNullIndex();

        return getIndex(s, 0);
    }

    public int indexOfMatchingCase(String s)
    {
        if (s == null)
            return getNullIndex();

        return getIndex(s, 1);
    }

    public void removeDuplicates()
    {
        StringArray aux = new StringArray();
        for (int c = 0; c < strArraySize; c++)
        {
            if (!aux.contains(strArray[c]))
                aux.add(strArray[c]);
        }
        this.strArray = aux.strArray;
        this.strArraySize = aux.strArraySize;
    }

    // Don't change this to overload the add() method or else add() can't accept nulls
    public void addArray(StringArray array)
    {
        for (int c = 0; c < array.size(); c++)
            this.add(array.get(c));
    }

    // Only for use in sorted arrays
    public boolean sortedContains(String word)
    {
        int lo = 0;
        int hi = strArraySize - 1;

        while (hi >= lo)
        {
            int mid = lo + ((hi - lo) / 2);
            int eqVal = word.compareToIgnoreCase(strArray[mid]);

            if (eqVal < 0)
                hi = mid - 1;
            else if (eqVal > 0)
                lo = mid + 1;
            else
                return true;
        }
        return false;
    }

    public StringArray getSubArray(int first, int last)
    {
        StringArray aux = new StringArray();

        for (int c = first; c <= last; c++)
            aux.add(this.get(c));

        return aux;
    }

    public void print()
    {
        for (int c = 0; c < strArraySize; c++)
            System.out.print(strArray[c] + " ");
        System.out.print("\n");
    }

    private void increaseSize()
    {
        strArraySize++;

        if (strArraySize == strArray.length)
            this.strArray = Arrays.copyOf(strArray, (int) (strArraySize * 1.3));
    }

    private void decreaseSize()
    {
        strArraySize--;

        if (strArraySize < (0.35 * strArray.length))
            this.strArray = Arrays.copyOf(strArray, strArray.length / 2);
    }

    // Type == 0: ignore case
    private int getIndex(String s, int type)
    {
        for (int c = 0; c < strArraySize; c++)
        {
            if (strArray[c] == null || strArray[c].length() == 0)
                continue;

            int eqVal = (type == 0) ? strArray[c].compareToIgnoreCase(s)
                                    : strArray[c].compareTo(s);

            if (eqVal == 0)
                return c;
        }
        return -1;
    }

    private int getNullIndex()
    {
        for (int c = 0; c < strArraySize; c++)
        {
            if (strArray[c] == null)
                return c;
        }
        return -1;
    }

    private String[] getArray()
    {
        return strArray;
    }
}