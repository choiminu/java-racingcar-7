package racingcar.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import camp.nextstep.edu.missionutils.test.Assertions;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.CarInfo;
import racingcar.domain.car.Cars;
import racingcar.utils.NumberGenerator;
import racingcar.utils.RandomNumberGenerator;

class GameManagerTest {

    GameRule gameRule;
    NumberGenerator numberGenerator;
    WinnerDeterminer winnerDeterminer;
    GameManager gameManager;

    @BeforeEach
    void beforeEach() {
        gameRule = new WootecoRule();
        numberGenerator = new RandomNumberGenerator();
        winnerDeterminer = new WinnerDeterminer(gameRule);
        gameManager = new GameManager(gameRule, numberGenerator, winnerDeterminer);
    }

    @Test
    public void 무작위_숫자가_4이상_일경우_모든_자동차는_전진한다() {
        Cars cars = new Cars(Arrays.asList(new Car("povi"), new Car("min")));

        Assertions.assertRandomNumberInRangeTest(
                () -> {
                    gameManager.moveCarsBasedOnRule(cars);
                    List<CarInfo> carInfos = gameManager.retrieveCarStatus(cars);
                    carInfos.forEach(carInfo -> assertEquals(1, carInfo.getCurrentPosition())
                    );
                },
                4
        );
    }

    @Test
    public void 무작위_숫자가_4미만_일경우_모든_자동차는_전진하지_않는다() {
        Cars cars = new Cars(Arrays.asList(new Car("povi"), new Car("min")));

        Assertions.assertRandomNumberInRangeTest(
                () -> {
                    gameManager.moveCarsBasedOnRule(cars);
                    List<CarInfo> carInfos = gameManager.retrieveCarStatus(cars);
                    carInfos.forEach(carInfo -> assertEquals(0, carInfo.getCurrentPosition())
                    );
                },
                3
        );
    }

    @Test
    public void 자동차들의_상태를_반환한다() {
        // given
        Car car1 = new Car("povi");
        Car car2 = new Car("min");
        Cars cars = new Cars(Arrays.asList(car1, car2));

        // when
        List<CarInfo> carInfos = gameManager.retrieveCarStatus(cars);

        // then
        assertThat(carInfos)
                .extracting(CarInfo::getName)
                .containsExactlyInAnyOrder("povi", "min");

        assertThat(carInfos)
                .extracting(CarInfo::getCurrentPosition)
                .containsExactlyInAnyOrder(0, 0);
    }

    @Test
    public void 우승자들의_이름을_반환한다() {
        //given
        Car car1 = new Car("povi");
        Car car2 = new Car("min");
        Cars cars = new Cars(Arrays.asList(car1, car2));

        //when
        car1.accelerate();
        List<String> winners = gameManager.determineWinners(cars);

        //then
        assertThat(winners).contains("povi");
    }
}