package org.tio.http.server.view.freemarker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;

/**
 * @author tanyaowu 
 *  
 */
public class FreemarkerConfig {
	private static Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);
	
	private Configuration configuration;
	
	private ModelMaker modelMaker;
	
	private String[] suffixes = null;
	
	public FreemarkerConfig(Configuration configuration, ModelMaker modelMaker, String[] suffixes) {
		super();
		this.configuration = configuration;
		this.modelMaker = modelMaker;
		this.setSuffixes(suffixes);
	}

	

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public ModelMaker getModelMaker() {
		return modelMaker;
	}

	public void setModelMaker(ModelMaker modelMaker) {
		this.modelMaker = modelMaker;
	}

	/**
	 * 
	 * @author tanyaowu
	 */
	public FreemarkerConfig() {
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}



	/**
	 * @return the suffixes
	 */
	public String[] getSuffixes() {
		return suffixes;
	}



	/**
	 * @param suffixes the suffixes to set
	 */
