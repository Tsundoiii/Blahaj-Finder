public enum Color {
    RESET("\u001B[0m"),
    CYAN("\u001B[36m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    RED("\u001B[31m");

    private final String COLOR_CODE;

    private Color(String color_code) {
        COLOR_CODE = color_code;
    }

    public String getColorCode() {
        return COLOR_CODE;
    }
}
