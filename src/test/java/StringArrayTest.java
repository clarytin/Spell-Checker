package test.java;

import main.java.StringArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringArrayTest
{
    StringArray array = new StringArray();

    @BeforeEach
    public void setup()
    {
        array = new StringArray();

        array.add("MyString");
        array.add("Clarissa");
        array.add("ThisIsStringTwo");
        array.add("Banana");
        array.add("Nintendo");
        array.add("nintendo");
        array.add("H");
        array.add("H");
        array.add("null");
        array.add(null);
    }

    @Test
    @DisplayName("Adding strings creates matching array")
    public void addTest()
    {
        assertEquals(array.get(0),"MyString");
        assertEquals(array.get(1),"Clarissa");
        assertEquals(array.get(2),"ThisIsStringTwo");
        assertEquals(array.get(3),"Banana");
        assertEquals(array.get(4),"Nintendo");
        assertEquals(array.get(5),"nintendo");
        assertEquals(array.get(6),"H");
        assertEquals(array.get(7),"H");
        assertEquals(array.get(8),"null");
        assertNull(array.get(9));

    }

    @Test
    @DisplayName("Constructing from array should work")
    public void constructorTest()
    {
        StringArray array2 = new StringArray(array);

        assertEquals(array2.get(0),"MyString");
        assertEquals(array2.get(1),"Clarissa");
        assertEquals(array2.get(2),"ThisIsStringTwo");
        assertEquals(array2.get(3),"Banana");
        assertEquals(array2.get(4),"Nintendo");
        assertEquals(array2.get(5),"nintendo");
        assertEquals(array2.get(6),"H");
        assertEquals(array2.get(7),"H");
        assertEquals(array2.get(8),"null");
        assertNull(array2.get(9));
    }

    @Test
    @DisplayName("Getting string should return matching string")
    public void getTest()
    {
        assertEquals(array.get(2),"ThisIsStringTwo");
    }

    @Test
    @DisplayName("Getting null should return null if in array")
    public void getNullTest()
    {
        assertNull(array.get(9));
    }

    @Test
    @DisplayName("Size should return number of elements")
    public void sizeTest()
    {
        assertEquals(array.size(), 10);
    }

    @Test
    @DisplayName("Size of empty StringArray should be 0")
    public void sizeZeroTest()
    {
        StringArray array2 = new StringArray();
        assertEquals(array2.size(), 0);
    }

    @Test
    @DisplayName("Empty should return false if not empty")
    public void emptyTest()
    {
        assertFalse(array.isEmpty());
    }

    @Test
    @DisplayName("Set should change value at the index")
    public void setTest()
    {
        array.set(6, "NotNintendo");
        assertEquals(array.get(6), "NotNintendo");
    }

    @Test
    @DisplayName("Insert should add value at index")
    public void insertTest()
    {
        array.insert(3, "Orange");
        assertEquals(array.get(3), "Orange");
        assertEquals(array.get(4), "Banana");
    }

    @Test
    @DisplayName("Remove should delete value at index")
    public void removeTest()
    {
        array.remove(4);
        assertEquals(array.get(4), "nintendo");
    }

    @Test
    @DisplayName("Contains should return false if string not in array")
    public void containsTestFalse()
    {
        assertFalse(array.contains("Clary"));
    }

    @Test
    @DisplayName("Contains should return true if string is in array")
    public void containsTestTrue()
    {
        assertTrue(array.contains("Clarissa"));
    }

    @Test
    @DisplayName("ContainsMatchingCase should return false if string not in array")
    public void containsMatchingTestFalse()
    {
        assertFalse(array.containsMatchingCase("clarissa"));
    }

    @Test
    @DisplayName("ContainsMatchingCase should return false if string not in array")
    public void containsMatchingTestTrue()
    {
        assertTrue(array.containsMatchingCase("Clarissa"));
    }

    @Test
    @DisplayName("IndexOf should return -1 if string not in StringArray")
    public void indexOfNotInTest()
    {
        assertEquals(array.indexOf("Watermelon"), -1);
    }

    @Test
    @DisplayName("IndexOf should return index of first instance of string")
    public void indexOfTest()
    {
        assertEquals(array.indexOf("H"), 6);
    }

    @Test
    @DisplayName("IndexOfMatchingCase should return index of string")
    public void indexMatchingTest()
    {
        assertEquals(array.indexOfMatchingCase("Clarissa"), 1);
    }

    @Test
    @DisplayName("IndexOfMatchingCase should return -1 if no strings match")
    public void indexMatchingNotInArrayTest()
    {
        assertEquals(array.indexOfMatchingCase("clarissa"), -1);
    }
}
