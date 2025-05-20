import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.*;

import java.util.*;

public class NLPExample {
    public static void main(String[] args) {
        // 1. Setup Stanford NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // 2. Input text
        String text = "Barack Obama was born in Hawaii. He was elected president in 2008. I love this historic moment!";

        // 3. Create and annotate document
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        // 4. Named Entity Recognition (NER)
        System.out.println("🔍 Named Entities:");
        for (CoreLabel token : document.tokens()) {
            String word = token.word();
            String ner = token.ner();
            if (!"O".equals(ner)) { // Skip non-entity words
                System.out.println(word + " : " + ner);
            }
        }

        // 5. Sentiment Analysis
        System.out.println("\n💬 Sentiment by sentence:");
        for (CoreSentence sentence : document.sentences()) {
            System.out.println("\"" + sentence.text() + "\" → Sentiment: " + sentence.sentiment());
        }
    }
}
