package com.assesment.task_project.security;

import com.assesment.task_project.customException.JwtTokenExpiredException;
import com.assesment.task_project.customException.JwtTokenInvalidException;
import com.assesment.task_project.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtauthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            String header = request.getHeader("Authorization");


            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);

            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or logged out");
                return;
            }

            try{
                if(token!= null){
                    UserDetails userDetails=authService.validateToken(token);
                    UsernamePasswordAuthenticationToken
                            authentication=new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    if(userDetails instanceof CustomerUserDetails){
                        request.setAttribute("userId",((CustomerUserDetails) userDetails).getId());

                    }

                }
            } catch (io.jsonwebtoken.ExpiredJwtException ex) {
                log.warn("JWT token has expired for request: {}", request.getRequestURI());
                throw new JwtTokenExpiredException("Token has expired");
            } catch (io.jsonwebtoken.JwtException ex) {
                log.warn("Invalid JWT token for request: {}", request.getRequestURI());
                throw new JwtTokenInvalidException("Invalid token");
            } catch (Exception ex) {
                log.error("Unexpected error during JWT authentication for request: {}", request.getRequestURI(), ex);
                throw new JwtTokenInvalidException("Authentication failed");
            }

            filterChain.doFilter(request,response);

        }
        private String extractToken(HttpServletRequest request) {
            String bearer = request.getHeader("Authorization");
            if (bearer != null && bearer.startsWith("Bearer ")) {
                return bearer.substring(7);
            }
            return null ;
        }
    }



