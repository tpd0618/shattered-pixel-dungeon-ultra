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

package com.shatteredpixel.shatteredpixeldungeonultra.items.armor.glyphs;

import com.shatteredpixel.shatteredpixeldungeonultra.actors.Char;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeonultra.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeonultra.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.CursedWand;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.traps.DisintegrationTrap;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.ItemSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( Bomb.MagicalBomb.class );
		RESISTS.add( ScrollOfPsionicBlast.class );
		RESISTS.add( ScrollOfTeleportation.class );

		RESISTS.add( ElementalBlast.class );
		RESISTS.add( CursedWand.class );
		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( ElementalStrike.class );
		RESISTS.add( Blazing.class );
		RESISTS.add( Shocking.class );
		RESISTS.add( Grim.class );

		RESISTS.add( WarpBeacon.class );
		
		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see:
		// Hero.damage
		// GhostHero.damage
		// Shadowclone.damage
		// ArmoredStatue.damage
		// PrismaticImage.damage
		return damage;
	}
	
	public static int drRoll( Char ch, int level ){
		return Random.NormalIntRange(
				Math.round(level * genericProcChanceMultiplier(ch)),
				Math.round((3 + (level*1.5f)) * genericProcChanceMultiplier(ch)));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}