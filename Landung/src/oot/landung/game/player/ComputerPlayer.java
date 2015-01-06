package oot.landung.game.player;

import java.util.List;
import java.util.Scanner;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.ai.AiInterface;
import oot.landung.game.player.ai.SmartAi;
import oot.landung.game.player.ai.StupidAi;
import oot.landung.game.utils.Utils;

/**
 * Computerspieler mit 5 Spielstufen (0-4)
 * 
 * @author Adrian
 *
 */
public class ComputerPlayer extends Player {

	protected int level;
	private AiInterface stupid;
	private AiInterface smart;

	/**
	 * Erzeugt einen Computerspieler mit einer KI Stufe
	 * 
	 * @param id
	 *            id
	 * @param level
	 *            KI Stufe 0-4
	 */
	public ComputerPlayer(int id) {
		super(id);
		this.level = askForLevel();
		setName(genName());
		this.stupid = new StupidAi();
		this.smart = new SmartAi();
	}

	private String genName() {

		switch (level) {
		case 0:
			return "Peter";
		case 1:
			return "Tyrone";
		case 2:
			return "Ivo";
		case 3:
			return "Thomas";
		case 4:
			return "Klaus";
		default:
			return "n/a";
		}
	}

	public int askForLevel() {
		Scanner in = Utils.getScanner();

		int level = -1;

		do {
			System.out.println("Geben Sie das Level von " + this.getPlayerID() + " ein (1-5): ");
			String lvl = in.nextLine();
			if (lvl.matches("[1-5]\\b")) {
				level = Integer.parseInt(lvl) - 1;
			} else {
				System.out.println("ungueltige Eingabe.");
			}
		} while (level == -1);

		return level;
	}

	@Override
	public void notifyWinner() {
		switch (level) {
		case 0:
			System.out.println("Peter hat gewonnen...");
			break;
		case 1:
			System.out.println("Tyrone hat gewonnen.");
			break;
		case 2:
			System.out.println("Ivo hat gewonnen.");
			break;
		case 3:
			System.out.println("Thomas hat gewonnen.");
			break;
		case 4:
			System.out.println("Nöööög! Klaus hat mal wieder dominiert und das Spiel gewonnen.");
			break;
		default:
			System.out.println("n/a hat gewonnen.");
			break;
		}
	}

	@Override
	public String askforName() {
		return "";
	}

	/**
	 * Gibt einen Zug des Spielers zurï¿½ck. Benutzt je nach Stufe Zï¿½ge der
	 * intelligenten und dummen KI (in einem Verhï¿½ltnis linear zum Level).
	 * 
	 * @param turn
	 *            der zug
	 * @param board
	 *            das Spielfeld
	 * @return der Zug
	 */
	@Override
	public Action askforAction(Game g) {
		
		Player enemy  = g.getPlayer()[0] == this ? g.getPlayer()[1]:g.getPlayer()[0];

		List<Action> actions = getValidActions(g.getBoard(), g.getTurn());
		
		int rand = (int) (Math.random() * 5);

		if (rand < level) {
			return smart.getNextAction(g.getBoard(), actions, g.getTurn(),enemy);
		} else {
			return stupid.getNextAction(g.getBoard(), actions, g.getTurn(),enemy);
		}

	}

	@Override
	public void notifyUnvalidMove(String message) {
		// irrelevant
		System.out.println(message);
	}

	/**
	 * Gibt einen Entfernen-Zug des Spielers zurï¿½ck. Benutzt je nach Stufe Zï¿½ge
	 * der intelligenten und dummen KI (in einem Verhï¿½ltnis linear zum Level).
	 * 
	 * @param turn
	 *            der zug
	 * @param board
	 *            das Spielfeld
	 * @return der Zug
	 */
	@Override
	public RemoveAction askforRemoveAction(Game g) {
		int rand = (int) (Math.random() * 5);
		Player enemy  = g.getPlayer()[0] == this ? g.getPlayer()[1]:g.getPlayer()[0];
		if (rand < level) {
			return smart.getNextRemoveAction(g.getBoard(), this.getValidRemoveActions(g.getBoard(), g.getTurn()),g.getTurn(),enemy);
		} else {
			return stupid.getNextRemoveAction(g.getBoard(), this.getValidRemoveActions(g.getBoard(), g.getTurn()),g.getTurn(),enemy);
		}
	}

}
