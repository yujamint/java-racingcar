package racing.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CarsTest {
    private static final MovingStrategy ALWAYS_MOVING_STRATEGY = () -> true;

    @DisplayName("쉼표로 구분된 여러 자동차 이름을 받아 Cars 컬렉션 객체를 생성한다.")
    @Test
    void makeCars() {
        String carNames = "pobi,brown";

        Cars cars = Cars.generate(carNames, ALWAYS_MOVING_STRATEGY);
        List<String> names = cars.getCars()
                .stream()
                .map(Car::getName)
                .collect(Collectors.toList());

        assertThat(names).containsExactly("pobi", "brown");
    }

    @DisplayName("잘못된 이름이 포함되어 있는 문자열은 Cars 컬렉션 객체 생성 예외가 발생한다.")
    @Test
    void cannotMakeCarsWithWrongName() {
        String carNames = "pobi,bro.wn,";

        assertThatCode(() -> {
            Cars.generate(carNames, ALWAYS_MOVING_STRATEGY);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자동차 이름은 5자 이하의 알파벳으로 구성되어야 합니다.");
    }

    @DisplayName("Cars 컬렉션 생성시 필요한 최소 플레이 인원이 2명 미만인 경우 예외가 발생한다.")
    @Test
    void cannotMakeCarsWithOnlyOnePlayer() {
        String carNames = "pobi";

        assertThatCode(() -> {
            Cars.generate(carNames, ALWAYS_MOVING_STRATEGY);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("게임 참가자는 최소 2명 이상이어야 합니다.");
    }

    @DisplayName("Cars 컬렉션 생성시 필요한 자동차 이름들 중 중복이 있으면 예외가 발생한다.")
    @Test
    void cannotMakeCarsWithDuplication() {
        String carNames = "pobi,brown,jiko,pobi";

        assertThatCode(() -> {
            Cars.generate(carNames, ALWAYS_MOVING_STRATEGY);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 자동차 이름이 존재합니다.");
    }

    @DisplayName("Cars 컬렉션 원소들 중 가장 멀리 이동한 자동차 명단이 우승자 명단이다.")
    @Test
    void findWinnerNames() {
        String carNames = "pobi,brown,java";
        Cars cars = Cars.generate(carNames, ALWAYS_MOVING_STRATEGY);

        cars.race();
        List<String> winnerNames = cars.getCars()
                .stream()
                .map(Car::getName)
                .collect(Collectors.toList());

        assertThat(winnerNames).containsExactly("pobi", "brown", "java");
    }
}