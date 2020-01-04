package com.example.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartCommand extends BotCommand {
    private ICommandRegistry commandRegistry;

    public StartCommand(ICommandRegistry commandRegistry) {
        super("/start", "start using bot\n");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage response = new SendMessage();
        response.setChatId(chat.getId().toString());

        StringBuilder sbText = new StringBuilder();
        sbText.append("Hello, ").append(user.getFirstName()).append("!\n");
        sbText.append("<b>Available commands:</b>\n");
        commandRegistry.getRegisteredCommands()
                .forEach(cmd -> sbText.append("/").append(cmd.getCommandIdentifier())
                        .append(":\n").append(cmd.getDescription()).append("\n"));

        response.enableHtml(true);
        response.setText(sbText.toString());

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        response.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("/quack"));
        keyboardSecondRow.add(new KeyboardButton("/quack"));
        keyboardSecondRow.add(new KeyboardButton("/quack"));
        keyboardSecondRow.add(new KeyboardButton("/quack"));

        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        try {
            absSender.execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
