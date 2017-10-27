package by.tc.task02.dao.impl;

import by.tc.task02.dao.DomDAO;
import by.tc.task02.entity.Element;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParserDao implements DomDAO {
	
    private String file;
    private int nodeLevel = 0;    
    private final Stack<Element> openedElements = new Stack<>();
    private final Stack<Element> closedElements = new Stack<>(); 
    
    private static final String OPEN_TAG = "<[^/][^<>]*>";
    private static final String CLOSE_TAG = "</[^<>]+>";
    private static final String TAG_NAME = "(</?)([^\\s/]+)(\\s*[^<>]*)>";
    private static final String ATTRIBUTE = "(\\s*)([^\\s]+)=\"([^\\s]+)\"";
    
    private static final Pattern OPEN_TAG_PATTERN = Pattern.compile(OPEN_TAG);
    private static final Pattern CLOSE_TAG_PATTERN = Pattern.compile(CLOSE_TAG);
    private static final Pattern TAG_NAME_PATTERN = Pattern.compile(TAG_NAME);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile(ATTRIBUTE);
    
    private final Matcher closeTagMatcher = CLOSE_TAG_PATTERN.matcher("");
    private final Matcher nameMatcher = TAG_NAME_PATTERN.matcher("");      
    
    public Element getDOM(String filePath) {
        file = xmlFileReader(filePath);        
        parseXmlByTags();        
        Element dom = closedElements.pop();        
        return dom;
    }
    
    private String xmlFileReader(String filePath) {
        URL resourceURL = XmlParserDao.class.getResource(filePath);
        BufferedReader reader = null;        
        StringBuilder xmlFile = new StringBuilder();        
        try {
            reader = new BufferedReader(new InputStreamReader(resourceURL.openStream()));            
            while (reader.ready()) {
                String line = reader.readLine();
                xmlFile.append(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("No file in directory.");
        } catch (IOException e) {
            System.err.println("I/O exception is found.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error while closing file.");
            }
        }
        return xmlFile.toString();
    }
    
    private void parseXmlByTags() {
        Matcher tagMatcher = OPEN_TAG_PATTERN.matcher(file);
        int previousTag = 0;
        while (tagMatcher.find(previousTag)) {
            int currentTag = tagMatcher.start();            
            if (previousTag < currentTag) {
                tagData(previousTag, currentTag);
            }            
            previousTag = tagMatcher.end();
            String tag = tagMatcher.group();
            tagTypeReader(tag);
        }
    }
        
    private void tagTypeReader(String tag) {
        if (closeTagMatcher.matches()) {
            closeTag(tag);
        } else {
            openTag(tag);
        }
    }
    
    private void openTag(String tag) {
        ++nodeLevel;        
        Element element = new Element();
        element.setNodeLevel(nodeLevel);
        elementName(element, tag);
        elementAttributes(element, tag);        
        if (closeTagMatcher.matches()) {
            closedElements.push(element);
            --nodeLevel;
        } else {
            openedElements.push(element);
        }
    }
    
    private void closeTag(String tag) {
        --nodeLevel;        
        Element currentElement = openedElements.pop();        
        if (!closedElements.isEmpty()) {
            Element previousElement = closedElements.peek();            
            int currentElementLevel = currentElement.getNodeLevel();
            int previousElementLevel = previousElement.getNodeLevel();            
            if (currentElementLevel < previousElementLevel) {
                removeClosedElements(currentElement);
            }
        }        
        closedElements.push(currentElement);
    }
    
    private void removeClosedElements(Element currentElement) {
        List<Element> childrenElements = new ArrayList<>();
        int currentLevel = currentElement.getNodeLevel();        
        Iterator<Element> iterator = closedElements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();            
            if (currentLevel < element.getNodeLevel()) {
                childrenElements.add(element);
                iterator.remove();
            }
        }        
        currentElement.setChildrenElements(childrenElements);
    }
    
    private void elementName(Element element, String tag) {
        if (nameMatcher.find()) {
            String name = nameMatcher.group(2);
            element.setNodeName(name);
        }
    }
    
    private void elementAttributes(Element element, String tag) {
        Matcher attributeMatcher = ATTRIBUTE_PATTERN.matcher(tag);        
        Map<String, String> attributes = new HashMap<>();
        while (attributeMatcher.find()) {
            String attributeName = attributeMatcher.group(2);
            String attributeValue = attributeMatcher.group(3);
            attributes.put(attributeName, attributeValue);
        }        
        element.setNodeAttributes(attributes);        
    }
    
    private void tagData(int previousTag, int currentTag) {
        String tagData = file.substring(previousTag, currentTag);        
        Element element = openedElements.peek();
        if (element != null) {
            element.setNodeData(tagData);
        }
    }
}
