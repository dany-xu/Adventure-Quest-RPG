package event;

public abstract class AbstractEvent implements Event {
    /**
     * 并无太大实际意义,主要是操作一下抽象类
     * @apiNote
     */
    private boolean triggered;

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }
}
