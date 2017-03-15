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
import com.lh64.randomdungeon.actors.mobs.Mob;
import com.lh64.randomdungeon.actors.mobs.npcs.Chest;
import com.lh64.randomdungeon.actors.mobs.npcs.Coinbox;
import com.lh64.randomdungeon.actors.mobs.npcs.HpSign;
import com.lh64.randomdungeon.actors.mobs.npcs.Shopkeeper;
import com.lh64.randomdungeon.items.Gold;
import com.lh64.randomdungeon.levels.painters.Painter;
import com.lh64.utils.Random;

public class HubLevel extends Level {

	public static final int SIZE = 7;
	public static int bottomleft = SIZE*WIDTH +1;
	public static int test = (SIZE+1)*WIDTH;
	{
		color1 = 0x801500;
		color2 = 0xa68521;
		
	}
	
	private int pedestal;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_SEWERS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}
	
	@Override
	protected boolean build() {

		Arrays.fill( map, Terrain.WALL );
		Painter.fill( this, 1, 1, SIZE, SIZE, Terrain.EMPTY );
		Painter.fill( this, 2, 2, SIZE-2, SIZE-2, Terrain.EMPTY );
		
		
		entrance = SIZE * WIDTH + SIZE / 2 + 1;
		map[entrance] = Terrain.ENTRANCE;
		map[bottomleft +1] = Terrain.BOOKSHELF;
		
		
		
		pedestal = (SIZE / 2 + 1) * (WIDTH + 1);
		exit = pedestal;
		map[exit] = Terrain.EXIT;
		
		
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
		createShopKeeper();
		createHpSign();
		createChest();
		createCoinbox();
	}
	
	protected void createShopKeeper(){
		Mob shopkeeper = new Shopkeeper();
		shopkeeper.pos = pedestal + 1;
		mobs.add(shopkeeper);
	}
	protected void createHpSign(){
		Mob hpsign = new HpSign();
		hpsign.pos = pedestal-1;
		mobs.add(hpsign);
	}
	protected void createChest(){
		Mob chest = new Chest();
		chest.pos = pedestal -2;
		RegularLevel.chestpos = chest.pos;
		mobs.add(chest);
	}
	protected void createCoinbox(){
		Mob coinbox = new Coinbox();
		coinbox.pos = pedestal - 3;
		mobs.add(coinbox);
	}

	@Override
	protected void createItems() {
		drop( new Gold(), bottomleft );
	}
	

	
	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {

		default:
			return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		default:
			return super.tileDesc( tile );
		}
	}
	
	@Override
	public void addVisuals( Scene scene ) {
		
	}
}
