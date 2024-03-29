package io.github.daniil547.js_executor_rest.repos;

import io.github.daniil547.js_executor_rest.domain.objects.LanguageTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface TaskRepository {

    @SuppressWarnings("UnusedReturnValue")
    LanguageTask storeTask(LanguageTask task);

    LanguageTask getTask(UUID id);

    void deleteTask(UUID id);

    // Predicate isn't the best choice, a dedicated interface would've been better
    Pair<List<LanguageTask>, Integer> getAllTasks(Predicate<LanguageTask> filter, Pageable paging);
}
