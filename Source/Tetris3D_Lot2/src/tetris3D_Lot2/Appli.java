package tetris3D_Lot2;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;


public class Appli extends SimpleApplication{
public RigidBodyControl plateau_phy;
public Spatial plateau_geo = null;
public Geometry red = null;
public RigidBodyControl piece_phy;
public Spatial piece_geo = null;
boolean isRunning=false,landed=false;
public Spatial yellow;
private BulletAppState bulletAppState;

@SuppressWarnings("deprecation")
@Override
public void simpleInitApp() {
    
	bulletAppState = new BulletAppState();
	stateManager.attach(bulletAppState);
	
	 Mesh plateau = new Box(2.5f, 0.1f, 2.5f);
	 plateau.scaleTextureCoordinates(new Vector2f(3, 6));
	    
	Material plateau_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
	key3.setGenerateMips(true);
	Texture tex3 = assetManager.loadTexture(key3);
	tex3.setWrap(WrapMode.Repeat);
	plateau_mat.setTexture("ColorMap", tex3);
    
	
	plateau_geo =  new Geometry("Floor", plateau);
	plateau_geo.setMaterial(plateau_mat);
	plateau_geo.setLocalTranslation(0, -0.1f, 0);   
    this.rootNode.attachChild(plateau_geo);
    plateau_phy=new RigidBodyControl(0.0f);
    plateau_geo.addControl(plateau_phy);
    bulletAppState.getPhysicsSpace().add(plateau_phy);
   
    
    yellow=assetManager.loadModel("Cube.mesh.xml");
    yellow.setLocalScale(0.5f);
    yellow.move(0f,10f,0f);
    rootNode.attachChild(yellow);
    
    // create a piece box straight above the plateau one at (1,3,1)
    Box box2 = new Box( Vector3f.ZERO, 1,1,1);
    red = new Geometry("Box", box2);
    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat2.setColor("Color", ColorRGBA.Red);
    red.setMaterial(mat2);
    red.move(0,10,0);
    
    piece_geo=yellow;/*
    piece_phy=new RigidBodyControl(0.5f);
    piece_geo.addControl(piece_phy);
    bulletAppState.getPhysicsSpace().add(piece_phy);
    piece_phy.setKinematic(true);
	piece_geo.setLocalTranslation(0,40,0);*/
    
    rootNode.attachChild(plateau_geo);
    rootNode.attachChild(piece_geo);

    plateau_geo.lookAt(piece_geo.getWorldTranslation(), new Vector3f(0,1,0) );
    piece_geo.lookAt(plateau_geo.getWorldTranslation(), new Vector3f(0,1,0) );
    initKeys();
    
    
}

@Override
public void simpleUpdate(float tpf) {
	
	if(isRunning&&(piece_geo.getLocalTranslation().getY()>plateau_geo.getLocalTranslation().getY()+1)){
		piece_geo.setLocalTranslation(new Vector3f(piece_geo.getLocalTranslation().getX(),
				piece_geo.getLocalTranslation().getY()-(float)(2*tpf),piece_geo.getLocalTranslation().getZ()));
	}
	if(isRunning&&(piece_geo.getLocalTranslation().getY()<=plateau_geo.getLocalTranslation().getY()+1)){
		landed=true;
		/*piece_phy.setKinematic(true);*/
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
			/*piece_phy.setKinematic(false);*/
			piece_geo.setLocalTranslation(0,40,0);
			/*piece_phy.setKinematic(true);*/
		}
		
		if(name.equals("Pause")&& !keyPressed){
			isRunning= !isRunning;
			guiNode.detachAllChildren();
			
			
			if(!isRunning){
				BitmapText hudText= new BitmapText(guiFont, false);
				hudText.setSize(70);
				hudText.setColor(ColorRGBA.White);
				hudText.setText("PAUSE");
				hudText.setLocalTranslation(800, hudText.getLineHeight(), 0);
				guiNode.attachChild(hudText);
				/*piece_phy.setKinematic(true);*/
			}
			/*else piece_phy.setKinematic(false);*/
		}
		
		if(!landed){
			
				if(name.equals("RotateY-") && !keyPressed){	
					/*piece_phy.setKinematic(true);*/
					piece_geo.rotate(0f,0f,(float)(Math.PI)/2);
					/*piece_phy.setKinematic(false);*/
				}
				if(name.equals("RotateY+") && !keyPressed){
					/*piece_phy.setKinematic(true);*/
					piece_geo.rotate(0f,0f,(float)-(Math.PI)/2);
					/*piece_phy.setKinematic(false);*/
				}
				
				if(name.equals("RotateX-") && !keyPressed){	
					piece_geo.rotate(0f,(float)(Math.PI)/2,0f);
				}
				if(name.equals("RotateX+") && !keyPressed){
					piece_geo.rotate(0f,(float)-(Math.PI)/2,0f);
				}
				
				if(name.equals("RotateZ-") && !keyPressed){			
					piece_geo.rotate((float)(Math.PI)/2,0f,0f);
				}
				if(name.equals("RotateZ+") && !keyPressed){
					piece_geo.rotate((float)-(Math.PI)/2,0f,0f);
				}
				
				if(name.equals("TranslateX-") && !keyPressed){	
					/*piece_phy.setKinematic(false);*/
					if(isRunning&&(piece_geo.getLocalTranslation().getX()>plateau_geo.getLocalTranslation().getX()-4)){
							piece_geo.setLocalTranslation(new Vector3f(piece_geo.getLocalTranslation().getX()-2,
									piece_geo.getLocalTranslation().getY(),piece_geo.getLocalTranslation().getZ()));
					}
					/*piece_phy.setKinematic(true);*/
				}
				if(name.equals("TranslateX+") && !keyPressed){
					if(isRunning&&(piece_geo.getLocalTranslation().getX()<plateau_geo.getLocalTranslation().getX()+4)){
							piece_geo.setLocalTranslation(new Vector3f(piece_geo.getLocalTranslation().getX()+2,
									piece_geo.getLocalTranslation().getY(),piece_geo.getLocalTranslation().getZ()));
					}
				}
				
				if(name.equals("TranslateZ-") && !keyPressed){			
					if(isRunning&&(piece_geo.getLocalTranslation().getZ()>plateau_geo.getLocalTranslation().getZ()-4)){
							piece_geo.setLocalTranslation(new Vector3f(piece_geo.getLocalTranslation().getX(),
									piece_geo.getLocalTranslation().getY(),piece_geo.getLocalTranslation().getZ()-2));
					}
				}
				if(name.equals("TranslateZ+") && !keyPressed){
					if(isRunning&&(piece_geo.getLocalTranslation().getZ()<plateau_geo.getLocalTranslation().getZ()+4)){
							piece_geo.setLocalTranslation(new Vector3f(piece_geo.getLocalTranslation().getX(),
									piece_geo.getLocalTranslation().getY(),piece_geo.getLocalTranslation().getZ()+2));
					}
				}
		
		}
		else if(!landed && !keyPressed);
	}
};


}