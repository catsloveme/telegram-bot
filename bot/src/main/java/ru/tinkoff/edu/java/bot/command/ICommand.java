package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ICommand {
    String  command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {return true; };

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
