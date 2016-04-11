package jfang.project.timesheet.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jfang on 4/11/2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ResourceNotFoundException(String message) {
        super(message);
        logger.info("resource not found: " + message);
    }
}
