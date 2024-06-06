import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> history = new ArrayList<>();
		ArrayList<String> Answer = new ArrayList<>();
		Bottle[] bottles = new Bottle[14];

		bottles[0] = new Bottle("Blue", "Yellow", "Sky", "Green");
		bottles[1] = new Bottle("Leaf", "Purple", "Gray", "White");
		bottles[2] = new Bottle("Gray", "Brown", "Red", "Leaf");
		bottles[3] = new Bottle("Orange", "Red", "White", "Yellow");
		bottles[4] = new Bottle("Pink", "Yellow", "Red", "Sky");
		bottles[5] = new Bottle("Leaf", "Red", "Green", "Gray");
		bottles[6] = new Bottle("Purple", "Blue", "Sky", "Pink");
		bottles[7] = new Bottle("Blue", "Brown", "Orange", "Brown");
		bottles[8] = new Bottle("Purple", "White", "White", "Pink");
		bottles[9] = new Bottle("Green", "Sky", "Yellow", "Blue");
		bottles[10] = new Bottle("Pink", "Purple", "Leaf", "Orange");
		bottles[11] = new Bottle("Gray", "Green", "Orange", "Brown");
		bottles[12] = new Bottle(4);
		bottles[13] = new Bottle(4);

		Bottle[] origin = copy(bottles);
		sort(history, bottles, Answer);

		int index = 0;
		for (String str : Answer) {
			print(origin);
			System.out.printf("%2d | %5s", index, str);
			System.out.println();
			String[] split = str.split("->");
			origin[Integer.parseInt(split[1])].insert(origin[Integer.parseInt(split[0])]);
			index++;
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		print(origin);
	}

	public static String toString(Bottle[] bottles) {
		String str = "";
		for (Bottle bottle : bottles) {
			str += bottle.toString();
		}
		return str;
	}

	public static boolean sort(ArrayList<String> history, Bottle[] bottles, ArrayList<String> Answer) {

		Bottle[] origin = copy(bottles);
		// 배열을 돌면서 확인
		for (int i = 0; i < bottles.length; i++) {
			for (int j = 0; j < bottles.length; j++) {
				if (i != j && bottles[i].checkInsert(bottles[j])) {

					history.add(toString(bottles));
					bottles[j].insert(bottles[i]);
					if (history.contains(toString(bottles))) {
						return false;
					}
					Answer.add(i + "->" + j);
					if (isSorted(bottles)) {
						return true;
					} else {
						if (sort(history, bottles, Answer)) {
							return true;
						} else {
							bottles = copy(origin);
							history.remove(history.size() - 1);
							Answer.remove(Answer.size() - 1);
						}
					}
				}

			}
		}
		return false;
	}

	private static boolean isSorted(Bottle[] bottles) {
		for (Bottle bottle : bottles) {
			if (!bottle.sorted()) {
				return false;
			}
		}
		return true;
	}

	private static Bottle[] copy(Bottle[] bottles) {
		Bottle[] newBottles = new Bottle[bottles.length];
		for (int i = 0; i < bottles.length; i++) {
			newBottles[i] = new Bottle(bottles[i].bottle);
		}
		return newBottles;
	}

	private static void print(Bottle[] bottles) {
		System.out.println(" _________________________________________");
		for (int i = 0; i < bottles.length; i++) {
			System.out.printf("%2d", i);
			bottles[i].print();
			System.out.println();
		}
		System.out.println(" _________________________________________");

	}

}
