package com.dacklabs.spookyaction.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyMain {

	public static void main(String[] args) {
		try {
			Server server = new Server(1234);

			WebAppContext ctx = new WebAppContext();
			ctx.setResourceBase("src/main/webapp");
			ctx.addServlet(new ServletHolder(new DefaultServlet()), "/");

			server.setHandler(ctx);
			server.setThreadPool(new QueuedThreadPool(20));

			server.start();
			server.join();
		} catch (Exception e) {
			System.out.println("Hurk! Server crashed!");
			e.printStackTrace();
		}
	}

	private static class GWTHandler implements Handler {

		@Override
		public void addLifeCycleListener(Listener arg0) {
		}

		@Override
		public boolean isFailed() {
			return false;
		}

		@Override
		public boolean isRunning() {
			return false;
		}

		@Override
		public boolean isStarted() {
			return false;
		}

		@Override
		public boolean isStarting() {
			return false;
		}

		@Override
		public boolean isStopped() {
			return false;
		}

		@Override
		public boolean isStopping() {
			return false;
		}

		@Override
		public void removeLifeCycleListener(Listener arg0) {
		}

		@Override
		public void start() throws Exception {
		}

		@Override
		public void stop() throws Exception {
		}

		@Override
		public void destroy() {
		}

		@Override
		public Server getServer() {
			return null;
		}

		@Override
		public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3)
		    throws IOException, ServletException {
		}

		@Override
		public void setServer(Server arg0) {
		}

	}
}
