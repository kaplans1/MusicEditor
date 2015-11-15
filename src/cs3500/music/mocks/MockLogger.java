package cs3500.music.mocks;

public class MockLogger {

    private static MockLogger mockLogger = new MockLogger();
    private static StringBuilder logger = new StringBuilder();

    private MockLogger() {}

    public static MockLogger getInstance() {
        return mockLogger;
    }

    protected static void log(String message) {
        logger.append(message + "\n");
    }
    public static String getLog() {
        return logger.toString();
    }
}
