
package com.acabra.tictactoe.utils;

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
        return this.bundle.getString(key);
    }
}
