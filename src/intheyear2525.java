import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import processing.core.*;
import wordcram.*;

@SuppressWarnings("serial")
public class intheyear2525 extends PApplet{
	
	int frameCounter = 0; // frame counter for noloop function

	public GLOBAL g = new GLOBAL(this);
	public Parser pars = new Parser();
	
	public static Menu menu;
	public static GraphsArea graphArea;
	public static MultistateButton analysisTypeButton;
	public TagCloud tc;
	private PImage title;
	
	public void setup() {
		title = GLOBAL.processing.loadImage("images/title1.jpg");
		
		setupColors();
		
		size(1024,768, JAVA2D);
		frameRate(60);
		
		background(GLOBAL.colorBackgroundLayerTwo);
		
		GLOBAL.gu = new GuiGradient();
//		GLOBAL.tFont = loadFont("LucidaSans-48.vlw");
		GLOBAL.tFont = createFont("Segoe UI", 20);
		GLOBAL.selectedEpisodesList = Parser.LIST_ALL; // Initialize the displayed list to "all episodes"
				
		//Parse all available transcripts.
		pars.parseCatchphrases();		//Loads cathphrases (& their regular expressions) into corresponding Character objects
		pars.parseBackgroundChars();	//Loads unacceptable character names from file names.txt
		pars.parseAllTranscripts();		//Parse all episode transcripts
		pars.filterCharacters();        //Remove characters in less than 1 episode
		GLOBAL.parseForWordMap = true;	//Flag to enable parsing on .txt files for word mapping [DO NOT REMOVE!!!]
		pars.parseAllTranscripts();		//Parse all word file transcripts.
		
		
		//Sort ALL_CHARACTERS where characters in more episodes are listed first.
		/*Collections.sort(Parser.ALL_CHARACTERS, new Comparator<Object>(){
            public int compare(Object ob1, Object ob2) {
                Character c1 = (Character) ob1;
                Character c2 = (Character) ob2;
               return c2.getTotalEpisodes() - c1.getTotalEpisodes();
            }
 
        });*/
		
		//Sort ALL_CHARACTERS alphabetically
			Collections.sort(Parser.ALL_CHARACTERS, new Comparator<Object>(){
	            public int compare(Object ob1, Object ob2) {
	                Character c1 = (Character) ob1;
	                Character c2 = (Character) ob2;
	               return c1.getName().compareToIgnoreCase(c2.getName());
	            }
	 
	        });
		
		// Set incons of the characters after the parsing end
		for (int i = 0; i< Parser.ALL_CHARACTERS.size(); i++) {
			Parser.ALL_CHARACTERS.get(i).setIcon();
		}

		// TreeMap .tm3 file -> copy & paste on the Characters.tm3 file  
		/*System.out.println("Appearence");
		System.out.println("INTEGER");
		
		for(int x=0; x<Parser.ALL_CHARACTERS.size(); ++x) {
			String name = Parser.ALL_CHARACTERS.get(x).getName();
			int ep = Parser.ALL_CHARACTERS.get(x).getTotalEpisodes();

			//System.out.println(name + ":\t\t" + ep + " episodes.");

			if ( Parser.ALL_CHARACTERS.get(x).getTotalEpisodes() > 5)
				//System.out.println("<leaf>\n<label>"+ Parser.ALL_CHARACTERS.get(x).getName() +"</label>\n<weight>"+ Parser.ALL_CHARACTERS.get(x).getTotalEpisodes() +"</weight>\n<value>"+ Parser.ALL_CHARACTERS.get(x).getTotalEpisodes() +"</value>\n</leaf>");
				System.out.println(Parser.ALL_CHARACTERS.get(x).getTotalEpisodes() + "\tCharacters\t" + Parser.ALL_CHARACTERS.get(x).getName());
		}
		*/
		
		//TESTING... 1 2 3! 
		//Parser.ALL_CHARACTERS.get(30).printPhrases();
		
		// Create the menu
		menu = new Menu();
		menu.width = 200;
		menu.height = height;
		
		// Create the plotting area, composed at most by 3 plots
		graphArea = new GraphsArea();
		graphArea.x = 220;
		graphArea.y = 20;
		graphArea.width = 1000 - 220;
		graphArea.height = 750 - 40;
		graphArea.createScrollBar();
		
		// tristate button for setting type of analysis
		analysisTypeButton = new MultistateButton();
		analysisTypeButton.x = 20;
		analysisTypeButton.y = 150;
		analysisTypeButton.addState("characters");
		analysisTypeButton.addState("episodes");
		analysisTypeButton.addState("seasons");
		analysisTypeButton.active = true;
		
		// Initialization of the first kind of analysis
		GLOBAL.ANALYSIS_TYPE = analysisTypeButton.getState();
		
		GLOBAL.episodeStart = Parser.LIST_ALL.get(0);
		GLOBAL.episodeEnd = Parser.LIST_ALL.get(Parser.LIST_ALL.size() - 1 );
	  
		smooth();
		
	}

