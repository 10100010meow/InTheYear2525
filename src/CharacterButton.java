import processing.core.*;

// A character buttons should contain an image to be displayed (we may eventually change this behavior )
public class CharacterButton extends GuiElement{
	
	public static int NO_LABEL_TYPE = 0;
	public static int LABEL_TYPE = 1;
	
	public int TYPE;
	
	public Character character;
	
	public CharacterButton(String name, int BUTTON_TYPE) {
		
		if (BUTTON_TYPE == LABEL_TYPE) {
			TYPE = BUTTON_TYPE;
			setLabel(name);
			width = (int)(GLOBAL.processing.textWidth(label)) + 10;
		}
		else if (BUTTON_TYPE == NO_LABEL_TYPE) {
			
			label = name;
			TYPE = BUTTON_TYPE;
		}
		
		// Find the character and save it
		for (int i=0 ; i< Parser.ALL_CHARACTERS.size(); i++) {
			if (Parser.ALL_CHARACTERS.get(i).getName().equals(name))
				character = Parser.ALL_CHARACTERS.get(i);
		}
	
	}

	public void setIcon(String name)
	{
		if(name != null)
		{
			icon = GLOBAL.processing.loadImage(name);
		}
	}

	public void setLabel(String name)
	{
		label = name;
		
		if ( icon == null ) {
			width += 15;
		    height = 30;
		}
			
	}

	public void draw()
	{

		int cx = x;
		int cy = y;
		
		if(TYPE == NO_LABEL_TYPE)
		{
			//GLOBAL.processing.image(icon, x, y, width, height);
			if(mouseOver()) 
			{
				super.draw();
				if(GLOBAL.processing.mousePressed)
				{
					//GLOBAL.gu.drawVGradient(x, y + height, width, -height, GLOBAL.processing.color(150, 150, 180), 80, GLOBAL.processing.color(150, 150, 180), 255, (float)0.8);    
					GLOBAL.gu.drawBox(x, y, width, height, 0, GLOBAL.processing.color(150, 150, 200), 255);
					GLOBAL.gu.drawBox(x, y, width, height, 1, GLOBAL.processing.color(150, 150, 200), 150);
					cx += 2;
					cy += 2;
				}
				mouseRolloverFunction();
			}
			GLOBAL.processing.strokeWeight(1);
		}
		// with label
		else 
		{
			GLOBAL.processing.textFont(GLOBAL.tFont, 19);
			width = (int)(GLOBAL.processing.textWidth(label)) + 43;
			if (character.getIcon() != null)
				GLOBAL.processing.image(character.getIcon(), x, y, 30, 30);
			GLOBAL.processing.textAlign(GLOBAL.processing.LEFT);
			if(mouseOver()) 
			{
				super.draw();
				if(GLOBAL.processing.mousePressed)
				{
					//GLOBAL.gu.drawVGradient(x, y + height, width, -height, GLOBAL.processing.color(150, 150, 180), 80, GLOBAL.processing.color(150, 150, 180), 255, (float)0.8);    
					GLOBAL.gu.drawBox(x, y, width, height, 0, GLOBAL.processing.color(150, 150, 200), 255);
					GLOBAL.gu.drawBox(x, y, width, height, 1, GLOBAL.processing.color(150, 150, 200), 150);
					cx += 2;
					cy += 2;
				}
			}

			GLOBAL.processing.strokeWeight(1);
			GLOBAL.processing.fill(GLOBAL.colorButtonLabel);
			GLOBAL.processing.text(label.substring(0,1).toUpperCase() + label.substring(1), cx + 35, cy + 20);
		}

	}

	public void mouseRolloverFunction() {

		// Draw a rectangle, the label and an image inside, now the image is set to 100x100

		if(TYPE==LABEL_TYPE) {
			// Rectangle
			GLOBAL.processing.noStroke();
			GLOBAL.processing.rectMode(GLOBAL.processing.CORNER);
			GLOBAL.processing.fill(GLOBAL.colorIconBackground);
			GLOBAL.processing.rect( GLOBAL.processing.mouseX +12, GLOBAL.processing.mouseY - 18 -100 , 104, 104 ); //x,y,width,height

			// Image
			if (character.getIcon()!= null)
				GLOBAL.processing.image(character.getIcon(), GLOBAL.processing.mouseX + 14, GLOBAL.processing.mouseY - 16 - 100, 100,100);
		}
		else {
			// Rectangle
			GLOBAL.processing.noStroke();
			GLOBAL.processing.rectMode(GLOBAL.processing.CORNER);
			GLOBAL.processing.fill(GLOBAL.colorIconBackground);
			GLOBAL.processing.rect( GLOBAL.processing.mouseX, GLOBAL.processing.mouseY - 40 - 120 , 100, 130); //x,y,width,height
			
			// Image
			if (character.getIcon()!= null)
				GLOBAL.processing.image(character.getIcon(), GLOBAL.processing.mouseX, GLOBAL.processing.mouseY - 10 - 100, 100,100);
			
			// Text
			GLOBAL.processing.fill(GLOBAL.colorBackgroundLayerTwo);
			GLOBAL.processing.textFont(GLOBAL.tFont,14);
			GLOBAL.processing.textAlign(GLOBAL.processing.CENTER);
			GLOBAL.processing.text(character.getName_firstToUppercase().replace(" ", "\n").replace("-", "-\n"), 
					GLOBAL.processing.mouseX + 50, GLOBAL.processing.mouseY - 18 - 120);
		}
		
	}

	public void doAction() {
		
		if (GLOBAL.charactersSelected.contains(character))
			return;
		
		// Add the character to the list to be analyzed in graphs, add it in 1st position if it is full ( size = 3 )
		if (GLOBAL.charactersSelected.size() < 3) {
			for (int i = 0 ; i < Parser.ALL_CHARACTERS.size() ; i++ ) {
				if (Parser.ALL_CHARACTERS.get(i).getName().equalsIgnoreCase(label)) {
					GLOBAL.charactersSelected.add(Parser.ALL_CHARACTERS.get(i));
					GLOBAL.CHARACTER_SELECTED = Parser.ALL_CHARACTERS.get(i);
					break;
				}
			}
		}
		else {
			for (int i = 0 ; i < Parser.ALL_CHARACTERS.size() ; i++ ) {
				if (Parser.ALL_CHARACTERS.get(i).getName().equalsIgnoreCase(label)) {
					GLOBAL.charactersSelected.remove(2);
					GLOBAL.charactersSelected.add(0,Parser.ALL_CHARACTERS.get(i));
					GLOBAL.CHARACTER_SELECTED = Parser.ALL_CHARACTERS.get(i);
					break;
				}
			}
		}
				
		// Create the new graph to be plot
		intheyear2525.graphArea.createCharacterGraph();
				
		Menu.selectingCharacter = false;
	}

	PImage icon;
	String label;
	
}


