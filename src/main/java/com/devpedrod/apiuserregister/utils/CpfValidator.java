package com.devpedrod.apiuserregister.utils;

public class CpfValidator {
    // CPF
    private static final int[] weightSsn = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    private static int calculate(final String str) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * CpfValidator.weightSsn[CpfValidator.weightSsn.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    public static boolean isValidCPF(final String ssn) {
        if ((ssn == null) || (ssn.length() != 11) || ssn.matches(ssn.charAt(0) + "{11}")){
            return false;
        }
        final int digit1 = calculate(ssn.substring(0, 9));
        final int digit2 = calculate(ssn.substring(0, 9) + digit1);
        return ssn.equals(ssn.substring(0, 9) + digit1 + digit2);
    }
}
