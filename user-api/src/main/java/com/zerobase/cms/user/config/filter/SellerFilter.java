package com.zerobase.cms.user.config.filter;

import com.zerobase.cms.user.service.seller.SellerServiceImpl;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/seller/*")
@RequiredArgsConstructor
public class SellerFilter implements Filter {
    private final JwtAuthenticationProvider provider;
    private final SellerServiceImpl sellerServiceImpl;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if (!provider.validateToken(token)) {
            throw new ServletException("Invalid Access");
        }
        UserVo vo = provider.getUserVo(token);
        sellerServiceImpl.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new ServletException("Invalid Access"));
        chain.doFilter(request, response);
    }
}
