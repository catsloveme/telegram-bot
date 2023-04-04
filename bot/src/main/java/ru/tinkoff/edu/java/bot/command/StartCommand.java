package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;


public class StartCommand implements ICommand {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Вы успешно зарегистрировались, для подробной информации введите команду /help";
    }

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();
        long chatId = message.chat().id();
            return new SendMessage(chatId,description());
        }
    }

