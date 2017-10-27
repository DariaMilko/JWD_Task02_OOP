package by.tc.task02.main;

import by.tc.task02.entity.Element;

class PrintXML {    
    private static final String NO_INFO_IN_FILE = "Receiving file information error";    
    static void print(Element element) {
        if (element != null) {
            System.out.println(element);
        } else {
            System.out.println(NO_INFO_IN_FILE);
        }
    }
}
