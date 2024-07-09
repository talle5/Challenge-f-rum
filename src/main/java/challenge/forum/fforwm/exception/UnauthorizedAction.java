package challenge.forum.fforwm.exception;

public class UnauthorizedAction extends RuntimeException {
    public UnauthorizedAction() {
        super("Ação não authorizada");
    }
}
