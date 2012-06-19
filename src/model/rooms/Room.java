/**
 * 
 */
package model.rooms;

import java.util.ArrayList;

import model.Resource;

/**
 * @author Nicolas
 *
 */
public class Room {
	private Resource res;
	private Matrix mat;
	
	private static ArrayList<Room> roomList;

	private Room(Resource res, Matrix mat) {
		this.res = res;
		this.mat = mat;
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

	public static Room createRooms() {
		roomList = new ArrayList<Room>();
		
		Room ginetteRoom = new Room(Resource.R_GINETTE, Matrix.R_GINETTE);
		
		roomList.add(ginetteRoom);
		
		return ginetteRoom;
	}
}
