package com.example.demo.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class FileWriter {
    @Autowired
    private PrintService printService;

    private static String log (String msg, String...vals){
        return String.format(msg, vals);
    }

    public void writeById(Document doc, String id) {
        String content = doc.getElementById(id).outerHtml();
        Document document = Jsoup.parse(content);

        BufferedWriter writer = null;

        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog);

            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new java.io.FileWriter(logFile));
            writer.write(printService.printSpellsById(doc, id));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void write(Document doc, String cssQuery) {
        log(doc.title());

        String str = doc.title() + "\n";

        Elements tables = doc.select(cssQuery);

        BufferedWriter writer = null;

        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog);

            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new java.io.FileWriter(logFile));
            for (Element element : tables) {
                str += log("%s\n\t%s\n", element.attr("title"), element.absUrl("href"));
            }
            writer.write(str);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
