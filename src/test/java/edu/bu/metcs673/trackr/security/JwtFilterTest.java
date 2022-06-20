package edu.bu.metcs673.trackr.security;

import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.user.TrackrUserServiceImpl;

/**
 * Simple Unit tests for JWT filter.
 *
 * @author Jean Dorancy
 */
public class JwtFilterTest {
    private static final User USER = new User("test", "test", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    private final JWTUtil util = new JWTUtil("test");
    private final TrackrUserServiceImpl service = mock(TrackrUserServiceImpl.class);
    private JwtFilter filter;

    @BeforeEach
    void setUp() {
        this.filter = new JwtFilter(service, util);
    }

    private static Stream<Arguments> shouldNotFilterUrls() {
        return Stream.of(
                Arguments.of(String.format(CommonConstants.FAVICON_PATH)),
                Arguments.of(String.format("%s%s/", CommonConstants.IMAGES_PATH, "logo.png")),
                Arguments.of(String.format("%s%s/", CommonConstants.IMAGES_PATH, "awesome.jpeg")),
                Arguments.of(String.format("%s%s/", CommonConstants.IMAGES_PATH, "any-other-images.extension")),
                Arguments.of(String.format("%s%s/", CommonConstants.GENERATED_ASSETS_PATH, "bundle.js")),
                Arguments.of(String.format("%s%s/", CommonConstants.GENERATED_ASSETS_PATH, "min.css")),
                Arguments.of(String.format("%s%s/", CommonConstants.GENERATED_ASSETS_PATH, "some-file.extension")),
                Arguments.of(CommonConstants.ROOT_PATH),
                Arguments.of(CommonConstants.REGISTER_PATH),
                Arguments.of(CommonConstants.LOGIN_PATH),
                Arguments.of(CommonConstants.LOGOUT_PATH)

        );
    }

    private static Stream<Arguments> shouldFilterUrls() {
        return Stream.of(
                Arguments.of("/api/v1/users"),
                Arguments.of("/api/v1/users/1"),
                Arguments.of("/api/v1/users/38493"),
                Arguments.of("/api/v1/bank-accounts"),
                Arguments.of("/api/v1/bank-accounts/1"),
                Arguments.of("/api/v1/bank-accounts/340"),
                Arguments.of("/api/v1/transactions"),
                Arguments.of("/api/v1/transactions/1"),
                Arguments.of("/api/v1/transactions/9854"),
                Arguments.of("/dashboard"),
                Arguments.of("/accounts"),
                Arguments.of("/profile"),
                Arguments.of("/any-other-page-should-filter"),
                Arguments.of("/any/other/page/even/nested")

        );
    }

    @ParameterizedTest
    @MethodSource("shouldNotFilterUrls")
    public void testShouldNotFilerUrls(String uri) throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        filter.doFilter(request, response, chain);

        assertTrue(filter.shouldNotFilter(request));
        verify(chain, times(1)).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("shouldFilterUrls")
    public void testShouldFilerUrlsUnauthorized(String uri) throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        filter.doFilter(request, response, chain);

        assertFalse(filter.shouldNotFilter(request));
        verify(chain, times(0)).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("shouldFilterUrls")
    public void testShouldFilerUrlsAuthorizedWithCookie(String uri) throws ServletException, IOException {
        Cookie[] cookies = new Cookie[]{new Cookie(JWT_COOKIE_NAME, util.generateToken("test"))};
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getCookies()).thenReturn(cookies);
        when(service.loadUserByUsername(anyString())).thenReturn(USER);
        filter.doFilter(request, response, chain);

        assertFalse(filter.shouldNotFilter(request));
        verify(chain, times(1)).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("shouldFilterUrls")
    public void testShouldFilerUrlsUnauthorizedWithCookie(String uri) throws ServletException, IOException {
        Cookie[] cookies = new Cookie[]{new Cookie(JWT_COOKIE_NAME, "my-fancy-token-from-cookie")};
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getCookies()).thenReturn(cookies);
        when(service.loadUserByUsername(anyString())).thenReturn(USER);
        filter.doFilter(request, response, chain);

        assertFalse(filter.shouldNotFilter(request));
        verify(chain, times(0)).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("shouldFilterUrls")
    public void testShouldFilerUrlsAuthorizedWithHeader(String uri) throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getHeader("Authorization")).thenReturn(String.format("Bearer %s", util.generateToken("test")));
        when(service.loadUserByUsername(anyString())).thenReturn(USER);
        filter.doFilter(request, response, chain);

        assertFalse(filter.shouldNotFilter(request));
        verify(chain, times(1)).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("shouldFilterUrls")
    public void testShouldFilerUrlsUnauthorizedWithHeader(String uri) throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getHeader("Authorization")).thenReturn(String.format("Bearer %s", "my-fancy-token-with-header"));
        when(service.loadUserByUsername(anyString())).thenReturn(USER);
        filter.doFilter(request, response, chain);

        assertFalse(filter.shouldNotFilter(request));
        verify(chain, times(0)).doFilter(request, response);
    }
}
