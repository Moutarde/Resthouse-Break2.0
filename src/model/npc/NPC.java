package model.npc;

import gui.sprite.Posture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Coord;
import model.player.Player;
import model.rooms.Room;
import controller.Direction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class NPC extends Player {
	private static String npcDescriptorFilePath = "/characters/characters.txt";
	private static HashMap<String, NPC> npcList;

	private String name;
	private List<Direction> moveScript = new ArrayList<Direction>();
	private int currentStepInScript = 0;

	private Coord spriteCoord;
	
	public NPC(Coord coord, Posture posture, int id, String name, Room startRoom, List<Direction> moveScript, Coord spriteCoord) {
		super(id, startRoom, coord, posture);
		this.name = name;
		
		if (!moveScript.isEmpty()) {
			this.moveScript.addAll(moveScript);
			this.getMove().setDir(moveScript.get(0));
		}
		
		this.spriteCoord = spriteCoord;
	}
	
	public String getName() {
		return name;
	}
	
	public Coord getSpriteCoord() {
		return spriteCoord;
	}

	public Direction getCurrentDirectionInScript() {
		return moveScript.isEmpty() ? Direction.NONE : moveScript.get(currentStepInScript);
	}

	public void goToNextStepOfScript() {
		++currentStepInScript;
		if (currentStepInScript >= moveScript.size()) {
			currentStepInScript = 0;
		}
		
		Direction nextDir = moveScript.get(currentStepInScript);
		if (getRoom().canWalkOnSquare(getNextSquare(nextDir), getId())) {
			startMove(nextDir);
		}
	}
	
	public static NPC getNPC(String id) {
		return npcList.get(id);
	}

	public static List<NPC> getNPCsInRoom(Room room) {
		List<NPC> returnList = new ArrayList<NPC>();
		for (NPC npc : npcList.values()) {
			if (npc.getRoom() == room) {
				returnList.add(npc);
			}
		}
		return returnList;
	}

	public static void createNPC() throws IOException {
		npcList = new HashMap<String, NPC>();
		
		URL url = NPC.class.getResource(npcDescriptorFilePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		assert reader != null;
		
		String line = null;
		String currentNPCId = null;
		int currentNumericId = 100;
		String currentNPCName = null;
		String currentNPCStartRoom = null;
		Coord currentNPCStartCoord = null;
		Posture currentNPCStartPosture = null;
		List<Direction> currentNPCMoveScript = new ArrayList<Direction>();
		Coord currentNPCspriteCoord = null;
		
		while ((line = reader.readLine()) != null) {
			// ID
			if (line.startsWith("C_")) {
				currentNPCId = line;
				++currentNumericId;
			}
			// NAME
			else if (line.startsWith("name=")) {
				String[] tokens = line.split("=");
				currentNPCName = tokens[1];
			}
			// START ROOM
			else if (line.startsWith("startRooom=")) {
				String[] tokens = line.split("=");
				currentNPCStartRoom = tokens[1];
			}
			// START COORD
			else if (line.startsWith("startCoord=")) {
				String[] tokens = line.split("=");
				String[] coords = tokens[1].split(",");
				currentNPCStartCoord = new Coord(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
			}
			// START POSTURE
			else if (line.startsWith("startPosture=")) {
				String[] tokens = line.split("=");
				String posture = tokens[1];
				
				assert posture.startsWith("LOOK_") : "Start postures for characters have to be static (LOOK_...). Value found : " + posture;
				
				if (posture.equals("LOOK_UP")) {
					currentNPCStartPosture = Posture.LOOK_UP;
				}
				else if (posture.equals("LOOK_RIGHT")) {
					currentNPCStartPosture = Posture.LOOK_RIGHT;
				}
				else if (posture.equals("LOOK_DOWN")) {
					currentNPCStartPosture = Posture.LOOK_DOWN;
				}
				else if (posture.equals("LOOK_LEFT")) {
					currentNPCStartPosture = Posture.LOOK_LEFT;
				}
				else {
					assert false : "Start postures for characters have to be static (LOOK_...). Value found : " + posture;
				}
			}
			// MOVE SCRIPT
			else if (line.startsWith("moveScript=")) {
				String[] tokens = line.split("=");
				String[] script = tokens[1].split(";");

				for (String move : script) {
					Direction dir = Direction.getDirectionFromString(move);
					currentNPCMoveScript.add(dir);
				}
			}
			// START COORD
			else if (line.startsWith("spriteCoord=")) {
				String[] tokens = line.split("=");
				String[] coords = tokens[1].split(",");
				currentNPCspriteCoord = new Coord(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
			}
			// END LINE -> construct item
			else if (line.equals("END"))
			{
				Room startRoom = Room.getRoom(currentNPCStartRoom);
				
				NPC npc = new NPC(currentNPCStartCoord, currentNPCStartPosture, currentNumericId, currentNPCName, startRoom, currentNPCMoveScript, currentNPCspriteCoord);
				npcList.put(currentNPCId, npc);
				
				currentNPCId = null;
				currentNPCName = null;
				currentNPCStartRoom = null;
				currentNPCStartCoord = null;
				currentNPCStartPosture = null;
				currentNPCMoveScript.clear();
			}
		}
		
		reader.close();
	}

}
