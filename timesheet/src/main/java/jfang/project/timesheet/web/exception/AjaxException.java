package jfang.project.timesheet.web.exception;

/**
 * Created by jfang on 4/12/2016.
 */
public class AjaxException extends RuntimeException {

    private int status;
    private String exception;

    public AjaxException(int status, String exception) {
        this.status = status;
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return status + " " + exception;
    }
}
