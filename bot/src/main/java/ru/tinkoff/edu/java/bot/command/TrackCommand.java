package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinkResponse;

@Component
public class TrackCommand implements ICommand {
    public static final String ERROR_MESSAGE =
        "Ссылка не добавлена в список отслеживаемых ссылок, введите ее сразу после команды /track";
    public static final String EXISTING_LINK_MESSAGE = "У Вас проблемы с памятью, ссылка %s и так уже отслеживается!";
    private static final String SUCCESSFUL_MESSAGE =
        "Вы успешно подписались на ссылку %s, теперь вы будете получать уведомления об изменениях";
    @Autowired
    private final ScrapperClient scrapperClient;

    public TrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        String message = update.message().text();
        long chatId = update.message().chat().id();
        String[] words = splitMessageIntoWords(message);
        if (words[1] == null) {
            return new SendMessage(chatId, ERROR_MESSAGE);
        } else {
            ListLinkResponse listLinks = scrapperClient.getTrackedLinks(chatId);
            for (LinkResponse link : listLinks.links()) {
                if (link.url().equals(words[1])) {
                    return new SendMessage(chatId, String.format(EXISTING_LINK_MESSAGE, words[1]));
                }
            }
            scrapperClient.addLinkToTrack(String.valueOf(chatId), words[1]);
            return new SendMessage(chatId, String.format(SUCCESSFUL_MESSAGE, words[1]));

        }
    }

    private String[] splitMessageIntoWords(String message) {
        if (message == null) {
            return new String[0];
        }
        String[] words = message.split(" ", 2);
        if (words.length > 1) {
            return words;
        } else {
            return new String[] {words[0], null};
        }
    }
    //добавить проверку на
    // -корректность ввода ссылки
    // -регитрацию ссылки
}
