public class Bottle {
	String[] bottle; // 내용물을 저장하는 배열

	public Bottle() {
		this.bottle = new String[3];
	}

	public Bottle(int size) {
		this.bottle = new String[size];
	}

	public Bottle(int size, String... waters) {
		if (waters.length > size) {
			// 예외처리 구현 필요 : 병의 크기보다 입력 내용이 더 많음
		}
		this.bottle = new String[size];
		for (int i = 0; i < waters.length; i++) {
			bottle[i] = waters[i];
		}
	}

	public Bottle(String... waters) {
		this.bottle = new String[waters.length];
		for (int i = 0; i < waters.length; i++) {
			bottle[i] = waters[i];
		}
	}

	public void print() {
		for (int i = 0; i < bottle.length; i++) {
			String color = bottle[i];
			if (color == null)
				color = "";
			System.out.printf("|%7s", color);
		}
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < bottle.length; i++) {
			if (bottle[i] == null)
				str += "Empty";
			else
				str += bottle[i];
		}
		return str;

	}

	public boolean checkInsert(Bottle target) {

		// 목표 병의 상태
		String targetColor = null; // 목표 병의 색상
		int targetSize = 0; // 목표 병의 총량

		for (int i = 0; i < target.bottle.length; i++) {
			if (target.bottle[i] == null) {
				break;
			}
			if (targetColor == null) {
				targetColor = target.bottle[i];
			} else {
				if (targetColor == target.bottle[i]) {
				} else {
					targetColor = target.bottle[i];
				}

			}
			targetSize++;
		}

		// 대상 병의 상태
		String color = null; // 대상의 색상
		int cnt = 0; // 대상의 색상 개수
		int amount = 0; // 대상의 총량

		for (int i = 0; i < this.bottle.length; i++) {
			if (this.bottle[i] == null) {
				break;
			}
			if (color == null) {
				color = this.bottle[i];
				cnt = 1;
			} else {
				if (color == this.bottle[i]) {
					cnt++;
				} else {
					color = this.bottle[i];
					cnt = 1;
				}
			}
			amount++;
		}
		if (color == null || (targetColor != null && color != targetColor) || targetSize + cnt > this.bottle.length
				|| (targetColor == null && cnt == amount && color != null && targetSize == 0)) {

			return false;
		}
		return true;
	}

	public void insert(Bottle target) {
		String color = null;
		int cnt = 0;

		for (int i = target.bottle.length - 1; i >= 0; i--) {
			if (target.bottle[i] == null) {
				continue;
			} else {
				if (color == null) {
					color = target.bottle[i];
					target.bottle[i] = null;
					cnt++;
				} else {
					if (color == target.bottle[i]) {
						target.bottle[i] = null;
						cnt++;
					} else {
						break;
					}
				}
			}
		}

		int i = this.bottle.length - 1;

		for (; i >= 0; i--) {
			if (this.bottle[i] != null)
				break;
		}
		for (int j = 1; j < cnt + 1; j++) {
			this.bottle[i + j] = color;
		}

	}

	public boolean sorted() {
		String peekColor = null; // 목표 병의 색상

		for (int i = 0; i < this.bottle.length; i++) {
			if (peekColor == null) {
				peekColor = this.bottle[i];
			} else {
				if (peekColor != this.bottle[i]) {
					return false;
				}
			}
		}
		return true;
	}

}
