/*
 * AppUnionFind.java    Jul 7 2014, 01:13
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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Problem: Union Find.
 *
 * We start with the following problem specification: The input is a sequence of
 * pairs of integers, where each integer represents an object of some type and
 * we are to interpret the pair p q as meaning p is connected to q. We assume
 * that is connected to is an equivalence relation, which means that:
 *
 * <ul>
 *   <li><strong>Reflexive</strong>: p is connected to p.</li>
 *   <li><strong>Symmetric</strong>: If p is connected to q, then q is connected to p.</li>
 *   <li><strong>Transitive</strong>: If p is connected to q and q is iss connected to r, then p is connected to r.</li>
 * </ul>
 *
 * @author  Brett Ryan
 */
public final class AppUnionFind {

    public static void main(String[] args) throws IOException {
        String fn = "tinyUF.txt";
//        String fn = "mediumUF.txt";
//        String fn = "largeUF.txt";
        UnionFind uf;
        try (BufferedReader r = App.dataReader(fn)) {
            String line;
            int p, q;
            String[] pq;
            String ct = r.readLine();
            uf = new SolutionQuickFindUnionFind(Integer.parseInt(ct));
            while ((line = r.readLine()) != null) {
                pq = line.split("\\s+");
                p = Integer.parseInt(pq[0]);
                q = Integer.parseInt(pq[1]);
                if (!uf.connected(p, q)) {
                    uf.union(p, q);
                    System.out.format("%d %d\n", p, q);
                }
            }
        }
        System.out.println(uf.count() + " components.");

//        System.out.println(uf.connected(6, 4));
//        System.out.println(uf.connected(3, 9));
    }


    private static interface UnionFind {

        void union(int p, int q);

        int count();

        int find(int p);

        boolean connected(int p, int q);

    }


    /**
     * Base class for solutions derived from book.
     *
     * Solutions are derived based on hints given throughout the book text.
     */
    private static abstract class SolutionUnionFind implements UnionFind {

        protected int[] id;
        protected int count;

        public SolutionUnionFind(int size) {
            id = new int[size];
            for (int i = 0; i < size; i++) {
                id[i] = i;
            }
            count = size;
        }

        @Override
        public abstract void union(int p, int q);

        @Override
        public int count() {
            return count;
        }

        @Override
        public abstract int find(int p);

        @Override
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

    }


    /**
     * Quick find union find solution.
     */
    private static final class SolutionQuickFindUnionFind extends SolutionUnionFind {

        public SolutionQuickFindUnionFind(int size) {
            super(size);
        }

        @Override
        public void union(int p, int q) {
            int pid = find(p);
            int qid = find(q);
            if (pid == qid) {
                return;
            }
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pid) {
                    id[i] = qid;
                }
            }
            count--;
        }

        @Override
        public int find(int p) {
            return id[p];
        }

    }


    /**
     * Data structure that satisfies requirements of union-find problem.
     */
    private static final class MyUnionFind implements UnionFind {

        private final ArrayList<Set<Integer>> sets;

        /**
         * Create a new empty {@link MyUnionFind} instance.
         */
        public MyUnionFind() {
            this.sets = new ArrayList<>();
        }

        /**
         * Add two connected points to the data structure.
         *
         * <ul>
         *  <li>If both points exist they nothing occurs.</li>
         *  <li>If no points exist a new set is added containing both points.</li>
         *  <li>If a set contains one point and another set contains the other,
         *      both sets will be merged.</li>
         *  <li>If a set contains one point the other will be added to that set.</li>
         * </ul>
         *
         * This function will print output:
         *
         * <ul>
         *  <li>If a new relationship is created then this will be printed as
         *      <code>(p, q)</code>.</li>
         *  <li>If a relationship already exists it will be printed as
         *      <code>(p, q) ***</code>.</li>
         * </ul>
         *
         * @param   p
         *          First point.
         * @param   q
         *          Second point.
         */
        @Override
        public void union(int p, int q) {
            Set<Integer> setX = null;
            Set<Integer> setY = null;
            int setYi = -1;
            for (int i = 0; i < sets.size(); i++) {
                Set<Integer> set = sets.get(i);
                if (set.contains(p)) {
                    setX = set;
                }
                if (set.contains(q)) {
                    setY = set;
                    setYi = i;
                }
                if (setX != null && setY != null) {
                    break;
                }
            }
            if (setX == null && setY == null) {
                Set<Integer> set = new HashSet<>();
                sets.add(set);
                set.add(p);
                set.add(q);
            } else if (setX != null && setY != null) {
                if (setX == setY) {
//                    System.out.format("(%d, %d) ***\n", p, q);
                    return;
                } else {
                    setX.addAll(setY);
                    sets.remove(setYi);
                }
            } else if (setX != null) {
                setX.add(q);
            } else if (setY != null) {
                setY.add(p);
            }
//            System.out.format("(%d, %d)\n", p, q);
        }

        @Override
        public int count() {
            return sets.size();
        }

        @Override
        public int find(int p) {
            for (int i = 0; i < sets.size(); i++) {
                Set<Integer> set = sets.get(i);
                if (set.contains(p)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean connected(int p, int q) {
            for (Set<Integer> set : sets) {
                if (set.contains(p) && set.contains(q)) {
                    return true;
                } else if (set.contains(p) || set.contains(q)) {
                    return false;
                }
            }
            return false;
        }

    }

}
