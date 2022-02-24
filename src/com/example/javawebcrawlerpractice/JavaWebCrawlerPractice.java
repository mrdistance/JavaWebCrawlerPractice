package com.example.javawebcrawlerpractice;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;


public class JavaWebCrawlerPractice {
    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/";

        crawl(1, url, new ArrayList<String>());     //Recursive function to go set number of levels deep into given url visiting all links
    }



    public static void crawl(int level, String url, ArrayList<String> visited) throws IOException {     //Crawler method
        if(level <= 5){                                                         //Number of recursive levels
            Document doc = request(url, visited);
            if(doc != null){
                for(Element link : doc.select("a[href]")) {             //Get all links on webpage
                    String next_link = link.absUrl("href");
                    if(!visited.contains(next_link)){                           //If haven't been to link, go to it
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> visited){                 //Helper Connection Method
        try{
            Connection con = Jsoup.connect(url);        //Connect to site
            Document doc = con.get();                   //Get document

            if(con.response().statusCode() == 200){     //request not rejected
                System.out.println("Link: " + url);
                System.out.println(doc.title());        //Print title of page
                visited.add(url);                       //track pages visited

                return doc;
            }
            return null;                                //return null if not accessible

        }catch(IOException e){
            return null;
        }
    }
}
