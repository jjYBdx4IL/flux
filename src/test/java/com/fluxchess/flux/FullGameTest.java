/*
 * Copyright 2007-2016 the original author or authors.
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

import com.fluxchess.flux.board.Hex88Board;
import com.fluxchess.flux.move.IntMove;
import com.fluxchess.jcpi.commands.*;
import com.fluxchess.jcpi.models.GenericBoard;
import com.fluxchess.jcpi.models.GenericMove;
import com.fluxchess.jcpi.protocols.IProtocolHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class FullGameTest implements IProtocolHandler {

	BlockingQueue<IEngineCommand> commandQueue = new LinkedBlockingQueue<IEngineCommand>();
	EngineStartCalculatingCommand startCommand = new EngineStartCalculatingCommand();
	Hex88Board board = new Hex88Board(new GenericBoard(GenericBoard.STANDARDSETUP));
	boolean found = false;
	
	public FullGameTest() {
		System.out.println(board.getBoard().toString2D());
  		startCommand.setDepth(3);
		this.commandQueue.add(new EngineInitializeRequestCommand());
		this.commandQueue.add(new EngineNewGameCommand());
		this.commandQueue.add(new EngineAnalyzeCommand(board.getBoard(), new ArrayList<GenericMove>()));
		this.commandQueue.add(startCommand);
	}
	
	@Test
	public void testMate30() {
		new Flux(this).run();
	}
	
	public void send(IProtocolCommand command) {
		command.accept(this);
	}

	@Override
	public IEngineCommand receive() {
		IEngineCommand command = null;
		try {
			command = this.commandQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assert command != null;
		
		System.out.println("rcve " + command);

		return command;
	}

	@Override
	public void send(ProtocolInitializeAnswerCommand command) {
		System.out.println("send " + command);
	}

	@Override
	public void send(ProtocolReadyAnswerCommand command) {
		System.out.println("send " + command);
	}

	@Override
	public void send(ProtocolBestMoveCommand command) {
		System.out.println("send " + command);
		System.out.println(board.getBoard().getActiveColor() + " move #" + board.getFullMoveNumber() + " : " + command.bestMove);
		if (command.bestMove == null) {
		    this.commandQueue.add(new EngineQuitCommand());
		    return;
		}
		board.makeMove(IntMove.convertMove(command.bestMove, board));
		System.out.println(board.getBoard().toString2D());
		this.commandQueue.add(new EngineAnalyzeCommand(board.getBoard(), new ArrayList<GenericMove>()));
		this.commandQueue.add(startCommand);
	}

	@Override
	public void send(ProtocolInformationCommand command) {
		if (command.getMate() != null) {
			if (command.getMate() == 30) {
				this.found = true;
			}
		}
		System.out.println("send " + command);
	}

	@Override
	public String toString() {
		return "FluxTesting Protocol";
	}

}
