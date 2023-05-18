public enum Color {
    RESET("\u001B[0m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m");

    private final String COLOR_CODE;

    private Color(String color_code) {
        COLOR_CODE = color_code;
    }

    public String getColorCode() {
        return COLOR_CODE;
    }
}
