package fr.schouvey.william.treasuremap.domain;

import fr.schouvey.william.treasuremap.exception.InvalidOrientationException;

import java.util.Arrays;

public enum OrientationEnum {
    NORTH("N", "W", "E"),
    SOUTH("S", "E", "W"),
    EAST("E", "N", "S"),
    WEST("W", "S", "N");

    private final String code;

    private final String rightCode;

    private final String leftCode;

    OrientationEnum(String code, String leftCode, String rightCode) {
        this.code = code;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
    }

    public String getCode() {
        return code;
    }

    public static OrientationEnum getOrientationByCode(String code) {
        return Arrays.stream(OrientationEnum.values()).filter(o -> o.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidOrientationException(code));
    }
    public OrientationEnum getRight() {
        return getOrientationByCode(this.rightCode);
    }

    public OrientationEnum getLeft() {
        return getOrientationByCode(this.leftCode);
    }
}
