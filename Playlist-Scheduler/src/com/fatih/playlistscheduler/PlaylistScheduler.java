package com.fatih.playlistscheduler;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.*;

import javax.swing.*;

public class PlaylistScheduler extends JPanel {
	static String logger = "";
	
	public static final int WIDTH = 310;
	public static final int HEIGHT = 480;
	
	public JFrame frame;
	
	JButton addMusic = new JButton();
	JButton removeMusic = new JButton();
	JButton setButton = new JButton();
	JButton playButton = new JButton();
	JButton stopButton = new JButton();
	
	DefaultListModel list = new DefaultListModel();
	JList Musics = new JList(list);
	JScrollPane musicScrollPane = new JScrollPane(Musics);
	
	JFileChooser fc = new JFileChooser();
	FileFilter filter = new FileNameExtensionFilter("MP3 File","mp3");

	public ArrayList<Music> musicList = new ArrayList<Music>();
	
	ButtonGroup group = new ButtonGroup();
	JRadioButton Monday = new JRadioButton();
	JRadioButton Tuesday = new JRadioButton();
	JRadioButton Wednesday = new JRadioButton();
	JRadioButton Thursday = new JRadioButton();
	JRadioButton Friday = new JRadioButton();
	JRadioButton Saturday = new JRadioButton();
	JRadioButton Sunday = new JRadioButton();
	
	JLabel Monday_t = new JLabel();
	JLabel Tuesday_t = new JLabel();
	JLabel Wednesday_t = new JLabel();
	JLabel Thursday_t = new JLabel();
	JLabel Friday_t = new JLabel();
	JLabel Saturday_t = new JLabel();
	JLabel Sunday_t = new JLabel();
	JLabel Settings = new JLabel();
	
	JLabel startTime = new JLabel();
	JLabel stopTime = new JLabel();
	
	JTextField startH = new JTextField();
	JTextField stopH = new JTextField();
	
	static JEditorPane logArea = new JEditorPane();
	static JScrollPane scroll = new JScrollPane(logArea);

	AudioInputStream audioInputStream;
	Clip clip;
	MyAudioPlayer thePlayer = null;

	static PlaylistScheduler app;
	
