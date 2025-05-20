package com.kim8x.nlp;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.List;
public class Tokenize {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        String txt = "Hello, This is the simple text for Kimoox";
        CoreDocument coreDoc = new CoreDocument(txt);
        stanfordCoreNLP.annotate(coreDoc);

        List<CoreLabel> coreLabelList = coreDoc.tokens();

        for(CoreLabel coreLabel:coreLabelList){
            System.out.println(coreLabel.originalText());
        }
    }
}
