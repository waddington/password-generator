import sun.text.IntHashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * Created by kai-w on 17/02/17.
 */
public class Creator {
    String seed;
    int offset;

    private ArrayList<String> passwordAL = new ArrayList<>();
    private String password;

    private boolean seedError = false;
    private boolean offsetError = false;

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
        consonentOrder();

        vowelsToNumbers();
        numberShift();

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

    private void consonentOrder() {
        // Find start of consonents
        int j = 0;
        while (vowelValues.containsKey(passwordAL.get(j))) {
            j++;
        }

        // Move consonents to seperate ArrayList
        ArrayList<String> tempCons = new ArrayList<>();
        int tempSize = passwordAL.size();
        for (int i=j; i<tempSize; i++) {
            if (!vowelValues.containsKey(passwordAL.get(j))) {
                tempCons.add(passwordAL.get(j));
                passwordAL.remove(j);
            }
        }

        // ArrayList to char[] and sort
        char[] tempTempCons = new char[tempCons.size()];
        for (int i=0; i<tempCons.size(); i++) {
            tempTempCons[i] = tempCons.get(i).charAt(0);
        }
        Arrays.sort(tempTempCons);

        // Added sorted consonents back into password
        for (int i=0; i<tempTempCons.length; i++) {
            passwordAL.add(Character.toString(tempTempCons[i]));
        }

    }

    private void vowelsToNumbers() {
        for (int i=0; i<passwordAL.size(); i++) {
            if (vowelValues.containsKey(passwordAL.get(i))) {
                passwordAL.set(i, Integer.toString(vowelValues.get(passwordAL.get(i))));
            }
        }
    }

    private void numberShift() {
        // Numbers into ArrayList
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=0; i<passwordAL.size(); i++) {
            try {
                nums.add(Integer.parseInt(passwordAL.get(i)));
            } catch (NumberFormatException e) {

            }
        }

        // Increment numbers
        int first = nums.get(0);
        for (int i=0; i<nums.size(); i++) {
            if (i == nums.size()-1) {
                nums.set(i, nums.get(i)+first);
            } else {
                nums.set(i, nums.get(i)+nums.get(i+1));
            }
        }

        // Add numbers back into password ArrayList
        for (int i=0; i<nums.size(); i++) {
            passwordAL.set(i, Integer.toString(nums.get(i)));
        }
    }



    private void stringifyPassword() {
        password = String.join("", passwordAL).replaceAll("\\s+","");
    }
}
