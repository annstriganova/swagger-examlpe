package com.example.pine.config;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@Configuration
public class SwaggerExampleProcessor {

    private static final String FILE_PREFIX = "file:";

    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    log.info("Process examples for requests");
                    operation.getResponses().values().stream().map(ApiResponse::getContent).forEach(this::processExamplesFromContent);
                    log.info("Process examples for responses");
                    processExamplesFromContent(operation.getRequestBody().getContent());
                });
    }

    private void processExamplesFromContent(Content content) {
        if (content != null) {
            for (Map.Entry<String, MediaType> entry : content.entrySet()) {
                MediaType mediaType = entry.getValue();
                if (mediaType.getExamples() != null) {
                    mediaType.getExamples().values().forEach((example) -> {
                        if (example.getValue() instanceof String value && value.startsWith(FILE_PREFIX)) {
                            String filePath = value.substring(FILE_PREFIX.length());
                            log.info("Loading example from {}", filePath);
                            example.setValue(loadExampleFromFile(filePath));
                        }
                    });
                }
            }
        }
    }

    private String loadExampleFromFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            log.error("Failed to read example file: {}", filePath, e);
            return "Failed to read example file %s".formatted(filePath);
        }
    }
}
