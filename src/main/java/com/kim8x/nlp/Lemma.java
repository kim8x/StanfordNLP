package com.kim8x.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class Lemma {
    public static void main(String[] args){
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String text = "That cat is funny";
        CoreDocument coreDoc = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDoc);
        List<CoreLabel> coreLabelList = coreDoc.tokens();

        for(CoreLabel coreLabel : coreLabelList){
            String lemma = coreLabel.lemma();
            System.out.println(coreLabel.originalText()+": "+lemma);
        }
    }
}
