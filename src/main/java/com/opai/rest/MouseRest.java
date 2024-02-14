package com.opai.rest;

import com.opai.acl.EventAcl;
import com.opai.auth.ITokenService;
import com.opai.model.Mouse;
import com.opai.util.rest.MongoREST;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class MouseRest extends MongoREST {

	public MouseRest(ITokenService tokenService, MongoClient db) {
		super(tokenService, db, Mouse.class);
		setACL(new EventAcl(db));
	}

	@Override
	protected void beforeCreate(RoutingContext event, JsonObject json){
		String appID = getId(event, "appID");
		json.put("appID", appID);
	}
}
