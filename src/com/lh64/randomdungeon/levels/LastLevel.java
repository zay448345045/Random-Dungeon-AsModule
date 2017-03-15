/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.lh64.randomdungeon.levels;

import java.util.Arrays;

import com.lh64.noosa.Scene;
import com.lh64.randomdungeon.Assets;
import com.lh64.randomdungeon.Dungeon;
import com.lh64.randomdungeon.actors.mobs.Mob;
import com.lh64.randomdungeon.actors.mobs.npcs.Portal;
import com.lh64.randomdungeon.items.Amulet;
import com.lh64.randomdungeon.items.Item;
import com.lh64.randomdungeon.levels.painters.Painter;
import com.lh64.utils.Random;

public class LastLevel extends Level {

	private static final int SIZE = 7;
	
	{
		color1 = 0x801500;
		color2 = 0xa68521;
	}
	
	private int pedestal;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_HALLS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}
	
	@Override
	protected boolean build() {

		Arrays.fill( map, Terrain.WALL );
		Painter.fill( this, 1, 1, SIZE, SIZE, Terrain.WATER );
		Painter.fill( this, 2, 2, SIZE-2, SIZE-2, Terrain.EMPTY );
		Painter.fill( this, SIZE/2, SIZE/2, 3, 3, Terrain.EMPTY_SP );
		
		entrance = SIZE * WIDTH + SIZE / 2 + 1;
		map[entrance] = Terrain.ENTRANCE;
		
		exit = entrance - WIDTH * SIZE;
		map[exit] = Terrain.LOCKED_EXIT;
		
		pedestal = (SIZE / 2 + 1) * (WIDTH + 1);
		
		map[pedestal-1] = map[pedestal+1] = Terrain.STATUE_SP;
		
		feeling = Feeling.NONE;
		
		return true;
	}

	@Override
	protected void decorate() {
		for (int i=0; i < LENGTH; i++) {
			if (map[i] == Terrain.EMPTY && Random.Int( 10 ) == 0) { 
				map[i] = Terrain.EMPTY_DECO;
			}
		}
	}

	@Override
	protected void createMobs() {
		createPortal();
	}
	protected void createPortal() {
		Mob portal = new Portal();
		portal.pos = pedestal;
		mobs.add(portal);
	}
	@Override
	protected void createItems() {
		Item amulet = Dungeon.hero.belongings.getItem(Amulet.class);
		if( amulet == null){
		drop( new Amulet(), pedestal-2 );
		}
	}
	
	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
		case Terrain.WATER:
			return "Cold lava";
		case Terrain.HIGH_GRASS:
			return "Emberfungi";
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return "Pillar";
		default:
			return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Water...";
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return "The pillar is made of real humanoid skulls. Awesome."; 
		default:
			return super.tileDesc( tile );
		}
	}
	
	@Override
	public void addVisuals( Scene scene ) {
		
	}
}
