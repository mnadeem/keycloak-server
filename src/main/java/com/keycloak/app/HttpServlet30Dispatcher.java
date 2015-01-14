package com.keycloak.app;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.Servlet3AsyncHttpRequest;
import org.jboss.resteasy.specimpl.UriInfoImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

@WebServlet(asyncSupported = true)
public class HttpServlet30Dispatcher extends HttpServletDispatcher {

	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		Dispatcher dispatcher = (Dispatcher) servletConfig.getServletContext().getAttribute(Dispatcher.class.getName());
		ResteasyProviderFactory.pushContext(Dispatcher.class, dispatcher);
		ResteasyProviderFactory.pushContext(ServletContext.class, servletConfig.getServletContext());
		super.init(servletConfig);
	}

	@Override
	protected HttpRequest createHttpRequest(String httpMethod, HttpServletRequest httpServletRequest, HttpHeaders httpHeaders, UriInfoImpl uriInfo, HttpResponse httpResponse, HttpServletResponse httpServletResponse)
	{
		return new Servlet3AsyncHttpRequest(httpServletRequest, httpServletResponse, httpResponse, httpHeaders, uriInfo, httpMethod, (SynchronousDispatcher) getDispatcher());
	}

}
