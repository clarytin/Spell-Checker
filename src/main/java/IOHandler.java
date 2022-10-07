package main.java;
import java.io.*;
import java.util.Scanner;

import static main.java.Colours.*;

public class IOHandler
{
    public int getIntInput(int lo, int hi)
    {
        Scanner input = new Scanner(System.in);

        while (true)
        {
            if (input.hasNextInt())
            {
                int ifFile = input.nextInt();

                if (lo <= ifFile && ifFile <= hi)
                    return ifFile;
            }
            System.out.println(RED + "Please only type a number between " + lo + " and " + hi + RESET);
            input.nextLine();
        }
    }

    public String getStrInput(int ifFilename)
    {
        Scanner input = new Scanner(System.in);
        String strInput = input.nextLine();

        while (ifFilename == 1)
        {
            if (new File(strInput).exists())
                break;
            else
            {
                System.out.println(RED + "File does not exist. Please retype file name." + RESET);
                strInput = input.nextLine();
            }
        }

        return strInput;
    }

    public void outputToFile(StringArray output)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/output/output.txt"));
            writer.write(output.get(0));

            for (int c = 1; c < output.size(); c++)
            {
                if (!output.get(c - 1).contains("\n"))
                    writer.append(" ");
                writer.append(output.get(c));
            }

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("IO error occurred.");
            e.printStackTrace();
        }
    }

    public StringArray cleanUpInput(StringArray input)
    {
        StringArray aux = new StringArray(input);

        for (int c = 0; c < aux.size(); c++)
            aux.set(c, input.get(c).replaceAll("[\\n\\p{Punct}&&[^']]", "").toLowerCase());

        return aux;
    }

    public StringArray extractWords(String in, int ifFile)
    {
        if (ifFile == 1)
            return extractWords(new File(in));
        return extractWords(in);
    }

    // A faster alternative to extractWords for use on text files with one word per line
    public StringArray extractFromDict(String filename)
    {
        File file = new File(filename);
        StringArray array = new StringArray();
        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
                array.addArray(this.extractWords(scanner.nextLine()));
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return array;
    }

    private StringArray extractWords(File file)
    {
        StringArray array = new StringArray();
        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                array.addArray(this.extractWords(scanner.nextLine()));
                int index = array.size() - 1;
                array.set(index, (array.get(index) + "\n"));
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return array;
    }

    private StringArray extractWords(String in)
    {
        StringArray array = new StringArray();

        Scanner sc = new Scanner(in);

        while (sc.hasNext())
            array.add(sc.next());

        sc.close();

        return array;
    }
}
