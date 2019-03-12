package com.fatih.playlistscheduler;

import java.io.File;

public class Music {
	private File file;
	
	//Start
	public String MondayS = "";
	public String TuesdayS = "";
	public String WednesdayS = "";
	public String ThursdayS = "";
	public String FridayS = "";
	public String SaturdayS = "";
	public String SundayS = "";
	//Stop
	public String MondayP = "";
	public String TuesdayP = "";
	public String WednesdayP = "";
	public String ThursdayP = "";
	public String FridayP = "";
	public String SaturdayP = "";
	public String SundayP = "";
	
	public boolean isPlaying = false;
	public Music(File fileInput){
		this.file = fileInput;
	}
	
	public File getFile(){
		return this.file;
	}
}

