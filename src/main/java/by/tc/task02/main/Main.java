package by.tc.task02.main;

import by.tc.task02.entity.Element;
import by.tc.task02.service.DOMService;
import by.tc.task02.service.ServiceFactory;

public class Main {
    
    public static void main(String[] args) {
        
        ServiceFactory factory = ServiceFactory.getInstance();
        DOMService domService = factory.getDOMService();
        String filePath = "/task02.xml";
        Element dom = domService.getDOM(filePath);        
        PrintXML.print(dom);
    }
}
