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

import com.fluxchess.jcpi.models.GenericBoard;
import com.fluxchess.jcpi.models.IllegalNotationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EvaluationTest {

  @Test
  public void testEvaluate() {
    Evaluation evaluation = new Evaluation(new EvaluationTable(1024), new PawnTable(1024));
    Position board = null;

    try {
      board = new Position(new GenericBoard("r6r/1bk2ppp/p2qp3/2b1Q3/Pp3P2/1B2P3/1P2N1PP/R1B3K1 w - -"));
      new See(board);
      int value1 = evaluation.evaluate(board);
      board = new Position(new GenericBoard("r4q1r/1bk2ppp/p2bp3/4Q3/Pp3P2/1B2P3/1P2N1PP/R1B3K1 w - -"));
      new See(board);
      int value2 = evaluation.evaluate(board);
      assertEquals(value1 + " > " + value2, true, value1 > value2);
    } catch (IllegalNotationException e) {
      e.printStackTrace();
    }
  }

}
