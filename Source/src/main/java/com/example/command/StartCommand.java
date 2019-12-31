package com.example.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {
    private ICommandRegistry iCommandRegistry;

    public StartCommand() {
        super("/start", "start using bot\n");
        this.iCommandRegistry = new CommandRegistry(false, com.example.Constants.TOKEN);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage response = new SendMessage();
        response.setChatId(chat.getId().toString());

        StringBuilder sbText = new StringBuilder();
        sbText.append("Hello, ").append(user.getFirstName()).append("!\n");
        sbText.append("<b>Available commands:</b> ");
        iCommandRegistry.getRegisteredCommands()
                .forEach(cmd -> sbText.append(cmd.toString()).append("\n"));

        response.enableHtml(true);
        response.setText(sbText.toString());
        try {
            absSender.execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
