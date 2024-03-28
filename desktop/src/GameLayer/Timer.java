package GameLayer;

public class Timer {
    private float timeRemaining;
    private boolean isRunning;
    private TimerListener listener;

    public Timer(float initialTime) {
        this.timeRemaining = initialTime;
        this.isRunning = false;
    }

    public void start() {
        isRunning = true;
    }

    public void update(float delta) {
        if (isRunning) {
            timeRemaining -= delta;
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                isRunning = false;
                if (listener != null) {
                    listener.onTimerEnd();
                }
            }
        }
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public boolean isRunning() {
        return isRunning;
    }
}

