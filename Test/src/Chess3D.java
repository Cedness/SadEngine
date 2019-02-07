import de.ced.chess_api.Chess;
import de.ced.chess_api.Game;
import de.ced.chess_api.Piece;
import de.ced.chess_api.PieceType;
import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.SadEngine;
import de.ced.sadengine.objects.SadMainLogic;
import de.ced.sadengine.objects.Sadness;
import de.ced.sadengine.objects.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static de.ced.sadengine.utils.SadValue.*;

public class Chess3D implements SadMainLogic {
	
	private final float pieceScale = 0.05f;
	
	private Chess chess = null;
	private Game game = null;
	
	private HashMap<PieceType, List<SadModel>> models = new HashMap<>();
	
	@Override
	public void setup(Sadness sadness) {
		SadWindow window = sadness.getWindow();
		SadContent content = sadness.getContent();
		
		SadCamera camera = content.createCamera("Camera");
		window.setCamera("Camera");
		
		SadLevel main = content.createLevel("main");
		content.createLevel("ui");
		camera.setLevel(main.getName());
		
		camera.getPosition().set(0f, 4.5f, -4.8f);
		camera.getRotation().set(-48f, 0f, 0f);
		
		//world.getLight().getPosition().set(0f, 4f, 0f);
		
		content.createTexture("dark", new File("C:\\Users\\Ced\\JavaProjekte\\SadEngine3D\\Core\\src\\res\\textures\\Wood.jpg"));
		content.createTexture("light", new File("C:\\Users\\Ced\\JavaProjekte\\SadEngine3D\\Core\\src\\res\\textures\\Wood2.jpg"));
		
		content.createMesh("Rect", new File("C:\\Users\\Ced\\JavaProjekte\\SadEngine3D\\Core\\src\\res\\models\\Rect.obj"));
		
		content.createModel("DarkSquare").addPart(content.createPart("DarkSquare_").setMesh("Rect").setTexture("dark").getName());
		content.createModel("LightSquare").addPart(content.createPart("LightSquare_").setMesh("Rect").setTexture("light").getName());
		
		for (int y = 1; y <= 8; y++) {
			for (int x = 1; x <= 8; x++) {
				SadEntity entity = content.createEntity("Square " + x + " " + y).setModel((x + y) % 2 == 0 ? "LightSquare" : "DarkSquare");
				entity.getPosition().set(x - 4.5f, 0f, y - 4.5f);
				entity.getRotation().set(90f, 0f, 0f);
				entity.getScale().set(0.95f);
				main.addEntity(entity.getName());
			}
		}
		
		for (PieceType type : PieceType.values()) {
			models.put(type, new ArrayList<>());
			SadMesh mesh = content.createMesh(type.name() + "Mesh", new File("C:\\Users\\Ced\\JavaProjekte\\SadEngine3D\\Core\\src\\res\\models\\" + type.name().toLowerCase() + ".obj"));
			SadModel lightPiece = content.createModel(type.name() + "LightModel").addPart(content.createPart(type.name() + "LightModel_").setMesh(type.name() + "Mesh").getName()).setTexture("light");
			SadModel darkPiece = content.createModel(type.name() + "DarkModel").addPart(content.createPart(type.name() + "DarkModel_").setMesh(type.name() + "Mesh").getName()).setTexture("dark");
			models.get(type).add(lightPiece);
			models.get(type).add(darkPiece);
		}
		
		//SadEntity uiT = new SadEntity("uiT", darkSquare);
		//ui.add(uiT);
		//uiT.getPosition().set(1.9f, 0.85f, 0);
		
		chess = new Chess("C:\\Chess", "C:\\Chess\\data");
		chess.getOutputManager().getSettings().setAll(true);
	}
	
	@Override
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadInput input = sadness.getInput();
		SadTimer timer = sadness.getTimer();
		
		SadLevel main = content.getLevel("main");
		
		if (game == null) {
			if (input.isPressed(KEY_N)) {
				game = chess.getNewGame();
				
				for (Piece piece : game.getPieces()) {
					SadEntity entity = content.createEntity(String.valueOf(piece.hashCode())).setModel(models.get(piece.getType()).get(piece.isBlack() ? 1 : 0).getName());
					entity.getScale().set(pieceScale);
					entity.getPosition().set((piece.getPosition().getX() - 4.5f) * -1, 0f, piece.getPosition().getY() - 4.5f);
					main.addEntity(entity.getName());
				}
			}
		} else {
			if (input.isPressed(KEY_U)) {
				for (Piece piece : game.getPieces()) {
					main.removeEntity(String.valueOf(piece.hashCode()));
				}
				game = null;
			}
		}
		if (input.isJustReleased(KEY_L)) {
			//world.getLight().getPosition().set(world.getCamera().getPosition());
		}
		
		//System.out.println(world.getCamera().getPosition().z + " " + world.getCamera().getPosition().y + " " + world.getCamera().getRotation().x);
		
		//world.getCamera().getControl().move(timer, input);
	}
	
	@Override
	public void terminate(Sadness sadness) {
		System.out.println("ChessAPI");
		System.out.println("(ɔ) 2018 by Ced");
	}
	
	public static void main(String[] args) {
		new SadEngine(new Chess3D());
	}
}
