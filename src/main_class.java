import java.util.Collections;
import java.util.Comparator;

import processing.core.*;
import controlP5.*;

public class main_class extends PApplet {
	
	public Parser pars = new Parser(this);
	
	public ControlP5 controlP5;
	public RadioButton layerOneSeriesChooser;
	public RadioButton layerOneEpisodesChooser;	
	public GLOBAL g;
	
	// common colors
	public int colorToggleOff = color(152);
	public int colorText = color(224);
	
	// colors. 0x means hex colors.
	public int colorBackgroundLayerOne = color(36);
	public int colorLinesLayerOne = color(128);
	
	public PFont tFont;
	
	// Starting point of the application
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "main_class" });
	}
	
	@SuppressWarnings("unchecked")
	public void setup() {
		
		size(1024,768);
		
		controlP5  = new ControlP5(this);
		tFont = loadFont("LucidaSans-48.vlw");
		
		//Parse all available transcripts.
		pars.parseAllTranscripts();
		pars.filterCharacters();	//Remove characters in less than 1 episode
		
		//Sort ALL_CHARACTERS where characters in more episodes are listed first.
		Collections.sort(Parser.ALL_CHARACTERS, new Comparator(){
            public int compare(Object ob1, Object ob2) {
                Character c1 = (Character) ob1;
                Character c2 = (Character) ob2;
               return c2.getTotalEpisodes() - c1.getTotalEpisodes();
            }
 
        });
		
		
		  //Prints all characters currently added. 
		  //For testing only.
		 
		for(int x=0; x<Parser.ALL_CHARACTERS.size(); ++x) {
			String name = Parser.ALL_CHARACTERS.get(x).getName();
			int ep = Parser.ALL_CHARACTERS.get(x).getTotalEpisodes();
			
			System.out.println(name + ":\t" + ep + " episodes.");
		}	
		
			

		setupLayerOneGUI();
		
		smooth();
		
	}

	public void draw() {

		// layer 1
		if(GLOBAL.LAYER == 1){
			
			drawLayerOneBackground();
			drawLayerOneText();
			
		}
		// layer 2
		else if(GLOBAL.LAYER == 2){
			
			
		}
		// layer 3
		else if(GLOBAL.LAYER == 3){


		}

		strokeWeight(1);
	}

	///
	/// Layer One functions
	///
	public void drawLayerOneText() {
		fill(colorText);
		textFont(tFont,24);
		text("In the year 2525", width/2 - 100, 40);
		text("Choose a character :",50,100);
		text("Choose among series and episodes :",50 + width/2,100);
	}

	// Draw the background of the first layer, i.e. the layer where you choose a character, a season or an episode (or all episodes)
	public void drawLayerOneBackground() {

		background(colorBackgroundLayerOne);
		
		stroke(this.color(colorLinesLayerOne));
		strokeWeight(2);
		line(width/2, 60, width/2, height - 30);
		
	}

	public void setupLayerOneGUI() {
		
		layerOneSeriesChooser = controlP5.addRadioButton("layerOneSeriesChooser_function", width/2 + 40, 130);
		layerOneSeriesChooser.setColorBackground(colorToggleOff);
		layerOneSeriesChooser.setItemHeight(30);
		layerOneSeriesChooser.setItemWidth(30);
		layerOneSeriesChooser.setColorLabel(colorText);
		layerOneSeriesChooser.setItemsPerRow(2);
		layerOneSeriesChooser.setSpacingColumn(100);
		layerOneSeriesChooser.setId(2);
		addToRadioButton(layerOneSeriesChooser, "Series 1", 1);
		addToRadioButton(layerOneSeriesChooser, "Series 2", 2);
		addToRadioButton(layerOneSeriesChooser, "Series 3", 1);
		addToRadioButton(layerOneSeriesChooser, "Series 4", 2);
		addToRadioButton(layerOneSeriesChooser, "Series 5", 1);
		addToRadioButton(layerOneSeriesChooser, "Series 6", 2);
	}
	
	// Radio button
	public void controlEvent(ControlEvent theEvent) {
	}
	
	public void addToRadioButton(RadioButton theRadioButton, String theName, int theValue ) {
		  Toggle t = theRadioButton.addItem(theName, theValue);
		  //t.captionLabel().setColorBackground(cmenu_background);
//		  t.captionLabel().style().movePadding(2, 0, -1, 2);
//		  t.captionLabel().style().moveMargin(-2, 0, 0, -3);
		  //  t.captionLabel().style().backgroundWidth = 46;
		    t.captionLabel().toUpperCase(false);
		   // t.captionLabel().set
		  //  t.captionLabel().setFont(ControlP5.standard56);
		}
	
	public void layerOneSeriesChooser_function() {
		
	}
	
	///
	/// End Layer One functions
	///
	
	public void mousePressed() { 
		  println("x = " + mouseX+ "y =" + mouseY);
	}

}