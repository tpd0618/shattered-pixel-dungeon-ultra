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

package com.shatteredpixel.shatteredpixeldungeonultra.items.potions;

import com.shatteredpixel.shatteredpixeldungeonultra.Challenges;
import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.Char;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Inversion;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeonultra.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeonultra.utils.GLog;

public class PotionOfHealing extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_HEALING;

		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		cure( hero );
		heal( hero );
		Talent.onHealingPotionUsed( hero );
	}

	public static void heal( Char ch ){
		if (ch == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
			pharmacophobiaProc(Dungeon.hero);
		} else {
			//starts out healing 30 hp, equalizes with hero health total at level 11
			if (ch.buff(Inversion.class) != null) {
				ch.damage(ch.HT / 2, ch);
				if (ch == Dungeon.hero && !ch.isAlive()) {
					Dungeon.fail(Warlock.class);
					GLog.n( Messages.get(Warlock.class, "ondeath") );
				}
			} else {
				Buff.affect(ch, Healing.class).setHeal((int) (0.8f * ch.HT + 14), 0.25f, 0);
			}
			if (ch == Dungeon.hero){
				GLog.p( Messages.get(PotionOfHealing.class, "heal") );
			}
		}
	}

	public static void pharmacophobiaProc( Hero hero ){
		// harms the hero for ~40% of their max HP in poison
		Buff.affect( hero, Poison.class).set(4 + hero.lvl/2);
	}
	
	public static void cure( Char ch ) {
		Buff.detach( ch, Poison.class );
		Buff.detach( ch, Cripple.class );
		Buff.detach( ch, Weakness.class );
		Buff.detach( ch, Vulnerable.class );
		Buff.detach( ch, Bleeding.class );
		Buff.detach( ch, Blindness.class );
		Buff.detach( ch, Drowsy.class );
		Buff.detach( ch, Slow.class );
		Buff.detach( ch, Vertigo.class);
	}

	@Override
	public int value() {
		return isKnown() ? 30 * quantity : super.value();
	}
}
