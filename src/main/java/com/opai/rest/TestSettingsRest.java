package com.opai.rest;

import com.opai.auth.ITokenService;
import com.opai.model.TestSetting;
import com.opai.model.User;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class TestSettingsRest extends AppPartREST<TestSetting>{

	public TestSettingsRest(ITokenService tokenService, MongoClient db, Class<TestSetting> cls, String idParameter) {
		super(tokenService, db, cls, idParameter);
	}
	
	protected JsonObject createInstance(RoutingContext event, String appID){
		User u = getUser(event);
		return TestSetting.createEmpty(u, getId(event, "appID"));
	}

}
