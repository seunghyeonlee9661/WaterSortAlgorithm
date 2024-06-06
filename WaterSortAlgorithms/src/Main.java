import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		ArrayList<String[][]> visited = new ArrayList<>();
		String[][] stacks = new String[14][4];

		stacks[0] = new String[] { "Blue", "Yellow", "Sky", "Green" };
		stacks[1] = new String[] { "Leaf", "Purple", "Gray", "White" };
		stacks[2] = new String[] { "Gray", "Brown", "Red", "Leaf" };
		stacks[3] = new String[] { "Orange", "Red", "White", "Yellow" };
		stacks[4] = new String[] { "Pink", "Yellow", "Red", "Sky" };
		stacks[5] = new String[] { "Leaf", "Red", "Green", "Gray" };
		stacks[6] = new String[] { "Purple", "Blue", "Sky", "Pink" };
		stacks[7] = new String[] { "Blue", "Brown", "Orange", "Brown" };
		stacks[8] = new String[] { "Purple", "White", "White", "Pink" };
		stacks[9] = new String[] { "Green", "Sky", "Yellow", "Blue" };
		stacks[10] = new String[] { "Pink", "Purple", "Leaf", "Orange" };
		stacks[11] = new String[] { "Gray", "Green", "Orange", "Brown" };
		stacks[12] = new String[] { null, null, null, null };
		stacks[13] = new String[] { null, null, null, null };

