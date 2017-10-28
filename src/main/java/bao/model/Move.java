package bao.model;

public class Move {
	private Row row;
	private int nodeNo;
	private Direction direction;

	public Move(Row row, int nodeNo, Direction direction) {
		this.row = row;
		this.nodeNo = nodeNo;
		this.direction = direction;
	}

	public Row getRow() {
		return row;
	}

	public int getNodeNo() {
		return nodeNo;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "Move [row=" + row + ", nodeNo=" + nodeNo + ", direction=" + direction + "]";
	}
}
