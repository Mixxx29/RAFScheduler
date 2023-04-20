package rs.raf.scheduler.utils;

import java.util.regex.Pattern;

public class Validator {

    private final static String FORBIDDEN = "~#^|$%&*!:";

    public static boolean validateEmail(String email, StringBuilder errorMessage) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.append("E-Mail Invalid!");
            return false;
        }
        return true;
    }

    public static boolean validateUsername(String username, StringBuilder errorMessage) {
        if (username.length() < 3) {
            errorMessage.append("Username must have minimum 3 characters!");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password, StringBuilder errorMessage) {
        Pattern forbiddenPattern = Pattern.compile("["+ FORBIDDEN + "]");
        Pattern upperPattern = Pattern.compile("[A-Z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        if (password.length() < 5) {
            errorMessage.append("Password must have minimum 5 characters!");
            return false;
        } else if (forbiddenPattern.matcher(password).find()) {
            errorMessage.append("Password can't contain '"+ FORBIDDEN + "'");
            return false;
        } else if (!upperPattern.matcher(password).find()) {
            errorMessage.append("Password must contain one upper case character!");
            return false;
        } else if (!digitPattern.matcher(password).find()) {
            errorMessage.append("Password must contain one digit!");
            return false;
        }
        return true;
    }
}
