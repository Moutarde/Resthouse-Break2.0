/**
 * 
 */
package model.rooms;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import model.Coord;
import model.Resource;
import model.chests.Chest;
import model.chests.Item;

/**
 * @author Nicolas
 *
 */
public class Room {
	private static String roomsDescriptorFilePath = "/room/Rooms.txt";
	private static HashMap<String, Room> roomList;
	
	private Resource res;
	private Matrix mat;
	private Matrix evolutiveMat;
	private HashMap<Integer, Room> neighbors;
	private HashMap<Integer, Integer> neighborsDoorsIds;
	private HashMap<Integer, Chest> chests;
	
	private BufferedImage img;

	private Room(Resource res, Matrix mat) {
		this.res = res;
		this.mat = mat;
		this.evolutiveMat = new Matrix(mat);
		this.neighbors = new HashMap<Integer, Room>();
		this.neighborsDoorsIds = new HashMap<Integer, Integer>();
		this.chests = new HashMap<Integer, Chest>();
	}

	public Resource getRes() {
		return res;
	}

	public void setRes(Resource res) {
		this.res = res;
	}

	public Matrix getMat() {
		return mat;
	}
	
	public void loadImg() {
		img = res.getBufferedImage();
	}
	
	public void unloadImg() {
		img = null;
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public boolean canWalkOnSquare(Coord c, int id) {
		SquareType evolutiveMatType = getSquareTypeFromEvolutiveMat(c);
		
		if (evolutiveMatType == SquareType.CHARACTER && evolutiveMat.getSquareValue(c) != id) {
			return false;
		}
		
		SquareType type = getSquareType(c);

		if (type == SquareType.FREESQUARE || type == SquareType.DOOR) {
			return true;
		}
		
		return false;
	}

	public boolean isChest(Coord c) {
		SquareType type = getSquareType(c);
		
		if (type == SquareType.CHEST) {
			return true;
		}
		
		return false;
	}
	
	public SquareType getSquareType(Coord c) {
		int squareValue = mat.getSquareValue(c);

		if (squareValue == -1) {
			return SquareType.OUTSIDE;
		}
		else if (squareValue == 0) {
			return SquareType.OBSTACLE;
		}
		else if (squareValue == 1) {
			return SquareType.FREESQUARE;
		}
		else if (squareValue >= 10 && squareValue < 30) {
			return SquareType.DOOR;
		}
		else if (squareValue >= 30 && squareValue < 40) {
			return SquareType.CHEST;
		}
		else {
			assert false : "Square type unknown : " + squareValue;
			return null;
		}
	}
	
	public SquareType getSquareTypeFromEvolutiveMat(Coord c) {
		int squareValueFromEvolutiveMat = evolutiveMat.getSquareValue(c);
		
		if (squareValueFromEvolutiveMat >= 100 && squareValueFromEvolutiveMat < 200) {
			return SquareType.CHARACTER;
		}
		else {
			return SquareType.FREESQUARE;
		}
	}

	public void setPlayerOnSquare(int id, Coord newCoord) {
		evolutiveMat.setSquareValue(id, newCoord);
	}

	public void freeSquare(Coord coord) {
		evolutiveMat.setSquareValue(1, coord);
	}

	private void addNeighbor(int doorId, Room newNeighbor, int neighborLinkedDoorId) {
		neighbors.put(doorId, newNeighbor);
		neighborsDoorsIds.put(doorId, neighborLinkedDoorId);
	}

	public Room getNeighbor(int doorId) {
		return neighbors.get(doorId);
	}

	public Coord getStartingCoordFromDoor(int doorId) {
		Room neighbor = neighbors.get(doorId);
		if (neighbor == null) {
			System.out.println("No neighbor room found from door " + doorId);
			return null;
		}
		
		int neighborDoorId = neighborsDoorsIds.get(doorId);
		return neighbor.getMat().getCoordFromValue(neighborDoorId);
	}

	public Chest getChest(int chestId) {
		return chests.get(chestId);
	}
	
	public static Room createRooms() throws IOException {
		roomList = new HashMap<String, Room>();
		
		URL url = Room.class.getResource(roomsDescriptorFilePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		assert reader != null;
		
		Room startRoom = null;
		StringTokenizer splitter;
		String line = null;
		String currentRoomName = null;
		String currentRoomRes = null;
		int currentRoomNbCols = 0;
		int currentRoomNbLines = 0;
		List<String> values = new ArrayList<String>();
		HashMap<Integer, Chest> currentRoomChestList = new HashMap<Integer, Chest>();
		
		while ((line = reader.readLine()) != null) {
			// NAME
			if (line.startsWith("R_")) {
				currentRoomName = line;
			}
			// RESOURCE
			else if (line.startsWith("image=")) {
				String[] tokens = line.split("=");
				currentRoomRes = tokens[1];
			}
			// SIZE
			else if (line.startsWith("size=")) {
				String[] tokens = line.split("=");
				String[] size = tokens[1].split("x");
				currentRoomNbCols = Integer.parseInt(size[0]);
				currentRoomNbLines = Integer.parseInt(size[1]);
			}
			// MATRIX LINE
			else if (line.startsWith("{") && line.endsWith("}")) {
				values.add(line.substring(1, line.length() - 1).replace(" ", ""));
			}
			// END LINE -> construct room
			else if (line.equals("END"))
			{
				// MATRIX
				int[][] matrix = new int[currentRoomNbLines][currentRoomNbCols];
				
				assert values.size() == currentRoomNbLines;
				int lineId = 0;
				for (String matrixLine : values) {
					splitter = new StringTokenizer(matrixLine, ",");
					int colId = 0;
					while (splitter.hasMoreTokens()) {
						matrix[lineId][colId] = Integer.parseInt((String)splitter.nextElement());
						++colId;
					}
					assert colId == currentRoomNbCols;
					++lineId;
				}
				assert lineId == currentRoomNbLines;
				Matrix mat = new Matrix(matrix);
				
				// RESOURCE
				Resource res = new Resource(currentRoomRes);
				
				// ROOM
				Room room = new Room(res, mat);
				room.chests.putAll(currentRoomChestList);
				roomList.put(currentRoomName, room);
				
				currentRoomName = null;
				currentRoomRes = null;
				currentRoomNbCols = 0;
				currentRoomNbLines = 0;
				values.clear();
				currentRoomChestList.clear();
			}
			// CHEST
			else if (line.startsWith("CHEST:")) {
				String[] tokens = line.split(":");
				String[] infos = tokens[1].split(";");
				
				Chest chest = new Chest();
				int chestId1 = Integer.parseInt(infos[0]);
				
				String item = infos[1];
				if (!item.equals("null")) {
					assert item.startsWith("O_");
					chest.setItem(Item.getItem(item));
				}
				
				currentRoomChestList.put(chestId1, chest);
			}
			// NEIGHBOR
			else if (line.startsWith("NEIGHBOR:")) {
				String[] tokens = line.split(":");
				String[] relationship = tokens[1].split(";");
				
				Room room1 = roomList.get(relationship[0]);
				int doorId1 = Integer.parseInt(relationship[1]);
				Room room2 = roomList.get(relationship[2]);
				int doorId2 = Integer.parseInt(relationship[3]);
				
				setLinkedRooms(room1, doorId1, room2, doorId2);
			}
			// START ROOM
			else if (line.startsWith("START_ROOM:")) {
				assert startRoom == null;
				String[] tokens = line.split(":");
				startRoom = roomList.get(tokens[1]);
				assert startRoom != null;
			}
		}
		
		assert startRoom != null;
		
		reader.close();
		
		return startRoom;
	}

	private static void setLinkedRooms(Room room1, int doorId1, Room room2, int doorId2) {
		room1.addNeighbor(doorId1, room2, doorId2);
		room2.addNeighbor(doorId2, room1, doorId1);
	}

	public static Room getRoom(String id) {
		return roomList.get(id);
	}
}
