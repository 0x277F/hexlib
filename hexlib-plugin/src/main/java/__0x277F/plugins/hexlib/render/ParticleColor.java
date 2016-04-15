package __0x277F.plugins.hexlib.render;

/**
 * Convenient enum for regular particle colors.
 */
public enum ParticleColor {
    RED(0, 0, 0), BLUE(-255, -255, 255), GREEN(-255, 255, 0), CYAN(-255, 255, 255), YELLOW(255, 255, 0), MAGENTA(255, 0, 255),
    WHITE(255, 255, 255), BLACK(1, 1, 1), BROWN(139, 69, 19);
    public float r, g, b;

    ParticleColor(float r, float g, float b) {
        this.r = r / 255;
        this.g = g / 255;
        this.b = b / 255;
    }
}
