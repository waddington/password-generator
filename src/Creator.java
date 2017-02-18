import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kai-w on 17/02/17.
 */
public class Creator {
    String seed;
    int offset;

    ArrayList<String> passwordAL = new ArrayList<>();
    String password;

    boolean seedError = false;
    boolean offsetError = false;

    private String[] vowels = new String[]{"A","E","I","O","U"};
    private HashMap<String, Integer> vowelValues = new HashMap<>();
    private String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0"};
    private HashMap<String, Integer> alphabetValues = new HashMap<>();
    private String[] symbols = new String[]{"!","£","$","^","&","*","@","~","?","#"};
    private HashMap<String, Integer> symbolValues = new HashMap<>();


    public Creator(String seed, String offset) {
        if (seed != null) {
            this.seed = seed.toLowerCase();
        } else {
            seedError = true;
        }

        try {
            this.offset = Integer.parseInt(offset);
        } catch (NumberFormatException e) {
            offsetError = true;
        }
    }

    protected String create() {
        checkErrors();
        initValues();

        vowelShift();

        stringifyPassword();

        return this.password;
    }

    private void checkErrors() {
        if (seedError) {
            System.out.println("Please enter a valid seed.");
        }
        if (offsetError) {
            System.out.println("Please enter a valid offset.");
        }

        if (seedError || offsetError) {
            System.exit(99);
        }
    }

    private void initValues() {
        // Seed to array
        for (int i=0; i<seed.length(); i++) {
            passwordAL.add(Character.toString(seed.charAt(i)));
        }

        // HashMap: vowel -> value
        for (int i=0; i<vowels.length; i++) {
            vowelValues.put(vowels[i].toLowerCase(), i+1+(offset%vowels.length));
        }
        vowels = null;

        // HashMap: letter -> value
        for (int i=0; i<alphabet.length; i++) {
            alphabetValues.put(alphabet[i].toLowerCase(), i+1+(offset%alphabet.length));
        }
        alphabet = null;

        // HashMap: symbol -> value
        for (int i=0; i<symbols.length; i++) {
            symbolValues.put(symbols[i].toLowerCase(), i+1+(offset%symbols.length));
        }
        symbols = null;
    }

    private void vowelShift() {

        ArrayList<String> tempVowels = new ArrayList<>();
        int numVowels = 0;

        // Moving vowels to front of ArrayList
        for (int i=0; i<passwordAL.size(); i++) {
            if (vowelValues.containsKey(passwordAL.get(i))) {
                String tempChar = passwordAL.get(i);
                passwordAL.remove(i);
                tempVowels.add(tempChar);
                passwordAL.add(numVowels, tempChar);
                numVowels++;
            }
        }

        // Removing vowels
        for (int i=0; i<numVowels; i++) {
            passwordAL.remove(0);
        }

        // Sorting vowels into alphabetical order
        char[] tempTempVowels = new char[tempVowels.size()];
        for (int i=0; i<tempVowels.size(); i++) {
            tempTempVowels[i] = tempVowels.get(i).charAt(0);
        }
        Arrays.sort(tempTempVowels);

        // Adding sorted vowels back into ArrayList
        for (int i=tempTempVowels.length-1; i>=0; i--) {
            passwordAL.add(0, Character.toString(tempTempVowels[i]));
        }
    }



    private void stringifyPassword() {
        password = String.join("", passwordAL);
    }
}
