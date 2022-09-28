package ru.practicum.mainserver.common.enums;

public enum EventState {
    PENDING("PENDING"),

    PUBLISHED("PUBLISHED"),

    CANCELED("CANCELED");

    private final String value;

    EventState(String value) {
        this.value = value;
    }

    public static EventState fromValue(String text) {
        for (EventState b : EventState.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
