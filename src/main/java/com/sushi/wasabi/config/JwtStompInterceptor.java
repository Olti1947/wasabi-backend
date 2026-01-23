package com.sushi.wasabi.config;

import com.sushi.wasabi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtStompInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        StompCommand command = accessor.getCommand();
        if (command == null) return message;

        System.out.println("STOMP command: " + command);
        System.out.println("Headers: " + accessor.toString());


        if (StompCommand.CONNECT.equals(command)) {
            String token = null;

            // 1️⃣ Try header first (works if you ever switch to WebSocket)
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if(authHeader == null){
                authHeader = accessor.getFirstNativeHeader("authorization");
            }
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            // 2️⃣ Fallback to query param from connect message
            if (token == null) {
                System.out.println("Tried doing this shit");
                String rawUrl = (String) accessor.getHeader("simpConnectMessage"); // contains SockJS URL
                if (rawUrl != null && rawUrl.contains("token=")) {
                    token = rawUrl.split("token=")[1].split("&")[0]; // crude parsing
                }
            }

            if (token == null) {
                throw new IllegalArgumentException("Missing token");
            }

            // validate JWT as usual
            String username = jwtService.extractUserName(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(token, userDetails)) {
                throw new IllegalArgumentException("Invalid JWT");
            }

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            accessor.setUser(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("✅ WS authenticated user: " + username);
        }
        return message;
    }
}
