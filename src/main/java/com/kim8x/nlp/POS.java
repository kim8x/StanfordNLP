package com.kim8x.nlp;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.CoreDocument;

public class POS {
    public static void main(String[] args){
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String txt = "Hey, He's name is Robet he lives in Berline";

        CoreDocument coreDoc = new CoreDocument(txt);
        stanfordCoreNLP.annotate(coreDoc);
        List<CoreLabel> coreLabelList = coreDoc.tokens();

        System.out.println("Sentence: ");
        System.out.println(txt);
        System.out.println("**** POS Tagging ****");

        for(CoreLabel coreLabel : coreLabelList){
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);


            System.out.println(coreLabel.originalText()+"   "+pos);
        }
    }
}
