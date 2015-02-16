package com.vbevans94.lastfm.data.api.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier @Retention(RetentionPolicy.RUNTIME)
public @interface ApiKey {
}
