package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;

public class IsDateWithinRange {

    public static boolean isDateWithinRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }
}
