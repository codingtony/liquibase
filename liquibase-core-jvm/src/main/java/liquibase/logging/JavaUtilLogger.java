package liquibase.logging;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.core.AbstractLogger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class JavaUtilLogger extends AbstractLogger {

    private java.util.logging.Logger logger;

    public int getPriority() {
        return 5;
    }

    public void setName(String name) {
        logger = java.util.logging.Logger.getLogger(name);
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        if (logLevel == LogLevel.DEBUG) {
            logger.setLevel(Level.FINEST);
        } else if (logLevel == LogLevel.INFO) {
            logger.setLevel(Level.INFO);
        } else if (logLevel == LogLevel.WARNING) {
            logger.setLevel(Level.WARNING);
        } else if (logLevel == LogLevel.SEVERE) {
            logger.setLevel(Level.SEVERE);
        } else if (logLevel == LogLevel.OFF) {
            logger.setLevel(Level.OFF);
        } else {
            throw new UnexpectedLiquibaseException("Unknown log level: " + logLevel);
        }
        super.setLogLevel(logLevel);
    }


    /**
     * @param logLevel
     * @param logFile
     */
    public void setLogLevel(String logLevel, String logFile) {
        Handler fH;

        try {
            fH = new FileHandler(logFile);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot open log file " + logFile + ". Reason: " + e.getMessage());
        }

        fH.setFormatter(new SimpleFormatter());
        logger.addHandler(fH);
        logger.setUseParentHandlers(false);
        setLogLevel(logLevel);

    }

    public void severe(String message) {
        logger.severe(message);
    }

    public void severe(String message, Throwable e) {
        logger.log(Level.SEVERE, message, e);
    }

    public void warning(String message) {
        logger.warning(message);
    }

    public void warning(String message, Throwable e) {
        logger.log(Level.WARNING, message, e);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Throwable e) {
        logger.log(Level.INFO, message, e);
    }

    public void debug(String message) {
        logger.finest(message);

    }

    public void debug(String message, Throwable e) {
        logger.log(Level.FINEST, message, e);
    }

    public Handler[] getHandlers() {
        return logger.getHandlers();
    }

    public void removeHandler(Handler handler) {
        logger.removeHandler(handler);
    }

    public void addHandler(Handler handler) {
        logger.addHandler(handler);
    }

    public void setUseParentHandlers(boolean b) {
        logger.setUseParentHandlers(b);
    }
}
