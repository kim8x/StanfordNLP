package com.kim8x.nlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class NER {
    public static void main(String[] args){
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String texto = "Hey! My name is Fatima-Ezzahrae and I have a friend his name is Robert! "+
                "We both living in New York";
        CoreDocument coreDoc = new CoreDocument(texto);

        stanfordCoreNLP.annotate(coreDoc);

        List<CoreLabel> labels = coreDoc.tokens();
        System.out.println(texto);
        for(CoreLabel coreLabel : labels){
            String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);

            System.out.println(coreLabel.originalText()+" = "+ner);
        }

    }
}
