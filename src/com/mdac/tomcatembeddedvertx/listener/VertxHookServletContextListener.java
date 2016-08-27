package com.mdac.tomcatembeddedvertx.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class VertxHookServletContextListener
               implements ServletContextListener{

	Vertx vertx;
	HttpServer server;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		if(server != null){
			server.close();
		}
		
		if(vertx != null){
			vertx.close();
		}
		
		System.out.println("Vertx Http Server stopped on port 8081");
	}

    //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		vertx = Vertx.factory.vertx();
		server = vertx.createHttpServer();
		
		Router router = Router.router(vertx);
		
		router.route().handler(context -> {
		
			context.response().end("I am served from Vertx");
		
		});
		
		server.requestHandler(router::accept).listen(8081);
		
		System.out.println("Vertx Http Server started on port 8081");
	}
}
