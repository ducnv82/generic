package generic.web.bean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public abstract class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private String theme = "redmond"; // default

	protected ApplicationContext applicationContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance());

	public Object getManagedBean(String beanName) {
		return getApplication().evaluateExpressionGet(getFacesContext(), "#{" + beanName + "}", Object.class);
	}
	
	public static Flash flashScope (){
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash(); 
		flash.setKeepMessages(true); 
		
		return flash;
	}

	public Application getApplication() {
		return getFacesContext().getApplication();
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public String getParameter(final String name) {
		return getRequest().getParameter(name);
	}


	public String getBundleName() {
		return getFacesContext().getApplication().getMessageBundle();
	}	
	
	public ResourceBundle getBundle() {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale(), classLoader);
	}

	public String getErrorText(final String key) {
		String message;

		try {
			// message = getBundle().getString(key);
			message = getApplication().getResourceBundle(getFacesContext(), "error_messages").getString(key);
		} catch (final java.util.MissingResourceException mre) {
			logger.warn("Missing key for '{}'", key);
			return "???" + key + "???";
		}

		return message;
	}

	public String getText(final String bundleName, final String key) {
		String message;
		try {
			message = getApplication().getResourceBundle(getFacesContext(), bundleName).getString(key);
		} catch (final java.util.MissingResourceException mre) {
			logger.warn("Missing key for '{}'", key);
			return "???" + key + "???";
		}

		return message;
	}

	public String getErrorText(final String key, final Object arg) {
		if (arg == null) {
			return getErrorText(key);
		}

		final MessageFormat form = new MessageFormat(getApplication().getResourceBundle(getFacesContext(), "error_messages").getString(key));

		if (arg instanceof String) {
			return form.format(new Object[] { arg });
		} else if (arg instanceof Object[]) {
			return form.format(arg);
		} else {
			logger.error("arg '{}' not String or Object[]", arg);

			return "";
		}
	}

	@SuppressWarnings("unchecked")
	protected void addMessage(final String key, final Object arg) {
		List<String> messages = (List<String>) getSession().getAttribute("messages");

		if (messages == null) {
			messages = new ArrayList<String>();
		}

		messages.add(getErrorText(key, arg));
		getSession().setAttribute("messages", messages);
	}


	protected void addMessage(final String key) {
		addMessage(key, null);
	}

	@SuppressWarnings("unchecked")
	protected void addError(final String key, final Object arg) {
		List<String> errors = (List<String>) getSession().getAttribute("errors");

		if (errors == null) {
			errors = new ArrayList<String>();
		}

		// if key contains a space, don't look it up, it's likely a raw message
		if (key.contains(" ") && arg == null) {
			errors.add(key);
		} else {
			errors.add(getErrorText(key, arg));
		}

		getSession().setAttribute("errors", errors);
	}

	protected void addError(final String key) {
		addError(key, null);
	}

	public boolean hasErrors() {
		return (getSession().getAttribute("errors") != null);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}

	protected ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext().getContext();
	}

	public String getTheme() {
		final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (params.containsKey("theme")) {
			theme = params.get("theme");
		}

		return theme;
	}

	public void setTheme(final String theme) {
		this.theme = theme;
	}	
}
