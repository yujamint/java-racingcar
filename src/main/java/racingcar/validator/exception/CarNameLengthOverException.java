package racingcar.validator.exception;

public class CarNameLengthOverException extends RuntimeException {
    public CarNameLengthOverException(int length) {
        super("자동차 이름은 5자 이하여야 합니다. 입력된 글자 수 : " + length);
    }
}