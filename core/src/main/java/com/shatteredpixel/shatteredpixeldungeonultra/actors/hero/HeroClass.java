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

package com.shatteredpixel.shatteredpixeldungeonultra.actors.hero;

import com.shatteredpixel.shatteredpixeldungeonultra.Assets;
import com.shatteredpixel.shatteredpixeldungeonultra.Badges;
import com.shatteredpixel.shatteredpixeldungeonultra.Challenges;
import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.QuickSlot;
import com.shatteredpixel.shatteredpixeldungeonultra.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.duelist.Feint;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeonultra.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeonultra.items.Item;
import com.shatteredpixel.shatteredpixeldungeonultra.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeonultra.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeonultra.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeonultra.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeonultra.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeonultra.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeonultra.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeonultra.items.spells.MagicalInfusion;
import com.shatteredpixel.shatteredpixeldungeonultra.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.missiles.ThrowingSpike;
import com.shatteredpixel.shatteredpixeldungeonultra.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeonultra.messages.Messages;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {
		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().identify();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
		}
	}

	public boolean isUnlocked(){
		//no unlock system in ultra patch!
		return true;
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
