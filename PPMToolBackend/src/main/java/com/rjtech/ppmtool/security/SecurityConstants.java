package com.rjtech.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_PREFIX_LENGTH = 7;
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 120_000;
}
