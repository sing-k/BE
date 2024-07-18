
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SimpleTest {

    @Test
    void LocalDate를_테스트한다() {
        // given
        Random random = new Random(18);
        int bound = 10;
        List<History> histories = List.of(
                new History(LocalDateTime.now().minusDays(5), random.nextInt(bound) + 1),
                new History(LocalDateTime.now().minusDays(5), random.nextInt(bound) + 1),
                new History(LocalDateTime.now().minusDays(4), random.nextInt(bound) + 1),
                new History(LocalDateTime.now().minusDays(4), random.nextInt(bound) + 1),
                new History(LocalDateTime.now().minusDays(3), random.nextInt(bound) + 1),
                new History(LocalDateTime.now().minusDays(2), random.nextInt(bound) + 1),
                new History(LocalDateTime.now(), random.nextInt(bound))
        );
        System.out.println("Initial Activity History ----");
        for (History history : histories) {
            System.out.println(history);
        }

        // when
        List<History> accumulateHistories = new ArrayList<>();
        int accumulateScore = 0;
        for (History history : histories) {
            accumulateScore += history.getScore();
            accumulateHistories.add(new History(history.getCreatedAt(), accumulateScore));
        }

        System.out.println("Accumulate Activity History ----");
        for (History history : accumulateHistories) {
            System.out.println(history);
        }

        // then

        List<History> resultHistory = new ArrayList<>();
        List<LocalDate> dates = LocalDate.now().minusDays(6).datesUntil(LocalDate.now().plusDays(1)).toList();
        int prev = 0;
        for(LocalDate date : dates) {
            List<History> h = accumulateHistories.stream()
                    .filter(score -> score.getCreatedAt().isAfter(date.atStartOfDay()) && score.getCreatedAt().isBefore(date.plusDays(1).atStartOfDay()))
                    .sorted(Comparator.comparing(History::getCreatedAt).reversed())
                    .toList();

            if (h.isEmpty()) resultHistory.add(new History(date.atStartOfDay(), prev));
            else {
                prev = h.get(0).getScore();
                resultHistory.add(new History(date.atStartOfDay(), prev));
            }
        }

        System.out.println("Result Activity History ----");
        for (History history : resultHistory) {
            System.out.println(history);
        }

        System.out.println("Weekly Sunday LocalDate ----");
        List<LocalDate> weeks = new ArrayList<>(LocalDate.now().minusMonths(2).datesUntil(LocalDate.now().plusDays(1))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .toList());

        if (LocalDate.now().getDayOfWeek() != DayOfWeek.SUNDAY) weeks.add(LocalDate.now());

        for (LocalDate week : weeks) {
            System.out.println(week);
        }
    }

    @Getter
    @ToString
    public static class History {
        private LocalDateTime createdAt;
        private int score;

        public History(LocalDateTime createdAt, int score) {
            this.createdAt = createdAt;
            this.score = score;
        }
    }

}
