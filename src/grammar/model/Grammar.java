package grammar.model;

import java.util.List;
import java.util.Set;

public class Grammar {

    private String S;
    private List<Rule> rules;
    private Set<Character> T;
    private Set<Character> N;

    public Grammar(List<Rule> rules, Set<Character> T, Set<Character> N) {
        this.rules = rules;
        this.T = T;
        this.N = N;
        this.S = "S";
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Set<Character> getT() {
        return T;
    }

    public Set<Character> getN() {
        return N;
    }

    public String getS() {
        return S;
    }
}
