package lorgar.avrelian.base.service;

public interface TaskExecutable<M, T> {
    T doTask(M input);
}
