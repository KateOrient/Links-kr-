package Links;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Links{
    private List<String> links;
    private List<String> files;

    public Links(){
        links = new ArrayList<>();
        files = new ArrayList<>();
    }

    public void loadFromFile(String linksFile, String filesFile)throws IOException{
        Scanner scanner = new Scanner(new File(linksFile)).useDelimiter("\n");
        StringBuilder code = new StringBuilder();
        while(scanner.hasNext()){
            code.append(scanner.next());
        }
        Pattern pattern = Pattern.compile("<a\\s+href\\s*=\\s*\"[a-zA-Z0-9]+(.[a-zA-Z0-9]+)?\"\\s*>");
        Matcher matcher = pattern.matcher(code.toString());
        while (matcher.find()){
            links.add(matcher.group().replaceAll("\\s+"," ").toLowerCase());
        }
        scanner=new Scanner(new File(filesFile)).useDelimiter("\\s*;\\s*");
        while(scanner.hasNext()){
            files.add(scanner.next().toLowerCase().replaceAll("[.]{1}[a-z0-9]+",""));
        }
        scanner.close();
    }

    public void printFilesInTags(String fileName)throws IOException{
        Pattern pattern = Pattern.compile("(?<=<a href\\s?=\\s?\")[a-z0-9]+(?=[.]?[a-z0-9]*\">)");
        Matcher matcher;
        PrintStream ps = new PrintStream(fileName);
        List<String> names = new ArrayList<>();
        for (String item: links){
            matcher = pattern.matcher(item);
            if(matcher.find()){
                names.add(matcher.group());
            }
        }
        names.stream().sorted(new Comparator<String>(){
            @Override
            public int compare (String o1, String o2){
                return o1.length()-o2.length();
            }
        }).forEach(s->ps.println(s));
        ps.close();
    }

    public void printBadLinkes(String fileName)throws IOException{
        Pattern pattern = Pattern.compile("(?<=<a href\\s?=\\s?\")[a-z0-9]+(?=[.]?[a-z0-9]*\">)");
        Matcher matcher;
        PrintStream ps = new PrintStream(fileName);
        List<String> names = new ArrayList<>();
        for (String item: links){
            matcher = pattern.matcher(item);
            if(matcher.find()){
                names.add(matcher.group());
            }
        }
        int result = 0;
        for(String item:names){
            if(!files.contains(item)){
                result++;
            }
        }
        ps.println(result);
        ps.close();
    }

    public void printUnmentioned(String fileName)throws IOException{
        Pattern pattern = Pattern.compile("(?<=<a href\\s?=\\s?\")[a-z]+(?=[.]?[a-z]*\">)");
        Matcher matcher;
        PrintStream ps = new PrintStream(fileName);
        List<String> names = new ArrayList<>();
        for (String item: links){
            matcher = pattern.matcher(item);
            if(matcher.find()){
                names.add(matcher.group());
            }
        }
        for(String item:files){
            if(!names.contains(item)){
                ps.println(item);
            }
        }
    }


}
