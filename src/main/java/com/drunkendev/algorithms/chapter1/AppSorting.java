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

import edu.princeton.cs.algs4.Stopwatch;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 *
 * @author  Brett Ryan
 */
public class AppSorting {

    private static int ex;

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    public static void main(String[] args) throws IOException {
        String[] a = load("tiny.txt");
        new ShellSort().sort(a);
        // Assert not working for NB run-file.
        System.out.println("Exchange: " + ex);
        if (!isSorted(a)) {
            throw new AssertionError("Array is not sorted");
        }
        show(a);
        ex = 0;
        new ShellSort().sort(a);
        System.out.println("Exchange: " + ex);
    }

    private static String[] load(String fn) throws IOException {
//        In.readStrings();
        List<String> strings = new ArrayList<>();
        try (BufferedReader r = App.dataReader(fn)) {
            String line;
            while ((line = r.readLine()) != null) {
                strings.addAll(Arrays.asList(WHITESPACE_PATTERN.split(line)));
            }
        }
        return strings.toArray(new String[strings.size()]);
    }

//    private static void sort(Comparable[] x, Sorter sorter) {
//        sorter.sort(x);
//    }
    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }

    private static void exch(Comparable[] a, int idx1, int idx2) {
        Comparable temp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = temp;
        ex++;
    }

    private static void show(Comparable[] a) {
        for (Comparable c : a) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static final Pattern WHITESPACE_PATTERN
            = Pattern.compile("\\p{javaWhitespace}+");


    private static abstract class Sorter {

        protected int ac;

        public void sort(Comparable[] x) {
            Stopwatch sw = new Stopwatch();
            sortImpl(x);
            System.out.println("Sort time: " + sw.elapsedTime());
            System.out.println("Accesses: " + ac);
        }

        abstract void sortImpl(Comparable[] x);

    }


    private static final class SelectionSort extends Sorter {

        @Override
        public void sortImpl(Comparable[] arr) {
            int idx;
            for (int i = 0; i < arr.length; i++) {
                ac++;
                idx = i;
                for (int j = i + 1; j < arr.length; j++) {
                    ac++;
                    if (less(arr[j], arr[idx])) {
                        idx = j;
                    }
                }
                exch(arr, i, idx);
            }
        }

    }


    private static final class InsertionSort extends Sorter {

        @Override
        public void sortImpl(Comparable[] arr) {
            for (int i = 1; i < arr.length; i++) {
                ac++;
                for (int j = i; j > 0; j--) {
                    ac++;
                    if (less(arr[j], arr[j - 1])) {
                        exch(arr, j, j - 1);
                    } else {
                        break;
                    }
                }
            }
        }

    }


    private static final class ShellSort extends Sorter {

        private final InsertionSort is;

        public ShellSort() {
            this.is = new InsertionSort();
        }

        @Override
        void sortImpl(Comparable[] arr) {
            int h = 1;
            int max = arr.length / 3;
            while (h < max) {
                h = 3 * h + 1;
            }

            while (h >= 1) {
                for (int i = h; i < arr.length; i++) {
                    ac++;
                    for (int j = i; j >= h; j -= h) {
                        ac++;
                        if (less(arr[j], arr[j - h])) {
                            exch(arr, j, j - h);
                        } else {
                            break;
                        }
                    }
                }
                h /= 3;
            }
        }

    }

}
