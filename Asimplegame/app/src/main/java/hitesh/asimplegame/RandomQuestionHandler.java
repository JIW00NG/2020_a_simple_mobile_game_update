package hitesh.asimplegame;

public class RandomQuestionHandler {
    private RandomQuestion newRQ;
    private RandomQuestion resetRQ = null;
    private boolean next = false;
    public RandomQuestionHandler() {
        newRQ = new RandomQuestion("DND");
    }

    RandomQuestion getNewRQ() {
        setNewRQ();
        return newRQ;
    }

    RandomQuestion getResetRQ() {
        return resetRQ;
    }

    public void setResetRQ(RandomQuestion RQ) {
        resetRQ = RQ;
    }

    public void setNewRQ() {
        newRQ = new RandomQuestion("DND");
    }

    public boolean getNextBoolean() {
        return next;
    }

    public void setNext(boolean TF) {
        next = TF;
    }
}
