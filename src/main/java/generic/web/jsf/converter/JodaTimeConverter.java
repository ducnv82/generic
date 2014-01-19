package generic.web.jsf.converter;

import generic.utils.DateUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JodaTimeConverter implements Converter {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String DATE_PATTERN = "datePattern";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if (StringUtils.isBlank(value)) {
				// context.addMessage(null, new
				// FacesMessage(FacesMessage.SEVERITY_ERROR, "Required field",
				// null));
				return null;
			}
			final String datePattern = (String) component.getAttributes().get("pattern");
			Object onlyDate = component.getAttributes().get("onlyDate");

			if (onlyDate != null) {
				return DateUtil.toLocalDate(value, datePattern);
			}
			return DateUtil.toLocalDateTime(value, datePattern);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		String pattern = (String) component.getAttributes().get("pattern");
		Object onlyDate = component.getAttributes().get("onlyDate");

		if (onlyDate != null) {
			LocalDate localDate = (LocalDate) value;
			return localDate.toString(pattern);
		}

		final LocalDateTime localDateTime = (LocalDateTime) value;
		return localDateTime.toString(pattern);
	}
}
