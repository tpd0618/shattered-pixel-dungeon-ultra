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

package com.shatteredpixel.shatteredpixeldungeonultra.items.bombs;

import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Char;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeonultra.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeonultra.utils.BArray;
import com.watabou.utils.PathFinder;

public class FrostBomb extends Bomb {
	
	{
		image = ItemSpriteSheet.FROST_BOMB;
	}
	
	@Override
	public void explode(int cell) {
		super.explode(cell);
		PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				GameScene.add(Blob.seed(i, 10, Freezing.class));
				Char ch = Actor.findChar(i);
				if (ch != null){
					Buff.affect(ch, Frost.class, 2f);
				}
			}
		}
	}
	
	@Override
	public int value() {
		//prices of ingredients
		return quantity * (20 + 30);
	}
}
