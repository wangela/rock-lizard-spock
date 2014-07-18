package com.codepath.wangela.apps.rocklizardspock.models;

import java.util.Random;

public enum Weapon {
		ROCK, PAPER, SCISSORS, LIZARD, SPOCK;

	public static Weapon pickWeapon() {
		Random rando = new Random();
		return values()[rando.nextInt(values().length)];
	}
}