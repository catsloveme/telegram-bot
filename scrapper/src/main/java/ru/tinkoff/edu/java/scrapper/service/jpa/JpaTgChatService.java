package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.Links;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Transactional
    @Override
    public void register(long tgChatId) {
        Chat chat = new Chat();
        chat.setId(tgChatId);
        chat.setTrackedLinks(new HashSet<>());
        jpaChatRepository.saveAndFlush(chat);
    }

    @Transactional
    @Override
    public void unregister(long tgChatId) {
        jpaChatRepository.deleteById(tgChatId);
        jpaChatRepository.flush();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getAllChatByLink(String url) {
        Links link = jpaLinkRepository.findByUrl(url);
        Set<Chat> subscribers = link.getSubscribers();
        List<Long> ids = new ArrayList<>();
        for (Chat chat : subscribers) {
            ids.add(chat.getId());
        }
        return ids;
    }

    @Transactional(readOnly = true)
    public List<Long> getAllChats() {
        List<Chat> chats = jpaChatRepository.findAll();
        List<Long> ids = new ArrayList<>();
        for (Chat chat : chats) {
            ids.add(chat.getId());
        }
        return ids;
    }

    public void removeAll() {
        jpaChatRepository.deleteAll();
    }
}
