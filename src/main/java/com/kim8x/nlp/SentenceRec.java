package com.kim8x.nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.List;
public class SentenceRec {
    public static void main(String[]args){
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String txt = "Hello, Kimoox is fine. Just chilling";
        CoreDocument coreDoc = new CoreDocument(txt);

        stanfordCoreNLP.annotate(coreDoc);
        List<CoreSentence> sentences = coreDoc.sentences();

        for(CoreSentence sentence : sentences){
            System.out.println(sentence.toString());
        }
    }
}
