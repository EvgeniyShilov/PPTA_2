package grammar.util;

import grammar.model.Grammar;
import grammar.model.GrammarType;
import grammar.model.Rule;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GrammarUtil {

    public static Grammar fromFile(String filename) throws IOException {
        List<String> rulesStrings = FileUtil.readFile(filename);
        List<Rule> rules = RuleParser.parseAll(rulesStrings);
        Set<Character> terminals = RuleAnalyzer.getTerminals(rules);
        Set<Character> nonTerminals = RuleAnalyzer.getNonTerminals(rules);
        return new Grammar(rules, terminals, nonTerminals);
    }

    public static GrammarType getType(Grammar grammar) {
        if (!isContextSensitive(grammar)) return GrammarType.TYPE_0;
        if (!isContextFree(grammar)) return GrammarType.CONTEXT_SENSITIVE;
        if (!isRegular(grammar)) return GrammarType.CONTEXT_FREE;
        return GrammarType.REGULAR;
    }

    private static boolean isContextSensitive(Grammar grammar) {
        for (Rule rule : grammar.getRules())
            if (rule.getLeft().length() > rule.getRight().length()) return false;
        return true;
    }

    private static boolean isContextFree(Grammar grammar) {
        for (Rule rule : grammar.getRules()) {
            List<Character> leftChars = StringUtil.splitToChars(rule.getLeft());
            if (leftChars.size() > 1 || grammar.getT().contains(leftChars.get(0))) return false;
        }
        return true;
    }

    private static boolean isRegular(Grammar grammar) {
        for (Rule rule : grammar.getRules()) {
            List<Character> rightChars = StringUtil.splitToChars(rule.getRight());
            int nonTerminalsCount = 0;
            for (Character rightChar : rightChars)
                if (grammar.getN().contains(rightChar)) nonTerminalsCount++;
            final int terminalsCount = rightChars.size() - nonTerminalsCount;
            if (terminalsCount != 1 || nonTerminalsCount > 1) return false;
        }
        return true;
    }
}
