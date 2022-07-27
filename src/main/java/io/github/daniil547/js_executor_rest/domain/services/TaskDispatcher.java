package io.github.daniil547.js_executor_rest.domain.services;

import io.github.daniil547.js_executor_rest.domain.objects.LanguageTask;
import io.github.daniil547.js_executor_rest.dtos.TaskView;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * An external executor for {@link LanguageTask}s.
 */
public interface TaskDispatcher {

    /**
     * Adds a task to this task dispatcher.<br>
     * Might queue or start execution immediately.
     *
     * @param task - task to execute
     */
    void addForExecution(LanguageTask task);

    /**
     * Cancels task execution but doesn't remove from the
     * dispatcher.
     *
     * @param id id of the task to cancel
     */
    void cancelExecution(UUID id);

    /**
     * Removes the task from this dispatcher, ceasing execution.
     *
     * @param id id of a task to delete
     */
    void removeTask(UUID id);

    /**
     * @param id of the task to fetch
     * @return the task with the given ID
     */
    TaskView getTask(UUID id);

    /**
     * @return all tasks managed by this dispatcher
     */
    List<TaskView> getAllTasks(Predicate<LanguageTask> filter, Pageable paging);

    long getTaskCount();
}
