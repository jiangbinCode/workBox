package sample.exception;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-17 15:09
 **/
public class OutMaxNumException extends RuntimeException {

    public OutMaxNumException(String msg) {
        super(msg);
    }

}
