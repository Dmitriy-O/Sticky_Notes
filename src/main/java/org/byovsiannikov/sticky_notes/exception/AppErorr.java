package org.byovsiannikov.sticky_notes.exception;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class AppErorr {
    private int status;
    private String massage;
    private Date timeStamp;

    public AppErorr(int status, String massage) {
        this.status = status;
        this.massage = massage;
        timeStamp= new Date();
    }
}
