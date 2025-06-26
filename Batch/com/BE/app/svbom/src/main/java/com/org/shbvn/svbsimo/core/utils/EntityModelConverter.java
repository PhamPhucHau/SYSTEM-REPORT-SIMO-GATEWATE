package com.org.shbvn.svbsimo.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic utility class to convert between entity and model objects
 */
public class EntityModelConverter {
    private static final Logger logger = LoggerFactory.getLogger(EntityModelConverter.class);

    private EntityModelConverter() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Generic method to convert an entity to a model
     * 
     * @param <E> Entity type
     * @param <M> Model type
     * @param entity Source entity
     * @param modelClass Target model class
     * @return Converted model instance
     */
    public static <E, M> M toModel(E entity, Class<M> modelClass) {
        if (entity == null) return null;
        
        try {
            // Try to find a no-args constructor
            Constructor<M> constructor = modelClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            M model = constructor.newInstance();
            
            // Copy properties using getters and setters
            Method[] entityMethods = entity.getClass().getMethods();
            Method[] modelMethods = modelClass.getMethods();
            
            for (Method getter : entityMethods) {
                String getterName = getter.getName();
                if (getterName.startsWith("get") && getter.getParameterCount() == 0) {
                    String propertyName = getterName.substring(3);
                    
                    // Find corresponding setter in model
                    for (Method setter : modelMethods) {
                        if (setter.getName().equals("set" + propertyName) && 
                            setter.getParameterCount() == 1) {
                            
                            // Get value from entity and set it to model
                            Object value = getter.invoke(entity);
                            setter.invoke(model, value);
                            break;
                        }
                    }
                }
            }
            
            return model;
        } catch (Exception e) {
            logger.error("Error converting entity to model", e.getMessage());
            return null;
        }
    }
    
    /**
     * Generic method to convert a list of entities to a list of models
     * 
     * @param <E> Entity type
     * @param <M> Model type
     * @param entities Source entity list
     * @param modelClass Target model class
     * @return List of converted model instances
     */
    public static <E, M> List<M> toModelList(List<E> entities, Class<M> modelClass) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
            .map(entity -> toModel(entity, modelClass))
            .collect(Collectors.toList());
    }
    
    /**
     * Generic method to convert an entity to a model using a custom converter function
     * 
     * @param <E> Entity type
     * @param <M> Model type
     * @param entity Source entity
     * @param converter Custom converter function
     * @return Converted model instance
     */
    public static <E, M> M toModel(E entity, Function<E, M> converter) {
        if (entity == null) return null;
        return converter.apply(entity);
    }
    
    /**
     * Generic method to convert a list of entities to a list of models using a custom converter function
     * 
     * @param <E> Entity type
     * @param <M> Model type
     * @param entities Source entity list
     * @param converter Custom converter function
     * @return List of converted model instances
     */
    public static <E, M> List<M> toModelList(List<E> entities, Function<E, M> converter) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
            .map(converter)
            .collect(Collectors.toList());
    }
}