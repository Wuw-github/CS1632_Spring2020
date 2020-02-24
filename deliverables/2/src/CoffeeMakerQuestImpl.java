import java.util.*;

enum Item {
	NONE,
	COFFEE,
	CREAM,
	SUGAR
}

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	Player p;
	boolean room_exists = false;
	ArrayList<Room> allRooms = new ArrayList<Room>();
	int currentRoom = -1;
	boolean gameOver;
	
	CoffeeMakerQuestImpl() {
		// TODO
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		if(gameOver)
		{

			return true;

		}

		return false;
		
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		// TODO
		this.p = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		if(room_exists)
		{

			return false;

		}
		else
		{

			allRooms.add(room);
			room_exists = true;
			return true;

		}
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the northern-most room
	 * @param northDoor string to label the south door of room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		// TODO
		if(room == null || northDoor == null || southDoor == null)
			return false;
		int room_num = allRooms.size();
		//System.out.println("room size is "+room_num);
		if(room_num == 0) return false;
		Room northern = allRooms.get(room_num-1);
		assert(northern!=null);
		//if the room is not unique, return false
		if(!check_unique(room, room_num))
			return false;
		//add room here
		allRooms.add(room);
		//System.out.println("the name is "+northDoor);
		northern.setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		return true;
	}
	private boolean check_unique(Room room, int room_num){
		for(int i=0; i < room_num; i++){
			Room cur = allRooms.get(i);
			if(cur.getAdjective().equals(room.getAdjective()) || cur.getFurnishing().equals(room.getFurnishing()))
				return false;
		}
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		// TODO
		if(currentRoom == -1)
		{

			return null;

		}
		else
		{

			return allRooms.get(currentRoom);

		}
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		// TODO
		int temp = -1;

		for(int i = 0; i < allRooms.size(); i++)
		{

			if(room.getFurnishing().equals(allRooms.get(i).getFurnishing()) && room.getAdjective().equals(allRooms.get(i).getAdjective()))
			{

				temp = i;

			}

		}

		if(temp == -1)
		{

			return false;

		}
		else
		{

			currentRoom = temp;
			return true;

		}
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		// TODO
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		// TODO
			String command = cmd.toUpperCase();

			if(command.equals("N"))
			{

				if(currentRoom == (allRooms.size() -1))
				{

					return "A door in that direction does not exist.\n";

				}

				currentRoom++;
				return "";

			}
			else
			{

				if(command.equals("S"))
				{

					if(currentRoom == 0)
					{

						return "A door in that direction does not exist.\n";

					}

					currentRoom--;
					return "";

				}
				else
				{

					if(command.equals("L"))
					{

						if(allRooms.get(currentRoom).getItem() == Item.NONE)
						{

							return "You don't see anything out of the ordinary.\n";

						}
						else
						{

							if(allRooms.get(currentRoom).getItem() == Item.CREAM)
							{
								p.addItem(Item.CREAM);
								return "There might be something here...\nYou found some creamy cream!\n";
							}
							else
							{

								if(allRooms.get(currentRoom).getItem() == Item.SUGAR)
								{
									p.addItem(Item.SUGAR);
									return "There might be something here...\nYou found some sweet sugar!\n";

								}
								else
								{
									p.addItem(Item.COFFEE);
									return "There might be something here...\nYou found some caffeinated coffee!\n";

								}

							}

						}

					}
					else
					{

						if(command.equals("I"))
						{

							return p.getInventoryString();

						}
						else
						{

							if(command.equals("D"))
							{

								gameOver = true;
								
								if(p.checkCoffee() && p.checkCream() && p.checkSugar())

									return "\nYou drink the beverage and are ready to study!\nYou win!\n";
								
								else if(p.checkCoffee() && p.checkCream() && !p.checkSugar())
									
									return p.getInventoryString()+ "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
								
								else if(p.checkCoffee() && !p.checkCream() && p.checkSugar())
								
									return p.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
								
								else if(!p.checkCoffee() && p.checkCream() && p.checkSugar())
								
									return p.getInventoryString() + "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
								
								else if(!p.checkCoffee() && !p.checkCream() && p.checkSugar())

									return p.getInventoryString() + "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
								
								else if(!p.checkCoffee() && p.checkCream() && !p.checkSugar())
								
									return p.getInventoryString() + "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
								
								else if(p.checkCoffee() && !p.checkCream() && !p.checkSugar())

									return p.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";

								else
									return "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";

							}
							else
							{

								if(command.equals("H"))
								{

									return "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";

								}

							}

						}

					}

				}

			}

		return "";
	}
	
}
