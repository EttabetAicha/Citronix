package org.aicha.citronix.domain.enums;

public enum Status {
    YOUNG(0, 3, 2.5),
    MATURE(3, 10, 12.0),
    OLD(10, Integer.MAX_VALUE, 20.0);

    private final int minAge;
    private final int maxAge;
    private final double productivity;

    Status(int minAge, int maxAge, double productivity) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.productivity = productivity;
    }

    public static double getProductivityByAge(int age) {
        for (Status status : values()) {
            if (age >= status.minAge && age < status.maxAge) {
                return status.productivity;
            }
        }
        throw new IllegalArgumentException("Invalid age: " + age);
    }
}