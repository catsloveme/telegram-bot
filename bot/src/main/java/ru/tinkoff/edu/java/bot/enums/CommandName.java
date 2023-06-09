package ru.tinkoff.edu.java.bot.enums;

public enum CommandName {

    START("/start"),
    HELP("/help"),
    TRACK("/track"),
    UNTRACK("/untrack"),
    LIST("/list");

    private final String cmd;

    CommandName(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return this.cmd;
    }

}
