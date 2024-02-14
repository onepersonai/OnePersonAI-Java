package com.opai.auth;

import com.opai.model.User;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public interface ITokenService {

    //User getUser(String token);

    User getUser(RoutingContext event);

    String getToken(JsonObject user);
}
