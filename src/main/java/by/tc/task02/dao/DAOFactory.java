package by.tc.task02.dao;

import by.tc.task02.dao.impl.XmlParserDao;

public final class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();

    private final DomDAO domDAO = new XmlParserDao();

    private DAOFactory() {
    }

    public DomDAO getDomDAO() {
        return domDAO;
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }
}
