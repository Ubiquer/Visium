package com.example.arek.visium.dependency_injection.application;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by arek on 12/5/2017.
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface VisiumApplicationScope {
}
