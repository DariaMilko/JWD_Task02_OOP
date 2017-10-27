package by.tc.task02.service;

import by.tc.task02.service.impl.DOMServiceImpl;

public final class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private final DOMService DOMService = new DOMServiceImpl();	
	private ServiceFactory() {}
	public DOMService getDOMService() {
		return DOMService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

}
