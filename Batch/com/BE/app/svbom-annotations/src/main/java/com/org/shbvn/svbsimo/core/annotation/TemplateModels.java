package com.org.shbvn.svbsimo.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation for multiple @TemplateModel annotations
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface TemplateModels {
    /**
     * Array of TemplateModel annotations
     */
    TemplateModel[] value();
}