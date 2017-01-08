package me.EctoDev.Krypter.command;

import java.util.ArrayList;

import me.EctoDev.Krypter.Krypter;
import me.EctoDev.Krypter.commands.Coords;
import me.EctoDev.Krypter.commands.Login;

public class CommandManager {
	private ArrayList<Command> commands;

	public CommandManager() {
		commands = new ArrayList();
addCommand(new Coords());
addCommand(new Login());

	}

	public void addCommand(Command c) {
		commands.add(c);
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void callCommand(String input){
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for(Command c: getCommands()){
			if(c.getAlias().equalsIgnoreCase(command)){
				try{
					c.onCommand(args, args.split(" "));
				}catch(Exception e){
					Krypter.addChatMessage("Invalid Command Usage!");
					Krypter.addChatMessage(c.getSyntax());
				}
				return;
			}
		}
	}

}