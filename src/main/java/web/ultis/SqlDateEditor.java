package web.ultis;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;

public class SqlDateEditor extends PropertyEditorSupport {
    private DateFormat dateFormat;

    public SqlDateEditor (final DateFormat dateFormat)
    {
      this.dateFormat = dateFormat;
    }

    @Override
    public void setAsText(String text) {
        try {
            setValue(new java.sql.Date(this.dateFormat.parse(text).getTime()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse date: " + e.getMessage());
        }
    }

    @Override
    public String getAsText() {
        java.sql.Date value = (java.sql.Date) getValue();
        return (value != null ? this.dateFormat.format(value) : "");
    }
}