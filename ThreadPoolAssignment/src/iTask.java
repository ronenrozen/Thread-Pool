import java.util.concurrent.Callable;

public interface iTask<T> extends Callable<T> {
    @Override
    T call() throws Exception;
}
