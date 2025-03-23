package org.example.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.util.HmacUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class HmacAuthFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "YourSecretKey";
    private static final long REQUEST_TIMEOUT = 300000L; // 5 minutes

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String signature = request.getHeader("X-Signature");
        String timestamp = request.getHeader("X-Timestamp");

        if (signature == null || timestamp == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing authentication headers.");
            return;
        }

        try {
            long requestTime = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - requestTime > REQUEST_TIMEOUT) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Request has expired.");
                return;
            }

            String requestBody = request.getReader().lines().reduce("", String::concat);
            String data = timestamp + requestBody;
            String expectedSignature = HmacUtil.generateHmac(data, SECRET_KEY);

            if (!expectedSignature.equals(signature)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid signature.");
                return;
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