	public void draw() {
		
		frameCounter++;
		
		drawLayerTwoBackground();
		graphArea.draw();
		menu.draw();
		analysisTypeButton.draw();

		drawLayerTwoText();
		noFill();

		if (frameCounter == 120) { // wait 2 sec before going to sleep
			noLoop();
			frameCount = 0;
		}
	}

	// Draw the background of the second layer
	public void drawLayerTwoBackground() {
		background(GLOBAL.colorBackgroundLayerTwo);
	}
	
	public void drawLayerTwoText() {
		
		GLOBAL.processing.image(title, 0, 20);
		
		fill(GLOBAL.colorText);
		textFont(GLOBAL.tFont,16);
		GLOBAL.processing.textAlign(GLOBAL.processing.LEFT);
		GLOBAL.processing.fill(GLOBAL.colorBlu);
		text("Select analysis type: ",20, 140);
		text("Stats or Plots: ",20, 390);
		
		//Instructions to be displayed on startup + after clearing all graphs
		if(GLOBAL.drawInstructions) {
			GLOBAL.processing.fill(GLOBAL.colorBlu);
			textFont(GLOBAL.tFont,24);
			text("Getting Started: ",240, 45);
			
			textFont(GLOBAL.tFont,20);
			text("<-- Select analysis of Characters, Episodes, or Seasons.",240, 140);
			text("<-- Add Characters/Episodes/Seasons to your data set.",240, 220);
			text("<-- Select whether you wish to view data in graph or statistical form.",240, 390);
			
		}
	}
	
	// Color setup function, all colors should be set here, so every change will be global
	public void setupColors() {
		GLOBAL.colorToggleOff = color(152);
		GLOBAL.colorText = color(224);
		GLOBAL.colorBackgroundLayerOne = color(36);
		GLOBAL.colorBackgroundLayerTwo = GLOBAL.colorBackgroundLayerOne;
		GLOBAL.colorLinesLabelY = color(128);		
		GLOBAL.colorButtonLabel = color(224);	
		GLOBAL.colorPlotArea = color(0);
		GLOBAL.colorStatsArea = color(28,28,28);
		GLOBAL.colorMenuBackground = color(28,28,28);
		GLOBAL.colorIconBackground = color(224);
		GLOBAL.colorTagCloudBackground = color(224,224,224,0);
		GLOBAL.colorBlu = color(136,204,238);
		
		int[] temp = {color(136,204,238), color(68,170,153), color(17,119,51), color(221,204,119), 
					color(204,102,119), color(136,34,85), color(146,189,16), color(170,68,153), 
					color(115,100,190)
		};
		
		//Randomize colors
		int[] c = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
		Random rand = new Random();
		
		for(int x=0; x<temp.length; ++x){
			int n = rand.nextInt(temp.length);
			while(c[n] != -1) {
				n = rand.nextInt(temp.length);
			}
			c[n] = temp[x];
		}

		GLOBAL.COLORS = new ColorSwatch(c);
	}

	// If mouse is pressed, check what has been pressed and activate the action
	public void mousePressed() { 
		frameCounter = 0;
		loop();

		//println("x = " + mouseX+ "y =" + mouseY);

		if(menu.mouseOver() || menu.characterPicker.mouseOver() || menu.seasonPicker.mouseOver() || menu.episodePicker.mouseOver())
			menu.doAction();

		// Change type of analysis
		if ( analysisTypeButton.mouseOver() ) {
			GLOBAL.WORD_ANALYSIS = false;
			analysisTypeButton.doAction();
			GLOBAL.ANALYSIS_TYPE = analysisTypeButton.getState();
			graphArea.clearGraphs();
			GLOBAL.charactersSelected.clear();
			GLOBAL.episodesSelected.clear();
			GLOBAL.seasonsSelected.clear();
		}

		if (graphArea.mouseOver())
			graphArea.doAction();

	}

	public void mouseClicked() {
		frameCounter = 0;
		loop();
	}
	public void mouseMoved() {
		frameCounter = 0;
		loop();
	}	

	public void mouseDragged() {
		frameCounter = 0;
		loop();
	}

	public void mouseReleased() {
		// Scrolls must be released 
		menu.episodePicker.scroll.mouseReleased();
		menu.characterPicker.scroll.mouseReleased();
		graphArea.mouseReleased();
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present --location=0,0", "intheyear2525" });
	}

}