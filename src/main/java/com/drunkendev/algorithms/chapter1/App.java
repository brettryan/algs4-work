/*
 * App.java    Jul 7 2014, 03:03
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author  Brett Ryan
 */
public final class App {

    public static final Path DATA_PATH;

    static {
        DATA_PATH = Paths.get(new File(".").getAbsolutePath(), "target/test-resources/algs4-data/").normalize();
    }

    public static Path dataFile(String fileName) {
        return DATA_PATH.resolve(fileName);
    }

    public static InputStream dataStream(String fileName) throws IOException {
        return Files.newInputStream(dataFile(fileName));
    }

    public static BufferedReader dataReader(String fileName) throws IOException {
        return Files.newBufferedReader(dataFile(fileName));
    }

}
