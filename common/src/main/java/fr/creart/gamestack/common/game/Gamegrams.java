/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

/**
 * Gamegrams utils (eg: conversion)
 *
 * @author Creart
 */
public class Gamegrams {

    private static final float COEFFICIENT = 102400F;

    /**
     * Returns the amount of gamegrams for the given RAM
     *
     * @param ram amount of RAM
     * @return the amount of gamegrams for the given RAM
     */
    public static float fromRam(long ram)
    {
        return (float) ram / COEFFICIENT;
    }

    /**
     * Returns the amount of RAM for the given gamegrams
     *
     * @param gamegrams amount of gamegrams
     * @return the amount of RAM for the given gamegrams
     */
    public static long toRam(float gamegrams)
    {
        return Math.round(gamegrams * COEFFICIENT);
    }

}
