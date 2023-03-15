/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Char;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Doublespeed;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.RegenBlock;
import com.shatteredpixel.shatteredpixeldungeonultra.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeonultra.items.Item;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeonultra.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.SwarmSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Swarm extends Mob {

	{
		spriteClass = SwarmSprite.class;
		
		HP = HT = 50;
		defenseSkill = 5;

		EXP = 3;
		maxLvl = 9;
		
		flying = true;
	}
	
	private static final float SPLIT_DELAY	= 1f;
	
	int generation	= 0;
	
	private static final String GENERATION	= "generation";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( GENERATION, generation );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		generation = bundle.getInt( GENERATION );
		if (generation > 0) EXP = 0;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 4 );
	}

	@Override
	public int attackProc(Char hero, int damage) {
		damage = super.attackProc(enemy, damage);
		if (enemy == Dungeon.hero && enemy.alignment != this.alignment && Random.Int(3) == 0){
			Buff.prolong(this, Doublespeed.class, Doublespeed.DURATION);
		}
		return damage;
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {

		//accounting for reduced damage on ascension
		int effectiveDmg = (int)Math.ceil(damage / AscensionChallenge.statModifier(this));

		if (HP >= effectiveDmg + 2) {
			ArrayList<Integer> candidates = new ArrayList<>();
			
			int[] neighbours = {pos + 1, pos - 1, pos + Dungeon.level.width(), pos - Dungeon.level.width()};
			for (int n : neighbours) {
				if (!Dungeon.level.solid[n]
						&& Actor.findChar( n ) == null
						&& (Dungeon.level.passable[n] || Dungeon.level.avoid[n])
						&& (!properties().contains(Property.LARGE) || Dungeon.level.openSpace[n])) {
					candidates.add( n );
				}
			}
	
			if (candidates.size() > 0) {
				
				Swarm clone = split();
				clone.HP = (HP - effectiveDmg) / 2;
				clone.pos = Random.element( candidates );
				clone.state = clone.HUNTING;

				GameScene.add( clone, SPLIT_DELAY );
				Actor.addDelayed( new Pushing( clone, pos, clone.pos ), -1 );

				Dungeon.level.occupyCell(clone);
				
				HP -= clone.HP;
			}
		}
		
		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}
	
	private Swarm split() {
		Swarm clone = new Swarm();
		clone.generation = generation + 1;
		clone.EXP = 0;
		if (buff( Burning.class ) != null) {
			Buff.affect( clone, Burning.class ).reignite( clone );
		}
		if (buff( Poison.class ) != null) {
			Buff.affect( clone, Poison.class ).set(2);
		}
		for (Buff b : buffs(AllyBuff.class)){
			Buff.affect( clone, b.getClass());
		}
		for (Buff b : buffs(ChampionEnemy.class)){
			Buff.affect( clone, b.getClass());
		}
		return clone;
	}

	@Override
	public float lootChance() {
		lootChance = 1f/(6 * (generation+1) );
		return super.lootChance() * (5f - Dungeon.LimitedDrops.SWARM_HP.count) / 5f;
	}
	
	@Override
	public Item createLoot(){
		Dungeon.LimitedDrops.SWARM_HP.count++;
		return super.createLoot();
	}
}
