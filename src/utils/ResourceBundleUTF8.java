
package utils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Describe your class
 */
public class ResourceBundleUTF8 {
    private final ResourceBundle bundle;

    public ResourceBundleUTF8(String bundle, Locale locale) {
        this.bundle = ResourceBundle.getBundle(bundle, locale);
    }

    public String getString(String key) {
        if (this.bundle.getLocale() == Locale.ENGLISH) {
            return this.bundle.getString(key);
        }
        return new String(this.bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
