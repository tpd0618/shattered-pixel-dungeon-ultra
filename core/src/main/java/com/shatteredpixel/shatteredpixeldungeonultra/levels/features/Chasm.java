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

package com.shatteredpixel.shatteredpixeldungeonultra.levels.features;

import com.shatteredpixel.shatteredpixeldungeonultra.Assets;
import com.shatteredpixel.shatteredpixeldungeonultra.Badges;
import com.shatteredpixel.shatteredpixeldungeonultra.Dungeon;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeonultra.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeonultra.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeonultra.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.Level;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeonultra.levels.rooms.special.WeakFloorRoom;
import com.shatteredpixel.shatteredpixeldungeonultra.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeonultra.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeonultra.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeonultra.sprites.MobSprite;
import com.shatteredpixel.shatteredpixeldungeonultra.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeonultra.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Chasm implements Hero.Doom {

	public static boolean jumpConfirmed = false;
	private static int heroPos;
	
	public static void heroJump( final Hero hero ) {
		heroPos = hero.pos;
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(
						new WndOptions( new Image(Dungeon.level.tilesTex(), 48, 48, 16, 16),
								Messages.get(Chasm.class, "chasm"),
								Messages.get(Chasm.class, "jump"),
								Messages.get(Chasm.class, "yes"),
								Messages.get(Chasm.class, "no") ) {

							private float elapsed = 0f;

							@Override
							public synchronized void update() {
								super.update();
								elapsed += Game.elapsed;
							}

							@Override
							public void hide() {
								if (elapsed > 0.2f){
									super.hide();
								}
							}

							@Override
							protected void onSelect( int index ) {
								if (index == 0 && elapsed > 0.2f) {
									if (Dungeon.hero.pos == heroPos) {
										jumpConfirmed = true;
										hero.resume();
									}
								}
							}
						}
				);
			}
		});
	}
	
	public static void heroFall( int pos ) {
		
		jumpConfirmed = false;
				
		Sample.INSTANCE.play( Assets.Sounds.FALLING );

		Level.beforeTransition();

		if (Dungeon.hero.isAlive()) {
			Dungeon.hero.interrupt();
			InterlevelScene.mode = InterlevelScene.Mode.FALL;
			if (Dungeon.level instanceof RegularLevel) {
				Room room = ((RegularLevel)Dungeon.level).room( pos );
				InterlevelScene.fallIntoPit = room != null && room instanceof WeakFloorRoom;
			} else {
				InterlevelScene.fallIntoPit = false;
			}
			Game.switchScene( InterlevelScene.class );
		} else {
			Dungeon.hero.sprite.visible = false;
		}
	}

	@Override
	public void onDeath() {
		Badges.validateDeathFromFalling();

		Dungeon.fail( Chasm.class );
		GLog.n( Messages.get(Chasm.class, "ondeath") );
	}

	public static void heroLand() {
		
		Hero hero = Dungeon.hero;
		
		FeatherFall.FeatherBuff b = hero.buff(FeatherFall.FeatherBuff.class);
		
		if (b != null){
			hero.sprite.emitter().burst( Speck.factory( Speck.JET ), 20);
			b.detach();
			return;
		}
		
		Camera.main.shake( 4, 1f );

		Dungeon.level.occupyCell(hero );
		Buff.prolong( hero, Cripple.class, Cripple.DURATION );

		//The lower the hero's HP, the more bleed and the less upfront damage.
		//Hero has a 50% chance to bleed out at 66% HP, and begins to risk instant-death at 25%
		Buff.affect( hero, Bleeding.class).set( Math.round(hero.HT / (6f + (6f*(hero.HP/(float)hero.HT)))), Chasm.class);
		hero.damage( Math.max( hero.HP / 2, Random.NormalIntRange( hero.HP / 2, hero.HT / 4 )), new Chasm() );
	}

	public static void mobFall( Mob mob ) {
		if (mob.isAlive()) mob.die( Chasm.class );
		
		if (mob.sprite != null) ((MobSprite)mob.sprite).fall();
	}
	
	public static class Falling extends Buff {
		
		{
			actPriority = VFX_PRIO;
		}
		
		@Override
		public boolean act() {
			heroLand();
			detach();
			return true;
		}
	}

}