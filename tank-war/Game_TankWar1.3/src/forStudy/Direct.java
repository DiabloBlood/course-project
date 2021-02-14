package forStudy;

public class Direct {
	private enum Direction {UP, DOWM, LEFT, RIGHT, STOP};
	
	public static void main(String[] args) {
		Direction dir[] = Direction.values();
		System.out.println(dir[4]);
	}
}
