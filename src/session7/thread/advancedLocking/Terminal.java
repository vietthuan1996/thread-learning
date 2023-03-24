package session7.thread.advancedLocking;

import java.lang.reflect.Field;

public class Terminal {
    public static void main(String[] args) throws Exception {
        ImmutableObject immutableObject = new ImmutableObject(10);
        System.out.println("Original Value: " + immutableObject.getValue());

        Field valueField = ImmutableObject.class.getDeclaredField("value");
        valueField.setAccessible(true);
        valueField.setInt(immutableObject, 20);

        System.out.println("Modified Value: " + immutableObject.getValue());
    }
}

final class ImmutableObject {
    private final int value;

    public ImmutableObject(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
