/**
 * 
 */
package model.rooms;

import java.util.ArrayList;
import java.util.HashMap;

import model.Coord;
import model.Resource;

/**
 * @author Nicolas
 *
 */
public class Room {
	private Resource res;
	private Matrix mat;
	private HashMap<Integer, Room> neighbors;
	private HashMap<Integer, Integer> neighborsDoorsIds;
	
	private static ArrayList<Room> roomList;

	private Room(Resource res, Matrix mat) {
		this.res = res;
		this.mat = mat;
		this.neighbors = new HashMap<Integer, Room>();
		this.neighborsDoorsIds = new HashMap<Integer, Integer>();
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
	
	public boolean canWalkOnSquare(Coord c) {
		boolean returnValue = false;
		SquareType type = getSquareType(c);
		
		switch (type) {
		case OUTSIDE:
		case OBSTACLE:
			returnValue = false;
			break;
		case FREESQUARE:
		case DOOR:
			returnValue = true;
			break;
		default:
			assert false;
			break;
		}
		
		return returnValue;
	}
	
	public SquareType getSquareType(Coord c) {
		int squareValue = mat.getSquareValue(c);
		
		if(squareValue == -1) {
			return SquareType.OUTSIDE;
		}
		else if(squareValue == 0) {
			return SquareType.OBSTACLE;
		}
		else if(squareValue == 1) {
			return SquareType.FREESQUARE;
		}
		else if(squareValue >= 10 && squareValue < 20) {
			return SquareType.DOOR;
		}
		else {
			assert false;
			return null;
		}
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
		if(neighbor == null) {
			System.out.println("No neighbor room found from door " + doorId);
			return null;
		}
		
		int neighborDoorId = neighborsDoorsIds.get(doorId);
		return neighbor.getMat().getCoordFromValue(neighborDoorId);
	}
	
	public static Room createRooms() {
		roomList = new ArrayList<Room>();
		
		Room ginetteRoom = new Room(Resource.R_GINETTE, Matrix.R_GINETTE);
		Room park = new Room(Resource.R_PARK, Matrix.R_PARK);
		
		roomList.add(ginetteRoom);
		roomList.add(park);

		setLinkedRooms(ginetteRoom, 11, park, 12);
		
		return ginetteRoom;
	}

	private static void setLinkedRooms(Room room1, int doorId1, Room room2, int doorId2) {
		room1.addNeighbor(doorId1, room2, doorId2);
		room2.addNeighbor(doorId2, room1, doorId1);
	}
}