	public static void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app = new PlaylistScheduler();
		app.frame.setVisible(true);
	}
	
	/**
	 * Class constructor for PlaylistScheduler
	 */
	public PlaylistScheduler(){
		
		log("Playlist-Scheduler initiated.");
		initComponents();
		initListeners();
		initListChecker();
		
		
	}
	
	/**
	 * Returns the name of the day.
	 * @param d
	 * @return
	 */
	public static String sayDayName(Date d) {
      DateFormat f = new SimpleDateFormat("EEEE", Locale.ENGLISH);
      try {
        return f.format(d);
      }
      catch(Exception e) {
    	  log("Error in sayDayName");
    	  return "";
      }
    }
	
	/**
	 * Checks the list if there are any songs ready-to-play
	 */
	public void initListChecker(){
		final long timeInterval = 5000;
		  Runnable runnable = new Runnable() {
		  public void run() {
		    while (true) {
		      //***********
		    	Date date = new Date();
		    	Calendar c = Calendar.getInstance();
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		    	String todaysName = (String)sayDayName(date);
		    	String todaysHour = (String)dateFormat.format(c.getTime());
		    	//log(todaysHour+" "+todaysName);
		    	//log("List has <font style='color:red'>"+musicList.size()+"</font> items");
		      if(Musics.getModel().getSize() > 0){
		    	  log("Checking the list");
		    	  for(int i = 0; i < musicList.size(); i++){
		    		  Music selectedM = musicList.get(i);
		    		  //log("Checking "+selectedM.getFile().getName());
		    		  switch(todaysName){
		    			  case "Monday":
		    				  if(selectedM.MondayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.MondayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Tuesday":
		    				  if(selectedM.TuesdayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.TuesdayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Wednesday":
		    				  if(selectedM.WednesdayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.WednesdayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Thursday":
		    				  if(selectedM.ThursdayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.ThursdayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Friday":
		    				  if(selectedM.FridayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.FridayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Saturday":
		    				  if(selectedM.SaturdayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.SaturdayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  case "Sunday":
		    				  if(selectedM.SundayS.equals(todaysHour)){
		    					  playSong(i);
		    				  }else if(selectedM.SundayP.equals(todaysHour) && i == thePlayer.Playing){
		    					  pauseSong();
		    				  }
		    				  break;
		    			  default:
		    					  log(selectedM.getFile().getName()+" has nothing...");
		    					  break;
		    		  }
		    	  }
		      }else{
		    	  log("<font style='color:blue'>Nothing to check. Please add music</font>");
		      }
		      //***********
		      try {
		       Thread.sleep(timeInterval);
		      } catch (InterruptedException e) {
		      }
		      }
		    }
		  };
		  Thread thread = new Thread(runnable);
		  thread.start();
	}
	
	/**
	 * Initiating the components with specific properties.
	 */
	public void initComponents(){
		log("Components initiallized");
		this.frame = new JFrame();
		this.frame.setTitle("Playlist Scheduler");
		this.frame.setSize(WIDTH, HEIGHT);
		this.setSize(WIDTH,HEIGHT);
		this.frame.getContentPane().add(this);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.frame.setResizable(false);
		

		//COMPONENT SETTING
		fc.setFileFilter(filter);
		logArea.setContentType("text/html");
		
		
		//SETS
		removeMusic.setEnabled(false);
		setButton.setEnabled(false);
		
		Monday.setEnabled(false);
		Tuesday.setEnabled(false);
		Wednesday.setEnabled(false);
		Thursday.setEnabled(false);
		Friday.setEnabled(false);
		Saturday.setEnabled(false);
		Sunday.setEnabled(false);
		startH.setEnabled(false);
		stopH.setEnabled(false);
		playButton.setEnabled(false);
		stopButton.setEnabled(false);

		//Labels
		Monday_t.setText("Monday");
		Tuesday_t.setText("Tuesday");
		Wednesday_t.setText("Wednesday");
		Thursday_t.setText("Thurday");
		Friday_t.setText("Friday");
		Saturday_t.setText("Saturday");
		Sunday_t.setText("Sunday");
		startTime.setText("S:");
		stopTime.setText("P:");
		
		Settings.setText("Settings");
		Font font = new Font("Arial", Font.BOLD,12);
		Settings.setFont(font);
		
		addMusic.setText("Add Music");
		removeMusic.setText("Remove Music");
		setButton.setText("Set");
		playButton.setText(">");
		stopButton.setText("||");
		
		
		//Sizes
		musicScrollPane.setSize(new Dimension(150,350));
		Musics.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Musics.setLayoutOrientation(JList.VERTICAL);
		
		addMusic.setSize(new Dimension(130,30));
		removeMusic.setSize(new Dimension(130,30));
		setButton.setSize(130,30);
		playButton.setSize(50, 35);
		stopButton.setSize(50, 35);

		Monday.setSize(20, 20);
		Monday_t.setSize(100, 15);
		Tuesday.setSize(20, 20);
		Tuesday_t.setSize(100, 15);
		Wednesday.setSize(20, 20);
		Wednesday_t.setSize(100, 15);
		Thursday.setSize(20, 20);
		Thursday_t.setSize(100, 15);
		Friday.setSize(20, 20);
		Friday_t.setSize(100, 15);
		Saturday.setSize(20, 20);
		Saturday_t.setSize(100, 15);
		Sunday.setSize(20, 20);
		Sunday_t.setSize(100, 15);
		
		Settings.setSize(100, 15);
		startTime.setSize(30,40);
		stopTime.setSize(30,40);
		
		startH.setSize(100,18);
		stopH.setSize(100, 18);
		
		scroll.setSize(290, 80);

		//Positions
		add(musicScrollPane);
		add(addMusic);
		add(removeMusic);
		add(setButton);
		add(Monday);
		add(Tuesday);
		add(Wednesday);
		add(Thursday);
		add(Friday);
		add(Saturday);
		add(Sunday);
		add(playButton);
		add(stopButton);
		add(scroll);
		
	    group.add(Monday);
	    group.add(Tuesday);
	    group.add(Wednesday);
	    group.add(Thursday);
	    group.add(Friday);
	    group.add(Saturday);
	    group.add(Sunday);
		
		add(Monday_t);
		add(Tuesday_t);
		add(Wednesday_t);
		add(Thursday_t);
		add(Friday_t);
		add(Saturday_t);
		add(Sunday_t);
		add(Settings);
		add(startTime);
		add(stopTime);
		
		add(startH);
		add(stopH);
		
		musicScrollPane.setLocation(10, 10);
		
		addMusic.setLocation(165, 10);
		removeMusic.setLocation(165, 45);
		setButton.setLocation(165, 330);
		
		Monday.setLocation(165, 100);
		Monday_t.setLocation(190, 103);
		
		Tuesday.setLocation(165, 120);
		Tuesday_t.setLocation(190, 123);
		
		Wednesday.setLocation(165, 140);
		Wednesday_t.setLocation(190, 143);
		
		Thursday.setLocation(165, 160);
		Thursday_t.setLocation(190, 163);
		
		Friday.setLocation(165, 180);
		Friday_t.setLocation(190, 183);
		
		Saturday.setLocation(165, 200);
		Saturday_t.setLocation(190, 203);
		
		Sunday.setLocation(165, 220);
		Sunday_t.setLocation(190, 223);
		
		Settings.setLocation(200, 85);
		startTime.setLocation(165, 235);
		stopTime.setLocation(165, 255);
		
		startH.setLocation(185, 245);
		stopH.setLocation(185, 265);
		
		playButton.setLocation(175, 290);
		stopButton.setLocation(235, 290);
		
		scroll.setLocation(10,370);

	}
	
	
	/**
	 * Initiating event listeners for the buttons.
	 */
	public void initListeners(){
		addMusic.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					musicList.add(new Music(fc.getSelectedFile()));
				    list.addElement(fc.getSelectedFile().getName());
				    File f = musicList.get(musicList.size()-1).getFile();
				    log("<font style='color:green'>'"+f.getName()+"'</font> is added.");
				}
			}
		});
		
		removeMusic.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedId = Musics.getSelectedIndex();
				list.removeElementAt(Musics.getSelectedIndex());
				musicList.remove(selectedId);
				log("Remove Music is clicked");
			}
		});

		Musics.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
			        if (Musics.getSelectedIndex() == -1) {
			            removeMusic.setEnabled(false);
			            Monday.setEnabled(false);
			    		Tuesday.setEnabled(false);
			    		Wednesday.setEnabled(false);
			    		Thursday.setEnabled(false);
			    		Friday.setEnabled(false);
			    		Saturday.setEnabled(false);
			    		Sunday.setEnabled(false);
			    		startH.setEnabled(false);
			    		stopH.setEnabled(false);
			    		playButton.setEnabled(false);
			    		stopButton.setEnabled(false);
			    		setButton.setEnabled(false);
			        } else {
			            removeMusic.setEnabled(true);
			            Monday.setEnabled(true);
			    		Tuesday.setEnabled(true);
			    		Wednesday.setEnabled(true);
			    		Thursday.setEnabled(true);
			    		Friday.setEnabled(true);
			    		Saturday.setEnabled(true);
			    		Sunday.setEnabled(true);
			    		startH.setEnabled(true);
			    		stopH.setEnabled(true);
			    		playButton.setEnabled(true);
			    		stopButton.setEnabled(true);
			    		setButton.setEnabled(true);
			            clearAll();
			        }
			    }
			}
		});
		
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedId = Musics.getSelectedIndex();
				playSong(selectedId);
				
			}
		});
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pauseSong();
			}
		});
		
		setButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//00:00
				String startD = startH.getText();
				String stopD = stopH.getText();
				if(startD.indexOf(":") != -1 && stopD.indexOf(":") != -1 && startD.length() == 5 && stopD.length() == 5){
					int index =  Musics.getSelectedIndex();
					musicList.get(index).isPlaying = false;
					if(Monday.isSelected()){
						musicList.get(index).MondayS = startD;
						musicList.get(index).MondayP = stopD;
						Monday_t.setText("Monday+");
						log("Monday is Set");
					}else if(Tuesday.isSelected()){
						musicList.get(index).TuesdayS = startD;
						musicList.get(index).TuesdayP = stopD;
						Tuesday_t.setText("Tuesday+");
						log("Tuesday is Set");
					}else if(Wednesday.isSelected()){
						musicList.get(index).WednesdayS = startD;
						musicList.get(index).WednesdayP = stopD;
						Wednesday_t.setText("Monday+");
						log("Wednesday is Set");
					}else if(Thursday.isSelected()){
						musicList.get(index).ThursdayS = startD;
						musicList.get(index).ThursdayP = stopD;
						Thursday_t.setText("Thursday+");
						log("Thursday is Set");
					}else if(Friday.isSelected()){
						musicList.get(index).FridayS = startD;
						musicList.get(index).FridayP = stopD;
						Friday_t.setText("Friday+");
						log("Friday is Set");
					}else if(Saturday.isSelected()){
						musicList.get(index).SaturdayS = startD;
						musicList.get(index).SaturdayP = stopD;
						Saturday_t.setText("Saturday+");
						log("Saturday is Set");
					}else if(Sunday.isSelected()){
						musicList.get(index).SundayS = startD;
						musicList.get(index).SundayP = stopD;
						Sunday_t.setText("Sunday+");
						log("Sunday is Set");
					}
				}else{
					log("input is not okey");
				}
			}
		});
		
		Monday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Monday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).MondayS);
				stopH.setText(musicList.get(index).MondayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
		Tuesday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Tuesday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).TuesdayS);
				stopH.setText(musicList.get(index).TuesdayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
		Wednesday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Wednesday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).WednesdayS);
				stopH.setText(musicList.get(index).WednesdayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
		Thursday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Thursday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).ThursdayS);
				stopH.setText(musicList.get(index).ThursdayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});

		Friday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Friday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).FridayS);
				stopH.setText(musicList.get(index).FridayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
		Saturday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Saturday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).SaturdayS);
				stopH.setText(musicList.get(index).SaturdayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
		Sunday.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				log("Sunday is clicked");
				log("Set a time now[Ex:12:00]");
				int index = Musics.getSelectedIndex();
				
				startH.setText(musicList.get(index).SundayS);
				stopH.setText(musicList.get(index).SundayP);
				if(startH.getText() == ""){
					startH.setText("NULL");
				}
				if(stopH.getText() == ""){
					stopH.setText("NULL");
				}
			}
		});
		
	}
	
	/**
	 * Plays the song selected in the music list.
	 * @param index
	 */
	public void playSong(int index){
		String musicPath = musicList.get(index).getFile().getPath();
		if(musicList.get(index).isPlaying != true){
			musicList.get(index).isPlaying = true;
			
			try{
				if(thePlayer != null)
					thePlayer.close();
				thePlayer = new MyAudioPlayer(musicPath, true);
		        thePlayer.start();
				log("<font style='color:red'>"+musicList.get(index).getFile().getName()+"</font> is playing now.");
				thePlayer.Playing = index;
			}catch(Exception ex)
			{log("Error:"+musicPath+"\nError:"+ex.getMessage());}
		}
	}
	
	/**
	 * Pauses the selected song.
	 */
	public void pauseSong(){
		log("<font style='color:red'>Music is stopped.</font>");
		musicList.get(thePlayer.Playing).isPlaying = false;
		thePlayer.Playing = -1;
		thePlayer.close();
	}
	
	/**
	 * Clears out the whole music list.
	 */
	public void clearAll(){
		startH.setText("");
        stopH.setText("");
        group.clearSelection();
	}
	
	/**
	 * This function logs a String s into the logArea at the bottom.
	 * @param s
	 */
	public static void log(String s){
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    	String logHour = (String)dateFormat.format(c.getTime());
		logger += s+"\n";
		System.out.print(s+"\n");
		logArea.setText("["+logHour+"] " + s +logArea.getText()+"<br>");
		scroll.getVerticalScrollBar().setValue(0);
	}
	
	
}
