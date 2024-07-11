import java.io.*;

public class Bomb {
    public static final int START = 123;
    public static final int CODE_LENGTH = 5;

    public static void defuse(String code) throws Exception {
        if (code.length() != CODE_LENGTH) {
            throw new Bomb.Explosion();
        }

        String curr = Integer.toHexString(START);
        for (int i = 0; i < CODE_LENGTH; i++) {
            curr = scramble(curr);
            if (curr.charAt(0) != code.charAt(i)) {
                throw new Bomb.Explosion();
            }
        }
    }

    private static String scramble(String secret) {
        return Integer.toHexString(Integer.parseInt(secret, 16) * 0xA9 / 0b010011);
    }

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("Bomb armed and ready for defusal.");

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
        defuse("421b6"); // TODO: Find the code, hurry!
        System.setOut(SYSTEM_OUT);

        System.out.println("Bomb defused! You saved the day!");
        System.out.println();
    }

    public static final PrintStream SYSTEM_OUT = System.out;

    private static class Explosion extends RuntimeException {
        public Explosion() {
            super("BOOM");
        }
    }
}
