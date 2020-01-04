package com.example.command;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class DuckCommand extends BotCommand {
    private static final String URL = "https://yandex.by/images/search?text=duck%20quau&from=tabbar";
    private static final String IMG_CLASS = "serp-item__thumb.justifier__thumb";
    private static final String DIV_CLASS = "serp-item";
    public DuckCommand() {
        super("quack", "some крякер from ohremchuk");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage response = new SendMessage();
        StringBuilder sb = new StringBuilder();
        SendPhoto sendPhoto = new SendPhoto();
        Long chatId = chat.getId();



        response.setChatId(chatId.toString());
        sendPhoto.setChatId(chatId.toString());

        Document doc = null;

        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            Elements images = doc.select("div." + DIV_CLASS + " img." + IMG_CLASS);
            Element image = images.get(getRandomNumberOfImage(images.size()));

            String src = image.absUrl("src");
            String dataSrc = image.absUrl("data-src");

            sendPhoto.setCaption("Some крякер");
            if (!src.equals("")) {
                sendPhoto.setPhoto(src);
            } else if(!dataSrc.equals("")) {
                sendPhoto.setPhoto(dataSrc);
            } else {
                try {
                    absSender.execute(response.setText("Nothing :("));
                    return;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            absSender.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumberOfImage(int sizeImages) {
       return (int) (Math.floor(Math.random() * (sizeImages - 1)));
    }
}
