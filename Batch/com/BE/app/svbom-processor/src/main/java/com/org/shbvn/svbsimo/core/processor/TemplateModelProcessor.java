package com.org.shbvn.svbsimo.core.processor;

import com.google.auto.service.AutoService;
import com.org.shbvn.svbsimo.core.annotation.TemplateModel;
import com.org.shbvn.svbsimo.core.annotation.TemplateModels;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Annotation processor that generates model classes based on @TemplateModel annotations
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.org.shbvn.svbsimo.core.annotation.TemplateModel", "com.org.shbvn.svbsimo.core.annotation.TemplateModels"})
@SupportedSourceVersion(SourceVersion.RELEASE_17) // Update to match project Java version
public class TemplateModelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "Processing is over, skipping this round"
            );
            return false;
        }

        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "TemplateModelProcessor is running! Annotations: " + annotations
        );
        
        // Process @TemplateModel annotations
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TemplateModel.class);
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "Found " + elements.size() + " elements with @TemplateModel"
        );
        
        for (Element element : elements) {
            try {
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "Processing @TemplateModel on " + element.getSimpleName()
                );
                
                TemplateModel annotation = element.getAnnotation(TemplateModel.class);
                if (annotation != null) {
                    generateModelClass(annotation);
                } else {
                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed to get annotation from element " + element.getSimpleName()
                    );
                }
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Error processing element " + element.getSimpleName() + ": " + e.getMessage()
                );
            }
        }
        
        // Process @TemplateModels annotations
        Set<? extends Element> containerElements = roundEnv.getElementsAnnotatedWith(TemplateModels.class);
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "Found " + containerElements.size() + " elements with @TemplateModels"
        );
        
        for (Element element : containerElements) {
            try {
                TemplateModels containingAnnotation = element.getAnnotation(TemplateModels.class);
                if (containingAnnotation != null) {
                    for (TemplateModel annotation : containingAnnotation.value()) {
                        generateModelClass(annotation);
                    }
                }
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Error processing container element " + element.getSimpleName() + ": " + e.getMessage()
                );
            }
        }
        
        return true;
    }

    private void generateModelClass(TemplateModel annotation) {
        try {
            String code = annotation.code();
            String columns = annotation.columns();
            
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "Generating class for code: " + code + " with columns: " + columns
            );
            
            //String[] columnNames = columns.split(",");
            // Parse columns into field definitions
            Map<String, List<String>> nestedFields = new LinkedHashMap<>();
            List<String> rootFields = new ArrayList<>();

            parseColumns(columns, rootFields, nestedFields);

            // Create class name
            String className = code.replaceAll("[^a-zA-Z0-9]", "") + "Template";
            
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "Generating class: " + className + " with columns: " + columns
            );
            
            // Create package and class builder
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC);
                    
            // // Handle base class
            // try {
            //     // We can't directly use annotation.baseClass() because it's a Class object
            //     // Instead, we need to get the class name as a string
            //     String baseClassName = annotation.baseClass().getName();
            //     if (!baseClassName.equals("java.lang.Object")) {
            //         classBuilder.superclass(ClassName.bestGuess(baseClassName));
            //     }
            // } catch (MirroredTypeException mte) {
            //     // This is expected - we get the TypeMirror instead
            //     TypeMirror typeMirror = mte.getTypeMirror();
            //     classBuilder.superclass(ClassName.get(typeMirror));
            // }
            
            // // Add fields, getters, and setters for each column
            // for (String column : columnNames) {
            //     String fieldName = toCamelCase(column);
                
            //     // Add field
            //     FieldSpec field = FieldSpec.builder(String.class, fieldName)
            //             .addModifiers(Modifier.PRIVATE)
            //             .build();
            //     classBuilder.addField(field);
                
            //     // Add getter
            //     MethodSpec getter = MethodSpec.methodBuilder("get" + capitalize(fieldName))
            //             .addModifiers(Modifier.PUBLIC)
            //             .returns(String.class)
            //             .addStatement("return $N", fieldName)
            //             .build();
            //     classBuilder.addMethod(getter);
                
            //     // Add setter
            //     MethodSpec setter = MethodSpec.methodBuilder("set" + capitalize(fieldName))
            //             .addModifiers(Modifier.PUBLIC)
            //             .addParameter(String.class, fieldName)
            //             .addStatement("this.$N = $N", fieldName, fieldName)
            //             .build();
            //     classBuilder.addMethod(setter);
            // }
            // 
            // Nested fields (List type)
            // Add root fields (e.g., ThoiGianDuLieu, ThoiGianGuiBaoCao)
            for (String field : rootFields) {
                String fieldName = toCamelCase(field);

                // Field
                FieldSpec fieldSpec = FieldSpec.builder(String.class, fieldName, Modifier.PRIVATE)
                    .addAnnotation(AnnotationSpec.builder(ClassName.get("com.fasterxml.jackson.annotation", "JsonProperty"))
                    .addMember("value", "$S", field)
                    .build())
                    .build();
                classBuilder.addField(fieldSpec);

                // Getter
                classBuilder.addMethod(generateGetter(fieldName, String.class));

                // Setter
                classBuilder.addMethod(generateSetter(fieldName, String.class));
            }
            for (Map.Entry<String, List<String>> entry : nestedFields.entrySet()) {
                String listField = entry.getKey();
                String itemClassName = capitalize(toCamelCase(listField)) + "Item";

                // Tạo class cho item
                TypeSpec.Builder itemClass = TypeSpec.classBuilder(itemClassName).addModifiers(Modifier.PUBLIC, Modifier.STATIC);
                itemClass.addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build());

                for (String subField : entry.getValue()) {
                    String subFieldName = toCamelCase(subField);
                    FieldSpec subFieldSpec = FieldSpec.builder(String.class, subFieldName, Modifier.PRIVATE)
                        .addAnnotation(AnnotationSpec.builder(ClassName.get("com.fasterxml.jackson.annotation", "JsonProperty"))
                        .addMember("value", "$S", subField)
                        .build())
                        .build();
                    itemClass.addField(subFieldSpec);
                    itemClass.addMethod(generateGetter(subFieldName, String.class));
                    itemClass.addMethod(generateSetter(subFieldName, String.class));
                }

                classBuilder.addType(itemClass.build());

                // Add List<T> field
                ParameterizedTypeName listType = ParameterizedTypeName.get(ClassName.get(List.class), ClassName.bestGuess(itemClassName));
                FieldSpec listFieldSpec = FieldSpec.builder(listType, toCamelCase(listField), Modifier.PRIVATE)
                    .addAnnotation(AnnotationSpec.builder(ClassName.get("com.fasterxml.jackson.annotation", "JsonProperty"))
                    .addMember("value", "$S", listField)
                    .build())
                    .build();
                classBuilder.addField(listFieldSpec);
                classBuilder.addMethod(generateGetter(toCamelCase(listField), listType));
                classBuilder.addMethod(generateSetter(toCamelCase(listField), listType));
            }
            // Add default constructor
            MethodSpec defaultConstructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("super()")
                    .build();
            classBuilder.addMethod(defaultConstructor);
            
            // Create the Java file with more detailed logging
            JavaFile javaFile = JavaFile.builder("com.org.shbvn.svbsimo.core.model.generated", classBuilder.build())
                    .build();
            
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "About to write file: " + className + ".java to package com.org.shbvn.svbsimo.core.model.generated"
            );
            
            // Write the file with try-catch to catch specific IO errors
            try {
                javaFile.writeTo(processingEnv.getFiler());
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "Successfully wrote file: " + className + ".java"
                );
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "IO Error writing file: " + e.getMessage()
                );
                throw e;
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                "Error generating model class: " + e.getMessage() + "\n" + 
                "Stack trace: " + e.getStackTrace()[0]
            );
            throw new RuntimeException("Failed to generate model class", e);
        }
    }
    
    private String toCamelCase(String columnName) {
        String[] parts = columnName.toLowerCase().split("_");
        StringBuilder camelCase = new StringBuilder(parts[0]);
        
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                camelCase.append(Character.toUpperCase(parts[i].charAt(0)));
                camelCase.append(parts[i].substring(1));
            }
        }
        
        return camelCase.toString();
    }
    
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
   private MethodSpec generateGetter(String name, Class<String> type) {
    return MethodSpec.methodBuilder("get" + capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .returns(type)
        .addStatement("return $N", name)
        .build();
}

private MethodSpec generateSetter(String name, Class<String> type) {
    return MethodSpec.methodBuilder("set" + capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .addParameter(type, name)
        .addStatement("this.$N = $N", name, name)
        .build();
} 

// Overloaded methods for TypeName (for generic types like List<T>)
private MethodSpec generateGetter(String name, TypeName type) {
    return MethodSpec.methodBuilder("get" + capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .returns(type)
        .addStatement("return $N", name)
        .build();
}

private MethodSpec generateSetter(String name, TypeName type) {
    return MethodSpec.methodBuilder("set" + capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .addParameter(type, name)
        .addStatement("this.$N = $N", name, name)
        .build();
} 
    private void parseColumns(String columns, List<String> rootFields, Map<String, List<String>> nestedFields) {
        int i = 0;
        while (i < columns.length()) {
            int bracketStart = columns.indexOf('[', i);
            if (bracketStart == -1) {
                String[] parts = columns.substring(i).split(",");
                for (String p : parts) if (!p.trim().isEmpty()) rootFields.add(p.trim());
                break;
            }

            int lastCommaBefore = columns.lastIndexOf(',', bracketStart);
            String prefix = columns.substring(lastCommaBefore + 1, bracketStart).trim();
            String before = columns.substring(i, lastCommaBefore);
            for (String p : before.split(",")) if (!p.trim().isEmpty()) rootFields.add(p.trim());

            int bracketEnd = columns.indexOf(']', bracketStart);
            String inside = columns.substring(bracketStart + 1, bracketEnd);
            nestedFields.put(prefix, Arrays.asList(inside.split(",")));
            i = bracketEnd + 1;
            if (i < columns.length() && columns.charAt(i) == ',') i++;
        }
    }

}
