package com.belperier.eu4m.exporter;

import com.belperier.eu4m.exporter.parser.TxtParser;

public class ExporterMain {


    public static void main(String[] args) {
        String txtAsJson = TxtParser.toJson("sample/missions/Brelar_Missions.txt");

        System.out.println("Hello !");
    }
}
