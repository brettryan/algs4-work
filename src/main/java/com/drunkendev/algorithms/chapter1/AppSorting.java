/*
 * AppSorting.java    Jul 8 2014, 04:07
 *
 * Copyright (C) 2014 Drunken Dev.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.drunkendev.algorithms.chapter1;


/**
 *
 * @author  Brett Ryan
 */
public class AppSorting {

    public static void main(String[] args) {

    }

    private static void sort(Comparable[] x) {

    }

    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }

    private static void exch(Comparable[] a, int idx1, int idx2) {
        Comparable temp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = temp;
    }

    private static void show(Comparable[] a) {
        for (Comparable c : a) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

}
