package com.kim8x.nlp;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {
    private static StanfordCoreNLP stanfordCoreNLP;
    private static String propertiesName = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
    private static Properties props;

    private Pipeline(){

    }
    static{
        props = new Properties();
        props.setProperty("annotators", propertiesName);
    }
    public static StanfordCoreNLP getPipeline(){
        if(stanfordCoreNLP == null){
            stanfordCoreNLP = new StanfordCoreNLP(props);
        }
        return stanfordCoreNLP;
    }
}
