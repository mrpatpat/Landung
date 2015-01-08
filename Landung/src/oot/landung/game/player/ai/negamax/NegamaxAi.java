package oot.landung.game.player.ai.negamax;

import java.util.List;
import java.util.stream.Collectors;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

/**
 * Negamaxalgorithmus. Bewertet einen Spielsituation und gibt den möglichst
 * besten Zug des Spielers zurück.
 */
public class NegamaxAi {

	/**
	 * Suchtiefe des Algorithmus.
	 */
	private static int maxDepth = 100;

	/**
	 * Sucht den besten Zug. Diese Methode nutzt Javastreams um Parallelität zu
	 * ermöglichen.
	 * 
	 * @param actions
	 *            Liste aller gültigen Züge des Spielers actor
	 * @param actor
	 *            Der Akteur in dieser Runde
	 * @param enemy
	 *            Der Gegner des Akteurs
	 * @param board
	 *            Das aktuelle Spielfeld
	 * @param turn
	 *            Der aktuelle Zug
	 * @return möglichst bester Zug für den Akteur
	 */
	public static Action getBestParallel(List<Action> actions, Player actor,
			Player enemy, Board board, int turn) {

		// Eröffnungszüge
		if (turn == 0) {
			actions = getOpeningMoves(actions, actor, board, turn);
		}

		// keine Aktionen -> null
		if (actions == null)
			return null;

		// hole die Liste mit den Bewertungen
		List<Integer> scores = getScores(actions, actor, enemy, board, turn);

		// gebe die beste Aktion zurück
		return findBestAction(actions, scores);

	}

	/**
	 * Die Negamaximplementation des Minimaxalgorithmus. Er versucht immer den
	 * für sich besten Zug zu wählen und geht davon aus, dass der Gegner das
	 * auch tut. D.h. Im eigenen Zug wählt er den für sich maximalen Zug aus und
	 * im gegnerischen Zug nimmt er den für sich minimalen Zug, da angenommen
	 * wird, dass der Gegner perfekt spielt. Der Negamaxalgorithmus muss immer
	 * nur nach dem maximum suchen, da er pro Schritt das Ergebnis negiert. Der
	 * Algorithmus ist außerdem mit einem Alpha-Beta-Pruning optimiert. D.h. er
	 * merkt sich vorherige Minima und Maxima und berechnet ganze Zweige nicht
	 * mehr, wenn sie außerhalb dieser Grenzen liegen, da sie das Ergebnis nicht
	 * mehr verändern.
	 * 
	 * @param depth
	 *            aktuelle Suchtiefe
	 * @param actor
	 *            der Akteur
	 * @param enemy
	 *            der Gegner
	 * @param board
	 *            das aktuelle Spielfeld
	 * @param turn
	 *            der aktuelle Zug
	 * @param alpha
	 *            untere Schranke
	 * @param beta
	 *            obere Schranke
	 * @return
	 */
	private static int miniMax(int depth, Player actor, Player enemy,
			Board board, int turn, int alpha, int beta) {

		if (depth == 0 || !actor.hasValidActions(board, turn))
			return evaluate(actor, enemy, board, turn, depth);
		int max = alpha;
		List<Action> actions = actor.getValidActions(board, turn);
		for (Action a : actions) {
			Board b2 = board.getTheoreticalNextBoard(a, board, turn + 1);
			int wert = -miniMax(depth - 1, enemy, actor, b2, turn + 1, -beta,
					-max);
			if (wert > max) {
				max = wert;
				if (max >= beta)
					break;
			}
		}
		return max;
	}

	/**
	 * Bewertungsfunktion für eine Spielsituation. Je höher das Ergebnis, desto
	 * besser ist die Situation für den Akteur.
	 * 
	 * @param actor
	 *            der Akteur
	 * @param enemy
	 *            der Gegner
	 * @param board
	 *            das Spielbrett
	 * @param turn
	 *            der aktuelle Zug
	 * @param depth
	 *            die aktuelle Suchtiefe
	 * @return Wert der Situation
	 */
	private static int evaluate(Player actor, Player enemy, Board board,
			int turn, int depth) {

		Player winner = Game.getWinner(board, actor, enemy, turn);

		if (winner == actor) {
			return Integer.MAX_VALUE;
		} else if (winner == enemy) {
			return Integer.MIN_VALUE;
		} else
			return 0;

	}

	private static Action findBestAction(List<Action> actions,
			List<Integer> scores) {
		Action best = actions.get(0);
		int bestScore = scores.get(0);

		for (int i = 1; i < scores.size(); i++) {
			// same -> random
			if (scores.get(i) == bestScore) {
				if (Math.random() <= 0.5d) {
					best = actions.get(i);
					bestScore = scores.get(i);
				}
			} else
			// bigger
			if (scores.get(i) > bestScore) {
				best = actions.get(i);
				bestScore = scores.get(i);
			}
		}

		return best;
	}

	/**
	 * gibt die Eröffnungszüge zurück.
	 * 
	 * @param actions
	 *            Aktionsliste
	 * @param actor
	 *            Akteur
	 * @param board
	 *            aktuelles Spielbrett
	 * @param turn
	 *            Zug
	 * @return Eröffnungszüge
	 */
	private static List<Action> getOpeningMoves(List<Action> actions,
			Player actor, Board board, int turn) {
		actions.clear();
		Action a;

		a = new SetAction(false, actor, new Vector<Integer>(0, 0));
		if (a.isActionValid(board, turn, false))
			actions.add(a);

		a = new SetAction(false, actor, new Vector<Integer>(4, 0));
		if (a.isActionValid(board, turn, false))
			actions.add(a);

		a = new SetAction(false, actor, new Vector<Integer>(0, 4));
		if (a.isActionValid(board, turn, false))
			actions.add(a);

		a = new SetAction(false, actor, new Vector<Integer>(4, 4));
		if (a.isActionValid(board, turn, false))
			actions.add(a);

		return actions;
	}

	/**
	 * Gibt zu einer Aktionsliste und einem Brett die dazugehörigen Bewertungen
	 * zurück.
	 * 
	 * @param actions Liste aller Aktionen
	 * @param actor Der Akteur
	 * @param enemy Der Gegner
	 * @param board Aktuelles Spielbrett
	 * @param turn Aktueller Spielzug
	 * @return Liste der Zugbewertungen
	 */
	private static List<Integer> getScores(List<Action> actions, Player actor,
			Player enemy, Board board, int turn) {

		/*
		 * Aktionsliste wird in einen parallelen Stream geladen. Als nächstes
		 * werden alle Aktionen auf ihre Bewertungen nach minimax gemappt.
		 * Der Stream wird dann eingesammelt und in eine Liste aus Integern konvertiert.
		 */
		return actions
				.stream()
				.parallel()
				.mapToInt(

						a -> miniMax(maxDepth, actor, enemy, board
								.getTheoreticalNextBoard(a, board, turn + 1),
								turn + 1, Integer.MIN_VALUE, Integer.MAX_VALUE)

				).boxed().collect(Collectors.toList());

	}

}
