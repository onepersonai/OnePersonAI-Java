package com.opai.rest;

import java.util.Map;

import com.opai.acl.EventAcl;
import com.opai.auth.ITokenService;
import com.opai.model.AppPart;
import com.opai.model.Event;
import com.opai.util.rest.MongoREST;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class EventRest extends MongoREST {
	
	private static String EXCLUDE = "exclude";
	

	public EventRest(ITokenService tokenService, MongoClient db) {
		super(tokenService, db, Event.class);
		setACL(new EventAcl(db));
	}

	@Override
	protected void beforeCreate(RoutingContext event, JsonObject json){
		/**
		 * Set the app id
		 */
		String appID = getId(event, "appID");
		json.put("appID", appID);
		
		/**
		 * Notify bus for dirty handler if a new session is started. This could have the effect 
		 * that a backup starts in the middle and half of the events are lost...
		 */
		//if(json.containsKey("type") && Event.TYPE_SESSION_START.equals(json.containsKey("type"))){
			AppPart.onUpdate(event, appID);
		//}
	}
	
	/**
	 * We support here the exclusion of specific event types,
	 *  e.g. animations!
	 */
	@Override
	protected JsonObject getPathQuery(RoutingContext event) {
		JsonObject query = super.getPathQuery(event);
		query.remove(EXCLUDE);
		query.remove(BATCH);
		String queryString = event.request().query();
		Map<String, String> map = parseQuery(queryString);
		if(map.containsKey(EXCLUDE)){
		    // "$ne" is resulting in slow mongo queries.
			// we should turn this around.
			String excludedType = map.get(EXCLUDE);
			query.put("type", new JsonObject().put("$ne", excludedType));
		}
		return query;
	}
	
	
}
