package com.galvanize.indus.gmdb.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GmdbException.class)
    public ResponseEntity<Object> handleGmdbException(
            GmdbException ex, WebRequest request) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode incorrectMovie = mapper.createObjectNode();
        incorrectMovie.put("message", "Movie does not exist");
        String messageObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(incorrectMovie);

        return new ResponseEntity<>(messageObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GmdbMissingRatingException.class)
    public ResponseEntity<Object> handleGmdbMissingRatingException(
            GmdbMissingRatingException ex, WebRequest request) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode missingRating = mapper.createObjectNode();
        missingRating.put("message", "Star rating is required");
        String messageObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(missingRating);

        return new ResponseEntity<>(messageObject, HttpStatus.NOT_ACCEPTABLE);
    }
}
