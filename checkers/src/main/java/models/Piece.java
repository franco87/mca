package models;

public class Piece {

	Color color;
	private static final int MAX_DISTANCE = 2;

	Piece(Color color) {
		assert color != null;
		this.color = color;
	}

	Error isCorrect(Coordinate origin, Coordinate target, PieceProvider pieceProvider) {

	    Error error = this.commonValidations(origin, target, pieceProvider);
        if (error != null) {
            return error;
        }

		int distance = origin.diagonalDistance(target);
		if (distance > Piece.MAX_DISTANCE) {
			return Error.BAD_DISTANCE;
		}
		if (distance == Piece.MAX_DISTANCE) {
			if (pieceProvider.getPiece(origin.betweenDiagonal(target).get(0)) == null) {
				return Error.EATING_EMPTY;
			}
		}
		return null;
	}

	Error commonValidations(Coordinate origin, Coordinate target, PieceProvider pieceProvider){
		if (!origin.isDiagonal(target)) {
			return Error.NOT_DIAGONAL;
		}
		if (!pieceProvider.isEmpty(target)) {
			return Error.NOT_EMPTY_TARGET;
		}
		if (!this.isAdvanced(origin, target)) {
			return Error.NOT_ADVANCED;
		}

		return null;
	}

	boolean isLimit(Coordinate coordinate){
		return coordinate.getRow()== 0 && this.getColor() == Color.WHITE ||
		coordinate.getRow()== 7 && this.getColor() == Color.BLACK;
	}

	boolean isAdvanced(Coordinate origin, Coordinate target) {
		assert origin != null;
		assert target != null;
		int difference = origin.getRow() - target.getRow();
		if (color == Color.WHITE) {
			return difference > 0;
		}
		return difference < 0;
	}

	Color getColor() {
		return this.color;
	}

}