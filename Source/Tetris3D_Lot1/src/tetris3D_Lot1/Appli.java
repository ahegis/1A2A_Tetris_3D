package tetris3D_Lot1;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


public class Appli extends SimpleApplication{
public Geometry blue = null;
public Geometry red = null;
boolean isRunning=false,landed=false;

@SuppressWarnings("deprecation")
@Override
public void simpleInitApp() {
	
	// create a blue box at coordinates (1,-1,1)
    Box box1 = new Box( Vector3f.ZERO, 5f,5f,0.5f);
    blue =  new Geometry("Box", box1);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.Blue);
    blue.setMaterial(mat1);
    blue.move(0f,0f,0f);  
    
    
    // create a red box straight above the blue one at (1,3,1)
    Box box2 = new Box( Vector3f.ZERO, 1,1,1);
    red = new Geometry("Box", box2);
    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat2.setColor("Color", ColorRGBA.Red);
    red.setMaterial(mat2);
    red.move(0,10,0);
    
    rootNode.attachChild(blue);
    rootNode.attachChild(red);

    blue.lookAt(red.getWorldTranslation(), new Vector3f(0,1,0) );
    red.lookAt(blue.getWorldTranslation(), new Vector3f(0,1,0) );
    initKeys();
}

@Override
public void simpleUpdate(float tpf) {
	
	if(isRunning&&(red.getLocalTranslation().getY()>blue.getLocalTranslation().getY()+1.5)){
    		red.setLocalTranslation(new Vector3f(red.getLocalTranslation().getX(),
    				red.getLocalTranslation().getY()-(float)(2*tpf),red.getLocalTranslation().getZ()));
	}
	if(isRunning&&(red.getLocalTranslation().getY()<=blue.getLocalTranslation().getY()+1.5)){
		landed=true;
	}
}

private void initKeys(){
	inputManager.addMapping("RotateY-", new KeyTrigger(KeyInput.KEY_K));
	inputManager.addMapping("RotateY+", new KeyTrigger(KeyInput.KEY_M));
	
	inputManager.addMapping("RotateX-", new KeyTrigger(KeyInput.KEY_O));
	inputManager.addMapping("RotateX+", new KeyTrigger(KeyInput.KEY_L));
	
	inputManager.addMapping("RotateZ-", new KeyTrigger(KeyInput.KEY_7));
	inputManager.addMapping("RotateZ+", new KeyTrigger(KeyInput.KEY_9));
	
	inputManager.addMapping("TranslateX-", new KeyTrigger(KeyInput.KEY_G));
	inputManager.addMapping("TranslateX+", new KeyTrigger(KeyInput.KEY_J));
	
	inputManager.addMapping("TranslateZ-", new KeyTrigger(KeyInput.KEY_Y));
	inputManager.addMapping("TranslateZ+", new KeyTrigger(KeyInput.KEY_H));	
	
	inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_SPACE));
	inputManager.addMapping("Restart", new KeyTrigger(KeyInput.KEY_BACK));

	inputManager.addListener(actionListener,"Pause","Restart","RotateX-","RotateX+","RotateY-","RotateY+",
			"RotateZ-","RotateZ+","TranslateX-","TranslateX+","TranslateZ-","TranslateZ+");
}

private ActionListener actionListener = new ActionListener(){
	public void onAction(String name, boolean keyPressed, float tpf){
		if(name.equals("Restart")&& !keyPressed){
			isRunning=false;
			landed=false;
			red.setLocalTranslation(0,10,0);
		}
		
		if(name.equals("Pause")&& !keyPressed){
			isRunning=!isRunning;
			guiNode.detachAllChildren();
			
			if(!isRunning){
				BitmapText hudText= new BitmapText(guiFont, false);
				hudText.setSize(70);
				hudText.setColor(ColorRGBA.White);
				hudText.setText("PAUSE");
				hudText.setLocalTranslation(800, hudText.getLineHeight(), 0);
				guiNode.attachChild(hudText);
			}
		}
		
		if(!landed){
				if(name.equals("RotateY-") && !keyPressed){			
					red.rotate(0f,0f,(float)(Math.PI)/2);
				}
				if(name.equals("RotateY+") && !keyPressed){
					red.rotate(0f,0f,(float)-(Math.PI)/2);
				}
				
				if(name.equals("RotateX-") && !keyPressed){			
					red.rotate(0f,(float)(Math.PI)/2,0f);
				}
				if(name.equals("RotateX+") && !keyPressed){
					red.rotate(0f,(float)-(Math.PI)/2,0f);
				}
				
				if(name.equals("RotateZ-") && !keyPressed){			
					red.rotate((float)(Math.PI)/2,0f,0f);
				}
				if(name.equals("RotateZ+") && !keyPressed){
					red.rotate((float)-(Math.PI)/2,0f,0f);
				}
				
				if(name.equals("TranslateX-") && !keyPressed){			
					if(isRunning&&(red.getLocalTranslation().getX()>blue.getLocalTranslation().getX()-4)){
							red.setLocalTranslation(new Vector3f(red.getLocalTranslation().getX()-2,
									red.getLocalTranslation().getY(),red.getLocalTranslation().getZ()));
					}
				}
				if(name.equals("TranslateX+") && !keyPressed){
					if(isRunning&&(red.getLocalTranslation().getX()<blue.getLocalTranslation().getX()+4)){
							red.setLocalTranslation(new Vector3f(red.getLocalTranslation().getX()+2,
									red.getLocalTranslation().getY(),red.getLocalTranslation().getZ()));
					}
				}
				
				if(name.equals("TranslateZ-") && !keyPressed){			
					if(isRunning&&(red.getLocalTranslation().getZ()>blue.getLocalTranslation().getZ()-4)){
							red.setLocalTranslation(new Vector3f(red.getLocalTranslation().getX(),
									red.getLocalTranslation().getY(),red.getLocalTranslation().getZ()-2));
					}
				}
				if(name.equals("TranslateZ+") && !keyPressed){
					if(isRunning&&(red.getLocalTranslation().getZ()<blue.getLocalTranslation().getZ()+4)){
							red.setLocalTranslation(new Vector3f(red.getLocalTranslation().getX(),
									red.getLocalTranslation().getY(),red.getLocalTranslation().getZ()+2));
					}
				}
		}			
	}
};


}