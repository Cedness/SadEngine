package de.ced.sadengine.sekokan;

import de.ced.sadengine.objects.*;
import de.ced.sadengine.utils.SadVector;

import java.util.*;

import static de.ced.sadengine.utils.SadValue.*;

public class Sekokengine extends SadEngine {
	
	private static String[][] IMAGE_NAMES =
			{{"hintergrund.gif", "mauer.gif", "hintergrund.gif", "hintergrund.gif", "ziel.gif", "ziel.gif", "ziel.gif", "hintergrund.gif"},
					{null, null, "kiste.gif", "spieler.gif", null, "spieler.gif", "kiste.gif", null}};
	private SadModel[][] models = new SadModel[2][8];
	private final int[][][] referenceFields;
	private SadLevel[] levels = new SadLevel[114];
	private List<HashMap<SadEntity, SadVector>> targets = new ArrayList<>();
	private SadEntity[][][] entities = new SadEntity[114][20][20];
	private Map<SadEntity, Float> movingEntities = new HashMap<>();
	
	private int currentLevel = 0;
	
	private final Sekokan main;
	private SadCamera camera;
	
	private boolean pressed = false;
	private boolean lvlKeyPressed = false;
	
	Sekokengine(Sekokan main, int[][][] referenceFields) {
		super(0);
		this.main = main;
		this.referenceFields = referenceFields;
		setName("Sekokan");
		setFullscreen(true);
		setWidth(1920);
		setHeight(1080);
		start();
	}
	
	@Override
	public void setup() {
		SadMesh mesh = new SadMesh("./Core/res/models/Cube.obj");
		for (int h = 0; h < IMAGE_NAMES.length; h++) {
			for (int i = 0; i < IMAGE_NAMES[0].length; i++) {
				if (IMAGE_NAMES[h][i] == null || IMAGE_NAMES[h][i].equals("hintergrund.gif"))
					continue;
				SadModel model = new SadModel();
				model.setMesh(mesh);
				if (!IMAGE_NAMES[h][i].equals("hintergrund.gif"))
					model.setTexture(new SadTexture("./Core/res/textures/images/" + IMAGE_NAMES[h][i]));
				else
					model.getColor().set(54f / 255f);
				models[h][i] = model;
			}
		}
		
		for (int i = 0; i < levels.length; i++) {
			targets.add(new HashMap<>());
			levels[i] = new SadLevel();
			for (int x = 0; x < entities[0].length; x++) {
				for (int y = 0; y < entities[0][0].length; y++) {
					int type = referenceFields[i][x][y];
					SadModel model = models[0][type];
					if (model == null)
						continue;
					SadEntity entity = new SadEntity();
					entity.setModel(model);
					entity.getPosition().set(0f + x, y, Sekokan.isWall(type) ? -1 : 0);
					levels[i].addEntity(entity);
				}
			}
		}
		
		getColor().set(1f, 0.7f, 1f);
		
		camera = new SadCamera();
		
		setCamera(camera);
		//camera.setOrtho(true);
		camera.getScale().set(0.1f);
		camera.getPosition().set(10f, 12f, -10f);
		camera.getRotation().set(-10f, 0f, 0f);
		//camera.getRotation().set(0f, 90f, 0f);
		//camera.setLevel(levels[0]);
		//getMover().add(levels[0]);
		
		main.setup();
	}
	
	@Override
	public void update() {
		boolean left = getInput().isPressed(KEY_LEFT);
		boolean right = getInput().isPressed(KEY_RIGHT);
		boolean up = getInput().isPressed(KEY_UP);
		boolean down = getInput().isPressed(KEY_DOWN);
		if (!(left || right || up || down)) {
			pressed = false;
		} else if (!pressed) {
			int x = 0, y = 0;
			if (left) {
				x = 1;
			} else if (right) {
				x = -1;
			} else if (up) {
				y = 1;
			} else if (down) {
				y = -1;
			}
			main.move(x, y);
			System.out.println("lol");
			pressed = true;
		}
		boolean inc = getInput().isPressed(KEY_PAGE_UP);
		boolean dec = getInput().isPressed(KEY_PAGE_DOWN);
		boolean incLarge = getInput().isPressed(KEY_KP_ADD);
		boolean decLarge = getInput().isPressed(KEY_KP_SUBTRACT);
		boolean start = getInput().isPressed(KEY_HOME);
		boolean end = getInput().isPressed(KEY_END);
		if (!(inc || dec || incLarge || decLarge || start || end)) {
			lvlKeyPressed = false;
		} else if (!lvlKeyPressed) {
			int level = main.getLevel();
			if (inc) {
				level++;
			} else if (dec) {
				level--;
			} else if (incLarge) {
				level += 10;
			} else if (decLarge) {
				level -= 10;
			} else if (start) {
				level = 0;
			} else if (end) {
				level = 113;
			}
			main.setLevel(level);
			lvlKeyPressed = true;
		}
		
		
		List<SadEntity> entities = new ArrayList<>(movingEntities.keySet());
		for (SadEntity entity : entities) {
			float timeLeft = movingEntities.get(entity);
			movingEntities.replace(entity, timeLeft - getInterval());
			if (Math.abs(timeLeft) > 0.05f)
				continue;
			movingEntities.remove(entity);
			entity.getVelocity().getPosition().set(0f, 0f);
			entity.getPosition().set(targets.get(currentLevel).get(entity));
		}
	}
	
	void load(int level) {
		for (SadEntity entity : targets.get(currentLevel).keySet()) {
			levels[currentLevel].removeEntity(entity);
		}
		entities[currentLevel] = new SadEntity[20][20];
		targets.set(currentLevel, new HashMap<>());
		getMover().remove(levels[currentLevel]);
		getMover().add(levels[level]);
		camera.setLevel(levels[level]);
		movingEntities = new HashMap<>();
		for (int x = 0; x < entities[0].length; x++) {
			for (int y = 0; y < entities[0][0].length; y++) {
				int type = referenceFields[level][x][y];
				SadModel model = models[1][type];
				if (model == null)
					continue;
				SadEntity entity = new SadEntity();
				entity.setModel(model);
				entity.getPosition().set((float) x, y, -1);
				entity.getScale().set(0.8f);
				entity.setName(model.getTexture().getName());
				levels[level].addEntity(entity);
				entity.setVelocityEnabled(true);
				entities[level][x][y] = entity;
				targets.get(level).put(entity, new SadVector().set((float) x, y));
			}
		}
		currentLevel = level;
	}
	
	
	void move(int xTarget, int yTarget, int xFrom, int yFrom) {
		System.out.println(Arrays.toString(entities[currentLevel]));
		SadEntity entity = entities[currentLevel][xFrom][yFrom];
		entities[currentLevel][xFrom][yFrom] = null;
		entities[currentLevel][xTarget][yTarget] = entity;
		System.out.print("move ");
		if (entity == null)
			return;
		System.out.println(entity.getModel().getTexture().getName());
		SadVector target = new SadVector((float) xTarget, yTarget);
		targets.get(currentLevel).get(entity).set(target);
		//entity.getPosition().set(target);
		entity.getVelocity().getPosition().set(target.add(entity.getPosition().clone().negate()).mul(4f));
		movingEntities.put(entity, 0.3f);
	}
}
