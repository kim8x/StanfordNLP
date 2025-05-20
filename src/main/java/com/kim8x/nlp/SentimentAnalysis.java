package com.kim8x.nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class SentimentAnalysis {
    public static void main(String[] args){
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String txt = "Hello this is Kimox. Unfortunately. But I won."; //Fool
        CoreDocument coreDoc = new CoreDocument(txt);
        stanfordCoreNLP.annotate(coreDoc);

        List<CoreSentence> sentences = coreDoc.sentences();
        for(CoreSentence sentence : sentences){
            String sentiment = sentence.sentiment();
            System.out.println(sentiment+"\t"+sentence);
        }

    }
}
