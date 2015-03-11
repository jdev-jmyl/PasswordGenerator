package jesus.my.lord.passgen;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordGenerator {

    private final String lowerString;
    private final String upperString;
    private final String numberString;
    private final String symbolString;
    private String combinedStrings;
    private char parsedChar;
    
    private Pattern pattern;
    private Matcher matcher;
    private final StringBuilder passwordPattern ;
    private final Random random;

    public PasswordGenerator() {
        
        random = new Random();
        passwordPattern = new StringBuilder();
        lowerString = "abcdefghijkmnpqrstuvwxyz";
        upperString = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        numberString = "1234567890";
        symbolString = "`~!#$%^&*()-_=+[{]};:,<.>?";
        combinedStrings = "";
        parsedChar = ' ';
    }

    private int setRandom(int input) {
        int number = random.nextInt(input);
        return number;
    }

    public String makePassword(int numChars, boolean lowerSelected, boolean upperSelected,
            boolean numbersSelected, boolean symbolsSelected) throws Exception {
        String password = "";
        if (numChars >= 1) {
            passwordPattern.setLength(0);
            passwordPattern.append("(");
            if (lowerSelected) {
                combinedStrings += lowerString;
                passwordPattern.append("(?=.*[a-z])");
            }
            if (upperSelected) {
                combinedStrings += upperString;
                passwordPattern.append("(?=.*[A-Z])");
            }
            if (numbersSelected) {
                combinedStrings += numberString;
                passwordPattern.append("(?=.*\\d)");
            }
            if (symbolsSelected) {
                combinedStrings += symbolString;
                passwordPattern.append("(?=.*[")
                        .append(symbolString).append("])");
            }
            passwordPattern.append(".{").append(numChars).append(",")
                    .append(numChars+1).append("})");
//        System.out.println("Pattern =" + passwordPattern);
            password = getPassword(numChars);
//        System.out.println("password " + password);
            while (!validate(password)) {
                password = getPassword(numChars);
//            System.out.println("try generate new password " + password);
            }
        } else {
            throw new Exception("The numChars has to be more than 1. numChars >=1");
        }
        return password;
    }

    private String getPassword(int numChars) {
        StringBuilder password = new StringBuilder("");
        int charCount = combinedStrings.length();
        for (int j = 0; j < numChars; j++) {
            parsedChar = combinedStrings.charAt(setRandom(charCount));
            password.append(parsedChar);
        }
        return password.toString();
    }

    private boolean validate(String password) {
        pattern = Pattern.compile(passwordPattern.toString());
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
