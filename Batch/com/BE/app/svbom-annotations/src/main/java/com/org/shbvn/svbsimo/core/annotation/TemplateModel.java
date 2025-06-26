package com.org.shbvn.svbsimo.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a template model that will be processed
 * to generate a model class
 */
@Retention(RetentionPolicy.CLASS) // Change from SOURCE to CLASS
@Target({ElementType.TYPE})
public @interface TemplateModel {
    /**
     * Template code identifier
     */
    String code();
    
    /**
     * Comma-separated list of column names
     */
    String columns();
    
    /**
     * Base class for the generated model
     */
    Class<?> baseClass() default Object.class;
}
