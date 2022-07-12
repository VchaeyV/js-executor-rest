package io.github.daniil547.js_executor_rest.exceptions;

import io.github.daniil547.js_executor_rest.domain.LanguageTask;

import java.util.UUID;

@Deprecated(forRemoval = true)
public class IllegalRestartException extends RuntimeException {
    public IllegalRestartException(UUID id, LanguageTask.Status status) {
        super("Restart support is dropped, please resubmit the task to rerun it");
    }
}
