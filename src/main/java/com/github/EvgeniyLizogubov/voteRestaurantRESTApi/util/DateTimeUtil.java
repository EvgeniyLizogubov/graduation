package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {

    private static final LocalTime VOTING_TIME_LIMIT = LocalTime.of(11, 0);

    public static boolean checkVoteDate(LocalDate voteDate) {
        return (LocalDate.now().isBefore(voteDate) || (LocalDate.now().isEqual(voteDate) && LocalTime.now().isBefore(VOTING_TIME_LIMIT)));
    }
}