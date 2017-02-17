import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by kai-w on 17/02/17.
 */
public class Creator {
    String seed;
    int offset;

    String[] vowels = new String[]{"A","E","I","O","U"};
    HashMap<String, Integer> vowelValues = new HashMap<>();
    String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    HashMap<String, Integer> alphabetValues = new HashMap<>();

    public Creator(String seed, String offset) {
        this.seed = seed;
        this.offset = Integer.parseInt(offset);
    }

    public String create() {
        initValues();

        return "some return";
    }

    public void initValues() {
        // HashMap: vowel -> value
        for (int i=0; i<vowels.length; i++) {
            vowelValues.put(vowels[i], i+1+this.offset);
        }

        // HashMap: letter -> value
        for (int i=0; i<alphabet.length; i++) {
            alphabetValues.put(alphabet[i], i+1);
        }
        alphabet = null;
    }
}
