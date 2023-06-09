package ru.tinkoff.edu.java.scrapper.service.jooq;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {
    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Override
    public void register(long tgChatId) {
        jooqChatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        jooqChatRepository.remove(tgChatId);
    }

    @Override
    public List<Long> getAllChatByLink(String link) {
        return jooqChatRepository.findAllByLink(link);
    }

    public List<Long> getAllChats() {
        return jooqChatRepository.getAllChats();
    }

    public void removeAll() {
        jooqChatRepository.removeAll();
    }

}
