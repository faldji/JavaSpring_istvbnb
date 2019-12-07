package com.istv.progcomp.form.exception;

@SuppressWarnings("serial")
public class EmailExistException extends Throwable {

    public EmailExistException(String s) {
        super(s);
    }
}
