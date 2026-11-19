import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class main {

    // Run the action and report the simple name of the exception thrown ("none" if nothing was thrown).
    private static String thrown(Runnable action) {
        try {
            action.run();
            return "none";
        } catch (Throwable t) {
            return t.getClass().getSimpleName();
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        final Pattern p = Pattern.compile("(?<word>a+)(b)?");
        Matcher m = p.matcher("xxaa ab");

        // ===== Pattern metadata =====
        errorCount += Checkers.checker("pattern() text", "(?<word>a+)(b)?", p.pattern());
        errorCount += Checkers.checker("toString() text", "(?<word>a+)(b)?", p.toString());
        errorCount += Checkers.checker("flags() default", 0, p.flags());
        errorCount += Checkers.checker("groupCount()", 2, m.groupCount());

        // ===== First match (original test) =====
        errorCount += Checkers.checker("find #1", true, m.find());
        errorCount += Checkers.checker("find #1 start()", 2, m.start());
        errorCount += Checkers.checker("find #1 end()", 4, m.end());
        errorCount += Checkers.checker("find #1 group(\"word\")", "aa", m.group("word"));
        errorCount += Checkers.checker("find #1 group()", "aa", m.group());
        errorCount += Checkers.checker("find #1 group(0)", "aa", m.group(0));
        errorCount += Checkers.checker("find #1 group(1)", "aa", m.group(1));
        errorCount += Checkers.checker("find #1 group(2) is null", (Object) null, (Object) m.group(2));
        errorCount += Checkers.checker("find #1 start(2) unmatched", -1, m.start(2));
        errorCount += Checkers.checker("find #1 end(2) unmatched", -1, m.end(2));
        errorCount += Checkers.checker("find #1 start(\"word\")", 2, m.start("word"));
        errorCount += Checkers.checker("find #1 end(\"word\")", 4, m.end("word"));

        // ===== Subsequent matches (original test) =====
        errorCount += Checkers.checker("find #2", true, m.find());
        errorCount += Checkers.checker("find #2 start()", 5, m.start());
        errorCount += Checkers.checker("find #2 end()", 7, m.end());
        errorCount += Checkers.checker("find #2 group()", "ab", m.group());
        errorCount += Checkers.checker("find #2 group(\"word\")", "a", m.group("word"));
        errorCount += Checkers.checker("find #2 group(2)", "b", m.group(2));
        errorCount += Checkers.checker("find #2 start(2)", 6, m.start(2));
        errorCount += Checkers.checker("find #2 end(2)", 7, m.end(2));
        errorCount += Checkers.checker("find #3 (none left)", false, m.find());

        // ===== Reset and matches() (original test) =====
        errorCount += Checkers.checker("reset/matches aaab", true, m.reset("aaab").matches());
        errorCount += Checkers.checker("reset/matches aaab group()", "aaab", m.group());
        errorCount += Checkers.checker("reset/matches aaab group(1)", "aaa", m.group(1));
        errorCount += Checkers.checker("reset/matches aaab group(2)", "b", m.group(2));
        errorCount += Checkers.checker("matches aaa", true, m.reset("aaa").matches());
        errorCount += Checkers.checker("matches aaa group(2) is null", (Object) null, (Object) m.group(2));
        errorCount += Checkers.checker("matches aaabc (trailing junk)", false, m.reset("aaabc").matches());
        errorCount += Checkers.checker("matches empty string", false, m.reset("").matches());

        // ===== lookingAt() =====
        errorCount += Checkers.checker("lookingAt aaabc", true, m.reset("aaabc").lookingAt());
        errorCount += Checkers.checker("lookingAt aaabc end()", 4, m.end());
        errorCount += Checkers.checker("lookingAt baaa", false, m.reset("baaa").lookingAt());

        // ===== find(int) =====
        errorCount += Checkers.checker("find(3)", true, m.reset("xxaa ab").find(3));
        errorCount += Checkers.checker("find(3) start()", 3, m.start());
        errorCount += Checkers.checker("find(3) group(1)", "a", m.group(1));
        errorCount += Checkers.checker("find(7) at end", false, m.reset("xxaa ab").find(7));
        errorCount += Checkers.checker("find(-1) throws", "IndexOutOfBoundsException",
                thrown(() -> p.matcher("xxaa ab").find(-1)));

        // ===== region() =====
        m.reset("xxaa ab").region(4, 7);
        errorCount += Checkers.checker("region(4,7) regionStart()", 4, m.regionStart());
        errorCount += Checkers.checker("region(4,7) regionEnd()", 7, m.regionEnd());
        errorCount += Checkers.checker("region(4,7) find", true, m.find());
        errorCount += Checkers.checker("region(4,7) start()", 5, m.start());

        // ===== hitEnd() =====
        m.reset("aaa");
        errorCount += Checkers.checker("hitEnd after matches aaa", true, m.matches() && m.hitEnd());

        // ===== Replacement =====
        errorCount += Checkers.checker("replaceAll $1 (original)", "<aaa>", m.reset("aaa").replaceAll("<$1>"));
        errorCount += Checkers.checker("replaceAll ${word}", "<aaa>", m.reset("aaa").replaceAll("<${word}>"));
        errorCount += Checkers.checker("replaceAll with unmatched $2", "xx[aa|] [a|b]",
                m.reset("xxaa ab").replaceAll("[$1|$2]"));
        errorCount += Checkers.checker("replaceFirst", "xx<aa> ab", m.reset("xxaa ab").replaceFirst("<$1>"));
        errorCount += Checkers.checker("replaceAll no match", "xyz", m.reset("xyz").replaceAll("<$1>"));
        errorCount += Checkers.checker("replaceAll Function", "xxAA A",
                m.reset("xxaa ab").replaceAll(r -> r.group(1).toUpperCase()));
        errorCount += Checkers.checker("quoteReplacement", "\\$1", Matcher.quoteReplacement("$1"));
        errorCount += Checkers.checker("replaceAll literal $", "$1", m.reset("aaa").replaceAll(Matcher.quoteReplacement("$1")));
        errorCount += Checkers.checker("replaceAll bad group ref throws", "IndexOutOfBoundsException",
                thrown(() -> p.matcher("aaa").replaceAll("$9")));

        // ===== appendReplacement / appendTail =====
        StringBuilder sb = new StringBuilder();
        m.reset("xxaa ab!");
        while (m.find())
            m.appendReplacement(sb, "<$1>");
        m.appendTail(sb);
        errorCount += Checkers.checker("appendReplacement/appendTail", "xx<aa> <a>!", sb.toString());

        // ===== results() and toMatchResult() =====
        errorCount += Checkers.checker("results().count()", 2L, m.reset("xxaa ab").results().count());
        MatchResult mr = m.reset("xxaa ab").find() ? m.toMatchResult() : null;
        errorCount += Checkers.checker("toMatchResult not null", true, mr != null);
        m.find();   // advance the matcher; the snapshot must not change
        errorCount += Checkers.checker("toMatchResult snapshot start()", 2, mr.start());
        errorCount += Checkers.checker("toMatchResult snapshot group()", "aa", mr.group());

        // ===== Zero-width matches =====
        Matcher z = Pattern.compile("a*").matcher("baac");
        int zeroCount = 0;
        StringBuilder zs = new StringBuilder();
        while (z.find()) {
            zeroCount++;
            zs.append(z.start()).append('-').append(z.end()).append(' ');
        }
        errorCount += Checkers.checker("a* zero-width match count", 4, zeroCount);
        errorCount += Checkers.checker("a* zero-width spans", "0-0 1-3 3-3 4-4 ", zs.toString());

        // ===== Greedy versus reluctant =====
        Matcher r = Pattern.compile("(a+?)(b)?").matcher("aab");
        errorCount += Checkers.checker("reluctant find #1", true, r.find());
        errorCount += Checkers.checker("reluctant find #1 group()", "a", r.group());
        errorCount += Checkers.checker("reluctant find #2", true, r.find());
        errorCount += Checkers.checker("reluctant find #2 group()", "ab", r.group());
        errorCount += Checkers.checker("reluctant find #3", false, r.find());

        // ===== Flags =====
        Pattern pi = Pattern.compile("(?<word>a+)(b)?", Pattern.CASE_INSENSITIVE);
        errorCount += Checkers.checker("CASE_INSENSITIVE flags()", Pattern.CASE_INSENSITIVE, pi.flags());
        errorCount += Checkers.checker("CASE_INSENSITIVE matches AAB", true, pi.matcher("AAB").matches());
        errorCount += Checkers.checker("case-sensitive rejects AAB", false, p.matcher("AAB").matches());
        errorCount += Checkers.checker("inline (?i)", true, Pattern.compile("(?i)ab").matcher("aB").matches());

        // ===== Named backreference =====
        Pattern pb = Pattern.compile("(?<word>a+)b\\k<word>");
        errorCount += Checkers.checker("\\k<word> matches aabaa", true, pb.matcher("aabaa").matches());
        errorCount += Checkers.checker("\\k<word> rejects aaba", false, pb.matcher("aaba").matches());

        // ===== Static helpers =====
        errorCount += Checkers.checker("Pattern.matches true", true, Pattern.matches("a+b?", "aaab"));
        errorCount += Checkers.checker("Pattern.matches false", false, Pattern.matches("a+b?", "bbb"));
        errorCount += Checkers.checker("Pattern.quote", "\\Qa.b\\E", Pattern.quote("a.b"));
        errorCount += Checkers.checker("quoted pattern matches literal", true, Pattern.compile(Pattern.quote("a.b")).matcher("a.b").matches());
        errorCount += Checkers.checker("quoted pattern rejects wildcard", false, Pattern.compile(Pattern.quote("a.b")).matcher("axb").matches());
        errorCount += Checkers.checker("split on b+ length", 3, Pattern.compile("b+").split("abbcbd").length);
        errorCount += Checkers.checker("split on b+ [1]", "c", Pattern.compile("b+").split("abbcbd")[1]);

        // ===== Exceptions =====
        errorCount += Checkers.checker("group() before find", "IllegalStateException", thrown(() -> p.matcher("aa").group()));
        errorCount += Checkers.checker("start() before find", "IllegalStateException", thrown(() -> p.matcher("aa").start()));
        errorCount += Checkers.checker("group(3) out of range", "IndexOutOfBoundsException",
                thrown(() -> { Matcher t = p.matcher("aa"); t.find(); t.group(3); }));
        errorCount += Checkers.checker("group(\"nope\") unknown name", "IllegalArgumentException",
                thrown(() -> { Matcher t = p.matcher("aa"); t.find(); t.group("nope"); }));
        errorCount += Checkers.checker("group() after failed find", "IllegalStateException",
                thrown(() -> { Matcher t = p.matcher("zzz"); t.find(); t.group(); }));
        errorCount += Checkers.checker("compile unclosed group", "PatternSyntaxException", thrown(() -> Pattern.compile("(a")));
        errorCount += Checkers.checker("compile dangling +", "PatternSyntaxException", thrown(() -> Pattern.compile("+a")));
        errorCount += Checkers.checker("compile duplicate group name", "PatternSyntaxException",
                thrown(() -> Pattern.compile("(?<w>a)(?<w>b)")));
        errorCount += Checkers.checker("PatternSyntaxException.getIndex()", 2, thrown2());

        Checkers.theEnd(errorCount);
    }

    // Index reported for an unclosed group in "(a" (the end of the pattern).
    private static int thrown2() {
        try {
            Pattern.compile("(a");
            return -99;
        } catch (PatternSyntaxException e) {
            return e.getIndex();
        }
    }
}
