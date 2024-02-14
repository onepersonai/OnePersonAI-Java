package com.opai.validation;

import com.opai.util.JSONMapper;
import io.vertx.ext.mongo.MongoClient;

public class MongValidator {


	protected final MongoClient client;
	
	protected final JSONMapper mapper = new JSONMapper();
	
	public MongValidator(MongoClient client){
		this.client = client;
	}
}
