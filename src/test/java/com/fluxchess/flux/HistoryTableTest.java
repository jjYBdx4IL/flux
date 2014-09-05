/*
 * Copyright 2007-2014 the original author or authors.
 *
 * This file is part of Flux Chess.
 *
 * Flux Chess is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Flux Chess is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Flux Chess.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.fluxchess.flux;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HistoryTableTest {

  @Test
  public void testHistoryTable() {
    HistoryTable table = new HistoryTable();

    int move1 = Move.createMove(Move.NORMAL, 16, 32, Piece.PAWN, Piece.NOPIECE, Piece.NOPIECE);
    table.add(move1, 1);
    assertEquals(1, table.get(move1));

    int move2 = Move.createMove(Move.NORMAL, 16, 32, Piece.PAWN, Piece.NOPIECE, Piece.NOPIECE);
    table.add(move2, 1);
    assertEquals(2, table.get(move2));
  }

}
