package filtering.process;

public interface Process<T, R> {

    R process (T data);

}
