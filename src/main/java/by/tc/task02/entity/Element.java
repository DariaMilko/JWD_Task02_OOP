package by.tc.task02.entity;

import java.util.List;
import java.util.Map;

public class Element {
    
    private int nodeLevel;
    private String nodeName;
    private Map<String, String> nodeAttributes;
    private StringBuilder nodeData = new StringBuilder();
    private List<Element> childrenElements;
    
    public int getNodeLevel() {
        return nodeLevel;
    }
    
    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
    
    public String getNodeName() {
        return nodeName;
    }
    
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    
    public Map<String, String> getNodeAttributes() {
        return nodeAttributes;
    }
    
    public void setNodeAttributes(Map<String, String> nodeAttributes) {
        this.nodeAttributes = nodeAttributes;
    }
    
    public StringBuilder getNodeData() {
        return nodeData;
    }
    
    public void setNodeData(String nodeData) {
        String data2 = nodeData.trim();
        this.nodeData.append(data2);
    }
    
    public List<Element> getChildrenElements() {
        return childrenElements;
    }
    
    public void setChildrenElements(List<Element> childrenElements) {
        this.childrenElements = childrenElements;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null==o) return false;
        if (getClass()!=o.getClass()) return false;
        
        Element element = (Element) o;        
        if (nodeLevel != element.nodeLevel) return false;
        if (nodeName != null ? !nodeName.equals(element.nodeName) : element.nodeName != null) return false;
        if (nodeAttributes != null ? !nodeAttributes.equals(element.nodeAttributes) : element.nodeAttributes != null) return false;
        if (!nodeData.equals(element.nodeData)) return false;
        return childrenElements != null ? childrenElements.equals(element.childrenElements) : element.childrenElements == null;
        
    }
    
    public int hashCode() {
        int result = nodeLevel;
        result = 17 * result + (nodeName != null ? nodeName.hashCode() : 0);
        result = 17 * result + (nodeAttributes != null ? nodeAttributes.hashCode() : 0);
        result = 17 * result + nodeData.hashCode();
        result = 17 * result + (childrenElements != null ? childrenElements.hashCode() : 0);
        return result;
    }
    
    public String toString() {
        StringBuilder tabs = new StringBuilder();
        String indent = "\t";
        int tabsCounter = nodeLevel - 1;
        for (int i = 0; i < tabsCounter; i++) {
            tabs.append(indent);
        }        
        String childrenPrint = childrenPrint();
        return String.format("", tabs, nodeLevel, nodeName, nodeAttributes, nodeData, childrenPrint);
        
    }
    
    private String childrenPrint() {
        StringBuilder children = new StringBuilder();
        if (childrenElements != null) {
            childrenElements.forEach(children::append);
        }        
        return children.toString();
    }
}
