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

package com.shatteredpixel.shatteredpixeldungeonultra.items.stones;

import com.shatteredpixel.shatteredpixeldungeonultra.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.ItemSpriteSheet;

public class StoneOfBlast extends Runestone {
	
	{
		image = ItemSpriteSheet.STONE_BLAST;
	}
	
	@Override
	protected void activate(int cell) {
		new Bomb.MagicalBomb().explode(cell);
	}
	
}