package com.lim1t.email.domain.mail;

public enum MailPurpose {
    AUTH("AUTH"), WELCOME("WELCOME");

    private final String value;

    MailPurpose(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
