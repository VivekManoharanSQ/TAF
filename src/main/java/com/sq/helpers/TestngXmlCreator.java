package com.sq.helpers;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestngXmlCreator {

    public class SuiteFileCreator {

        public static void main(String[] args) throws IOException {
            XmlSuite suite = new XmlSuite();
            suite.setName("Master suite");
            XmlTest xmlTest = new XmlTest();
            xmlTest.setName("Sample Test");
            List<XmlClass> xmlClasses = new ArrayList<>();
            XmlClass xmlClass = new XmlClass();
//            xmlClass.
//            List<String> files = new ArrayList<String>();
//            files.add("/Users/krmahadevan/githome/test-projects/ExperimentsWithWebDriverAndTestNG/demo-testng.xml");
//            files.add("/Users/krmahadevan/githome/test-projects/ExperimentsWithWebDriverAndTestNG/master-testng-enum.xml");
//            suite.setSuiteFiles(files );
//            System.out.println(suite.toXml());
//            FileWriter writer = new FileWriter(new File("MyMasterSuite.xml"));
//            writer.write(suite.toXml());
//            writer.flush();
//            writer.close();
//            System.out.println(new File("MyMasterSuite.xml").getAbsolutePath());
        }
    }
}
