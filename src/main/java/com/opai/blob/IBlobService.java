package com.opai.blob;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface IBlobService {

    void setBlob(RoutingContext event, String tempFileToUpload, String target, Handler<Boolean> handler);

    void copyBlob(RoutingContext event, String source, String target, Handler<Boolean> handler);

    void getBlob(RoutingContext event, String folder, String file);

    String createFolder(RoutingContext event, String folder);

    void deleteFile(RoutingContext event, String folder, String fileName, Handler<Boolean> handler);

}
