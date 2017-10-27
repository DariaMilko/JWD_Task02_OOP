package by.tc.task02.service.impl;

import by.tc.task02.dao.DomDAO;
import by.tc.task02.dao.DAOFactory;
import by.tc.task02.entity.Element;
import by.tc.task02.service.DOMService;

public class DOMServiceImpl implements DOMService {

    public Element getDOM(String filePath) {
        DAOFactory factory = DAOFactory.getInstance();
        DomDAO domDAO = factory.getDomDAO();
        Element dom = domDAO.getDOM(filePath);
        return dom;
    }


}