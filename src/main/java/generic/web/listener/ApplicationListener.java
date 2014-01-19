package generic.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class ApplicationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		SLF4JBridgeHandler.uninstall();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		SLF4JBridgeHandler.install();
	}

}

