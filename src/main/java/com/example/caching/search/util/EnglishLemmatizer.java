package com.example.caching.search.util;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.SentenceUtils;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class EnglishLemmatizer {

	private static StanfordCoreNLP pipeline;

	static {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
		pipeline = new StanfordCoreNLP(props);
	}

	public static List<String> lemmatize(String documentText) {
		List<String> lemmas = new ArrayList<>();
		Annotation document = new Annotation(documentText);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
			}
		}
		return lemmas;
	}
}

