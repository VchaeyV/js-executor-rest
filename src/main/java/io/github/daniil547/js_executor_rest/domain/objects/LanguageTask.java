package io.github.daniil547.js_executor_rest.domain.objects;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a single script of a guest language.
 * <p>
 * If implementation is to be managed externally
 * (e.g. by an {@link java.util.concurrent.ExecutorService})
 * must allow valid concurrent (non-blocking) access to its methods.
 * <p>
 * Access to host machine or code from within a guest script
 * should be prohibited if the code isn't trusted.
 */
public interface LanguageTask {

    /**
     * Represents state of task's execution.
     */
    public static enum Status {
        /**
         * The task is, or being, created, submitted to an
         * {@link java.util.concurrent.ExecutorService} and waiting in
         * the queue etc.
         */
        SCHEDULED,
        /**
         * Execution of the task has started.
         */
        RUNNING,
        /**
         * The task was canceled (either by a user, or
         * app's logic).
         */
        CANCELED,
        /**
         * Task's execution is finished, either successfully
         * or with errors. This means that output is no longer written,
         * no errors will be thrown etc. In other words, the task's
         * object must not be mutated. <br>
         */
        FINISHED;
    }


    /**
     * @return task's ID
     */
    UUID getId();

    /**
     * @return task's source code
     */
    String getSource();

    /**
     * Returns the state of the task at some point in the past.
     *
     * @return task's status
     */
    Status getStatus();

    /**
     * Returns output of a task, generated by things like <code>print()</code>,
     * or <code>console.log()</code> or other means of writing to standard
     * output, defined by a language.
     * <p>
     * Access of a task to any files on host machine or other output channels
     * best be prohibited if guest code isn't trusted.
     * <p>
     * If previously invoked {@link #getStatus()} returned {@link Status#RUNNING},
     * then returned data represents output at some point during the task's
     * execution or after it's finished. <br>
     * If {@link #getStatus()} returned {@link Status#FINISHED}, then returned
     * data must not change.
     *
     * @return standard out output of the script
     */
    String getOutput();

    /**
     * <ul>
     *     <li>If the task is {@link Status#SCHEDULED} returns {@link Optional#empty()}.</li>
     *     <li>Otherwise, returns time at which execution of the task has started as
     *         {@link Optional#of(Object) Optional.of(ZonedDateTime)}</li>
     * </ul>
     *
     * @return time at which execution of the task has started
     */
    Optional<ZonedDateTime> getStartTime();

    /**
     * <ul>
     *     <li>If the task is {@link Status#SCHEDULED} returns {@link Optional#empty()}.</li>
     *     <li>If the task is {@link Status#RUNNING} returns time elapsed between the start of
     *         the task execution and current moment as T.</li>
     *     <li>Otherwise returns time elapsed between the start of the task execution and the moment it
     *         stopped (for any reason) as T.</li>
     * </ul>
     * where T is {@link Optional#of(Object) Optional.of(Duration)}
     *
     * @return time the task was being executed
     */
    Optional<Duration> getDuration();

    /**
     * <ul>
     *     <li>If the task is {@link Status#FINISHED} or {@link Status#CANCELED}
     *         returns the time at which the execution has ended (for any reason)
     *         as {@link Optional#of(Object) Optional.of(ZonedDateTime)}</li>
     *     <li>Otherwise returns {@link Optional#empty()}</li>
     * </ul>
     *
     * @return time at which execution of the task has ended (for any reason)
     */
    Optional<ZonedDateTime> getEndTime();


    /**
     * Executes the task.
     * <p>
     * While this method is running, {@link #getStatus}
     * must return {@link Status#RUNNING}.
     * <p>
     * When it is exited, {@link #getStatus} must return
     * <ul>
     *     <li>
     *         {@link Status#FINISHED}, if it stopped naturally
     *         or because of some internal error,
     *     </li>
     *     <li>
     *         or {@link Status#CANCELED}, if the task was canceled
     *         by system or a user.
     *     </li>
     * </ul>
     */
    void execute();

    /**
     * Cancels the task. Actual effect is up to implementation.
     * <p>
     * After invocation of this method, {@link #getStatus}
     * must return {@link Status#CANCELED}.
     */
    void cancel();
}