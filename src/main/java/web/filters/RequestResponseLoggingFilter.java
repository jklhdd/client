package web.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import web.dto.ThanhVien;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet filter to log request and response The logging implementation is
 * pretty native and for demonstration only
 * 
 * @author hemant
 *
 */
//@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

	private final static Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		LOG.info("Initializing filter :{}", this);
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
		ThanhVien thanhVien = (ThanhVien) req.getSession().getAttribute("account");
//		LOG.info(thanhVien.getTaikhoan());
		if(req.getRequestURI().equals("/") || req.getRequestURI().equals("/login")) {
			LOG.info("ok");
			chain.doFilter(request, response);
		}
		else if (req.getSession().getAttribute("account") == null ) {
				LOG.info("ok2");
			    res.sendRedirect(req.getContextPath() + "/");
			} else {
				LOG.info("ok3");
				//request.setAttribute("account", thanhVien);
				chain.doFilter(request, response);
			}

		LOG.info("Logging Response :{}", res.getContentType());
	}

	@Override
	public void destroy() {
		LOG.warn("Destructing filter :{}", this);
	}
}