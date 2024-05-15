package id.ac.ui.cs.advprog.farrel.enums;
import lombok.Getter;

@Getter
public enum TopUpStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String value;

    private TopUpStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (TopUpStatus topUpStatus : TopUpStatus.values()) {
            if (topUpStatus.getValue().equals(param)) {
                return true;
            }
        }

        return false;
    }


}