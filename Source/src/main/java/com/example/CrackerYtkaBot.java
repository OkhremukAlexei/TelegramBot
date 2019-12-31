package com.example;

import com.example.command.StartCommand;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CrackerYtkaBot extends TelegramLongPollingCommandBot {
    public CrackerYtkaBot(DefaultBotOptions defaultBotOptions) {
        super(defaultBotOptions);
        register(new StartCommand());
    }

    @Override
    public String getBotUsername() {
        return Constants.USERNAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("Нету такой команды :(");
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return Constants.TOKEN;
    }
}
