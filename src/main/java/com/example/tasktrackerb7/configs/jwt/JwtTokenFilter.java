package com.example.tasktrackerb7.configs.jwt;

import com.example.tasktrackerb7.entities.User;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtils;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalToken = getTokenFromRequest(request);
        optionalToken.ifPresent(token -> {
            String email = jwtUtils.verifyToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(NotFoundException::new);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getRoles(),
                    user.getAuthInfo().getEmail(),
                    null
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return Optional.of(header.substring("Bearer ".length()));
        }
        return Optional.empty();
    }
}