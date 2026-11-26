package kunuz.uz.api.util;

import kunuz.uz.api.enums.Language;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LanguageUtil {


    public static Locale currentLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static Language currentLang() {
        return switch (currentLocale().getLanguage()) {
            case "ru" -> Language.RU;
            case "en" -> Language.EN;
            default -> Language.UZ;
        };
    }
}
