package com.manchau.creational;

/***
 * 
 * @author Maneesh
 * Single element Enums are the safest ways to create singletons.
 * We can also have static final instance variable style singletons and
 * also ones with static factory methods like getInstance(). These ways however are prone
 * to attacks by privileged clients via reflection, and may also break in cases
 * where object serialization comes into picture. To provide singleton guarantee while using'
 * serialization mechanism, it is important to make all the fields transient and provide a
 * readResolve method, so that multiple copies of the same object are not created while deserialization.
 */
public enum Elvis {
	INSTANCE;
	private Boolean isHere = true;
	private final String whoAmI = "The King";
	private String songToSing = "Hound Dog";
	
	synchronized public void leaveTheBuilding() throws Exception {
		if(!isHere) {
			throw new Exception("Elvis has already left the building!");
		}
		System.out.println("Elvis has left the building !!");
		isHere = false;
	}
	synchronized public void requestSong(String song) {
		this.songToSing = song;
	}
	synchronized public String whichSong() {
		return songToSing;
	}
	public String whoAmI(){
		return whoAmI;
	}

	public static void main(String [] args) {
		System.out.println(Elvis.INSTANCE.whoAmI());
		System.out.println(Elvis.INSTANCE.whichSong());
		Elvis.INSTANCE.requestSong("Burning Love");
		System.out.println(Elvis.INSTANCE.whichSong());
		try {
			Elvis.INSTANCE.leaveTheBuilding();
			Elvis.INSTANCE.leaveTheBuilding();			
		} catch (Exception e) {
			System.out.println("Sorry Folks: " + e.getMessage());
		}		
	}
}


