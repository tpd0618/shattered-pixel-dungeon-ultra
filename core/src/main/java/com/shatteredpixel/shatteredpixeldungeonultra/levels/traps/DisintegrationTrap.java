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

package com.shatteredpixel.shatteredpixeldungeonultra.levels.traps;

import com.shatteredpixel.shatteredpixeldungeonultra.Assets;
import com.shatteredpixel.shatteredpixeldungeonultra.Badges;
import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Char;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeonultra.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeonultra.items.Heap;
import com.shatteredpixel.shatteredpixeldungeonultra.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeonultra.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeonultra.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeonultra.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DisintegrationTrap extends Trap {

	{
		color = VIOLET;
		shape = CROSSHAIR;
		
		canBeHidden = false;
		avoidsHallways = true;
	}

	@Override
	public void activate() {
		Char target = Actor.findChar(pos);
		
		//find the closest char that can be aimed at
		if (target == null){
			float closestDist = Float.MAX_VALUE;
			for (Char ch : Actor.chars()){
				float curDist = Dungeon.level.trueDistance(pos, ch.pos);
				if (ch.invisible > 0) curDist += 1000;
				Ballistica bolt = new Ballistica(pos, ch.pos, Ballistica.PROJECTILE);
				if (bolt.collisionPos == ch.pos && curDist < closestDist){
					target = ch;
					closestDist = curDist;
				}
			}
		}
		
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) heap.explode();
		
		if (target != null) {
			if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[target.pos]) {
				Sample.INSTANCE.play(Assets.Sounds.RAY);
				ShatteredPixelDungeon.scene().add(new Beam.DeathRay(DungeonTilemap.tileCenterToWorld(pos), target.sprite.center()));
			}
			target.damage( Random.NormalIntRange(30, 50) + Dungeon.depth, this );
			if (target == Dungeon.hero){
				Hero hero = (Hero)target;
				if (!hero.isAlive()){
					Badges.validateDeathFromGrimOrDisintTrap();
					Dungeon.fail( getClass() );
					GLog.n( Messages.get(this, "ondeath") );
				}
			}
		}

	}
}
