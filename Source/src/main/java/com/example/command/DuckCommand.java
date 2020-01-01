package com.example.command;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class DuckCommand extends BotCommand {
    private static final String URL = "https://yandex.by/images/search?text=%D0%BA%D1%80%D1%8F&from=tabbar";
    private static final String IMG_CLASS = "serp-item__thumb.justifier__thumb";
    private static final String DIV_CLASS = "serp-item";
    public DuckCommand() {
        super("quack", "some крякер from ohremchuk");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage response = new SendMessage();
        response.setChatId(chat.getId().toString());
        StringBuilder sb = new StringBuilder();

        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements images;
        if (doc != null) {
            images = doc.select("div." + DIV_CLASS + " img." + IMG_CLASS);
            Element image = images.get(getRandomNumberOfImage(images.size()));

            String src = image.absUrl("src");
            String dataSrc = image.absUrl("data-src");
            sb.append("Some крякер:\n");
            if (!src.equals("")) {
                sb.append(image.absUrl("src")).append("\n");
            } else if(!dataSrc.equals("")) {
                sb.append(image.absUrl("data-src")).append("\n");
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
            response.enableHtml(true);
            response.setText(sb.toString());
            absSender.execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumberOfImage(int sizeImages) {
       return (int) (Math.floor(Math.random() * (sizeImages - 1)));
    }
}
