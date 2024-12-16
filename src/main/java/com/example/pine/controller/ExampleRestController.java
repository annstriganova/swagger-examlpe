package com.example.pine.controller;

import com.example.pine.dto.RequestDto;
import com.example.pine.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleRestController {
    @Operation(summary = "Test method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is ok",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Example 1",
                                            summary = "Example loaded from response-1.json",
                                            value = "file:src/main/resources/examples/response-1.json"
                                    ),
                                    @ExampleObject(
                                            name = "Response Example 2",
                                            summary = "Example loaded from response-2.json",
                                            value = "file:src/main/resources/examples/response-2.json"
                                    )
                            }))})
    @PostMapping("/test")
    public ResponseDto testMethod(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = {
                    @ExampleObject(
                            name = "Request Example 1",
                            summary = "Example loaded from request-1.json",
                            value = "file:src/main/resources/examples/request-1.json"
                    ),
                    @ExampleObject(
                            name = "Request Example 2",
                            summary = "Example loaded from request-2.json",
                            value = "file:src/main/resources/examples/request-2.json"
                    ),
            }))
                             @RequestBody RequestDto requestDto) {
        return new ResponseDto(requestDto.name());
    }
}
