import Links.*;

import java.io.IOException;

public class Main{
    public static void main (String[] args)throws IOException{
        Links links = new Links();
        links.loadFromFile("input1.html","input2.in");
        links.printFilesInTags("output1.out");
        links.printBadLinkes("output2.out");
        links.printUnmentioned("output3.out");
    }
}
