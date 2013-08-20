package com.greatcow.nomad.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.Nomad;

public class Unit extends Actor {
	// helpers-----------------------------------------------
	private class unitmover extends Action {		
		@Override
		public boolean act(float delta) {
			if (ustate == UnitState.Moving) {
				translate(velocity.x * delta, velocity.y * delta);

				if (destination.dst(getX(), getY()) < 2) {
					setToIdle();
					removeAction(this);
				}
			}
			return false;
		}
	}
	// helpers===============================================

	// helpers----------------------------------------------
	public enum UnitState {
		Idle, OrderMove, Moving, OrderAttack, Attacking
	};

	public enum Alignment {
		Friendly, Neutral, Enemy
	};
	// helpers==============================================

	// varblok----------------------------------------------
	// render data
	public UnitStyle style;
	// range circle
	float circleRadius = -1;
	Color circleColor = Color.BLACK;
	// unit control
	private Vector2 destination = new Vector2(0, 0);
	private Vector2 velocity = new Vector2(0, 0);
	Alignment alignment = Alignment.Friendly;
	UnitState ustate = UnitState.Idle;
	// turn control
	private int AP = 10;
	private int maxAP = 10;
	private int incAP = 10;
	// movement control
	private float distPerAP = 100;
	private float movespeed = 350;
	// actions
	private unitmover moveAction = new unitmover();

	// varblok==============================================

	// constructors-----------------------------------------
	public Unit(UnitStyle s) {
		style = s;
	}

	public Unit(UnitStyle s, float x, float y) {
		style = s;
		setPosition(x, y);
	}

	// constructors=========================================

	// render settings--------------------------------------
	public void setAsFriendly() {
		alignment = Alignment.Friendly;
		setWidth(style.friendlySprite.getRegionWidth());
		setHeight(style.friendlySprite.getRegionHeight());
	}

	public void setAsEnemy() {
		alignment = Alignment.Enemy;
		setWidth(style.enemySprite.getRegionWidth());
		setHeight(style.enemySprite.getRegionHeight());
	}

	public void drawRangeCircle(float range, Color ccolor) {
		circleRadius = range;
		circleColor = ccolor;
	}

	private void drawSprite(SpriteBatch batch) {
		if (style != null) {
			switch (alignment) {
			case Friendly:
			case Neutral:
				batch.draw(style.friendlySprite, getX(), getY());
				break;
			case Enemy:
				batch.draw(style.enemySprite, getX(), getY());
				break;
			default:
				break;
			}
		}
	}

	private void drawUnitExtras(SpriteBatch batch) {
		if (circleRadius > 0) {
			batch.end();
			
			Nomad.shapeRenderer.begin(ShapeType.FilledCircle);
			Nomad.shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			Nomad.shapeRenderer.setColor(Color.RED);
			Nomad.shapeRenderer.filledCircle(getX(), getY(), 100);
			Nomad.shapeRenderer.end();
			
			batch.begin();
		}
	}

	public boolean contains(float x, float y) {
		if (x < getX() && x > getX() + getWidth()) {
			return false;
		}
		if (y < getY() && y > +getHeight()) {
			return false;
		}
		return true;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawUnitExtras(batch);
		drawSprite(batch);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	// render settings======================================

	// action controls--------------------------------------
	// idle commands-----
	public void setToIdle() {
		switch (ustate) {
		case Attacking:
			break;
		case Moving:
			velocity.set(0, 0);
			setPosition(destination.x, destination.y);
			break;
		case OrderMove:
		case OrderAttack:
			drawRangeCircle(-1, Color.BLACK);
			break;
		case Idle:
			break;
		default:
			break;
		}
		ustate = UnitState.Idle;
	}

	// turn---------
	public void startTurn() {
		AP = Math.min(AP + incAP, maxAP);
		setToIdle();
		setAsFriendly();
	}

	public void endTurn() {
		setToIdle();
		setAsEnemy();
	}

	// movement-----
	public float moveRange() {
		return AP * distPerAP;
	}

	public void startMoveOrder() {
		ustate = UnitState.OrderMove;
		drawRangeCircle(moveRange(), style.moveCircleColor);
	}

	public boolean doMove(float x, float y) {
		if ((((getX() - x) * (getX() - x)) + ((getY() - y) * (getY() - y))) <= moveRange()
				* moveRange()) {
			destination.set(x, y);
			System.out.println("new dest: " + destination.x + " "
					+ destination.y);
			velocity.set(destination).sub(getX(), getY());
			velocity.mul(movespeed / velocity.len());
			ustate = UnitState.Moving;
			addAction(moveAction);
			return true;
		}
		return false;
	}

	// combat-------
	public void startCombatOrder() {

	}

	public void doCombat(Unit u) {

	}
	// action controls======================================
}