//		String[][] stacks = new String[4][3];
//
//		stacks[0] = new String[] { "red", "yellow", "blue" };
//		stacks[1] = new String[] { "blue", "yellow", "red" };
//		stacks[2] = new String[] { "red", "blue", "yellow" };
//		stacks[3] = new String[] { null, null, null };

		print(stacks);
		sort(visited, stacks);
		print(stacks);
	}

	public static boolean sort(ArrayList<String[][]> visited, String[][] stacks) {

		if (checkError(stacks)) {
			System.out.println("Error!!!!!");
			return true;
		}

		// 시간 제어
//		try {
//			TimeUnit.SECONDS.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// 원본 저장을 위한 변수
		String[][] history = new String[stacks.length][stacks[0].length];

		// 배열을 돌면서 확인
		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks.length; j++) {
				// 현재 값이 이동이 가능한지 확인
				if (isMovable(stacks, i, j)) {
					System.out.println(i + "에서 " + j + "로 이동 가능");

					// 해당 내용을 먼저 복사
					copy(history, stacks);

					// 방문 기록에 내용를 기록
					visited.add(history);

					// 내용물 이동
					move(stacks, i, j);

					// 만약 변동 내용이 이미 갔던 단계라고 한다면
					if (checkVisited(visited, stacks)) {
						// false 반환
						return false;
					}
					// 배열이 정렬된 상태라면
					if (sorted(stacks)) {
						// true 반환;
						return true;
						// 배열이 정렬이 안된 상태라면
					} else {
						// 재귀 : 다음 단계 진행
						if (sort(visited, stacks)) {
							// true 반환 -> 정렬 완료
							return true;
						} else {
							// false 반환 -> 해당 단계가 이미 방문했거나 더 이상 움직일 수가 없는 경우
							// 원본으로 다시 바꾸기
							copy(stacks, history);
							// 방문 기록을 제거
							visited.remove(visited.size() - 1);
						}
					}
				}

			}
		}
		System.out.println("더 이상 진행 과정이 없음!!!!!");
		return false;
	}

	public static boolean checkError(String[][] stacks) {
		ArrayList<String> colors = new ArrayList<>();
		ArrayList<Integer> cnt = new ArrayList<>();

		for (int i = 0; i < stacks.length; i++) {
			for (int j = 0; j < stacks[0].length; j++) {
				String color = stacks[i][j];
				if (color == null)
					continue;
				int index = colors.indexOf(color);
				if (index < 0) {
					colors.add(color);
					cnt.add(1);
				} else {
					cnt.set(index, cnt.get(index) + 1);
				}
			}
		}

		System.out.println(" _________________________________________");
		for (int i = 0; i < colors.size(); i++) {
			System.out.printf("%7s | %2d", colors.get(i), cnt.get(i));
			System.out.println("");
			if (cnt.get(i) != 4)
				return true;
		}
		System.out.println(" _________________________________________");

		return false;

	}

	public static void copy(String[][] target, String[][] origin) {
		for (int i = 0; i < origin.length; i++) {
			for (int j = 0; j < origin[0].length; j++) {
				target[i][j] = origin[i][j];
			}
		}
	}

	public static boolean checkVisited(ArrayList<String[][]> list, String[][] newStack) {

		for (String[][] stack : list) { // for문을 통한 전체출력

			if (Arrays.deepEquals(stack, newStack)) {
				return true;
			}
		}
		return false;

	}

	public static void print(String[][] stacks) {
		System.out.println(" _________________________________________");
		for (int i = 0; i < stacks.length; i++) {
			System.out.printf("%2d | ", i);
			for (int j = 0; j < stacks[0].length; j++) {
				if (stacks[i][j] != null)
					System.out.printf("%7s | ", stacks[i][j]);
				else
					System.out.printf("%7s | ", "");
			}
			System.out.println();
		}
	}

	public static boolean sorted(String[][] stacks) {
		for (int i = 0; i < stacks.length; i++) {
			String color = stacks[i][0];
			for (int j = 1; j < stacks[0].length; j++) {
				if (color != stacks[i][j])
					return false;
			}
		}
		System.out.println(" _________________Sorted!!____________________");
		return true;

	}

	public static boolean isMovable(String[][] stacks, int i, int j) {
//		System.out.println(" _________________Check Movable_" + i + "->" + j + "___________________");
//		print(stacks);
		// 위치가 같으면 불가능
		if (i == j) {
			return false;
		}
		// 대상의 색상과 개수 확인
		String icolor = null;
		String jcolor = null;
		int ilength = 0;
		int isize = 0;
		int jsize = 0;

		// 현재 배열의 가장 높은 칸의 색상과 크기를 확인하는 구간
		for (int k = stacks[0].length - 1; k >= 0; k--) {
			if (icolor == null) {
				if (stacks[i][k] != null) {
					icolor = stacks[i][k];
					isize = 1;
					ilength++;
				}
			} else {
				if (stacks[i][k] == icolor) {
					isize++;
				} else {
					break;
				}
			}
		}

		// 현재 배열 내용물의 개수를 확인하는 구간
		for (int k = stacks[0].length - 1; k >= 0; k--) {
			if (stacks[i][k] != null) {
				ilength++;
			}

			if (stacks[j][k] != null) {
				jsize++;
			}
		}

		// 대상 배열 색상과 크기 확인하는 구간
		for (int k = stacks[0].length - 1; k >= 0; k--) {
			if (jcolor == null) {
				if (stacks[j][k] != null) {
					jcolor = stacks[j][k];
				}
			}
		}
		if (icolor != null && isize == ilength && jcolor == null) {
			System.out.println(i + "의" + icolor + " " + isize);
		}

		// 대상 색깔이 null이거나, 가장 위에 색상이 동일하지 않거나, 추가했을 때 그 용량이 최대치를 넘는다면 불가능
		if (icolor == null || (jcolor != null && icolor != jcolor) || isize + jsize > stacks[0].length
				|| (icolor != null && isize == ilength && jcolor == null)) {
			return false;
		}

		return true;
	}

	public static void move(String[][] stacks, int i, int j) {
		String icolor = null;
		int isize = 0;
		int index = stacks[0].length - 1;
		int jsize = 0;

		// 현재 배열의 가장 높은 칸의 색상과 크기를 확인하는 구간
		for (int k = stacks[0].length - 1; k >= 0; k--) {
			if (icolor == null) {
				if (stacks[i][k] != null) {
					icolor = stacks[i][k];
					isize = 1;
					index = k;
				}
			} else {
				if (stacks[i][k] == icolor) {
					isize++;
				} else {
					break;
				}
			}
		}

		// 현재 배열 내용물의 개수를 확인하는 구간
		for (int k = stacks[0].length - 1; k >= 0; k--) {

			if (stacks[j][k] != null) {
				jsize++;
			}
		}

		System.out.println(i + "의" + icolor + " " + isize + "개를 " + j + "로 이동");
		for (int k = 0; k < isize; k++) {
			stacks[i][index - k] = null;
			stacks[j][k + jsize] = icolor;
		}
		print(stacks);
	}

}
