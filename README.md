🧩 Water Sort Puzzle Algorithm

![example](https://github.com/LeeSeungHyun9661/WaterSortAlgorithm/assets/101535408/7c31888c-ae81-4cfb-8588-6572f0d5e6e7)

**Water Sort Puzzle**, also known as **Ball Sort Puzzle**, is a puzzle game where the player arranges contents of different colors mixed in multiple bottles into groups of the same color. The rules are as follows:

> 1. The objective of the game is to have all bottles contain the same color of contents.
> 2. You can only move contents from the top of one bottle to another if they are of the same color and the receiving bottle is empty.
> 3. When moving contents, all contents of the same color from the top of the source bottle must move together.
> 4. No bottle can contain more contents than its capacity allows.

While the rules are simple, as the number of bottles and the volume of contents increase, the complexity of solving the puzzle also increases due to the many possible scenarios. The biggest challenge is encountering situations where no more moves are possible (leading to game over unless reset or undo options are available).

The algorithm to solve this problem uses recursive functions, and the sequence is as follows:

### Sorting Function

- Save the **original** state of the current step's bottles array.
- Iterate through all pairs of bottles (**i**, **j**):
  - Check if contents from **bottle-i** can be moved to **bottle-j**.
    - Record the current state in **history**.
    - Move contents from **bottle-i** to **bottle-j**.
    - Check if the current state of bottles is already in **history** (to avoid infinite loops).
    - Check if bottles are **sorted**. If sorted, exit the function.
      - If not sorted, call the **sorting function** recursively.
      - Depending on the result of the **sorting function** (i.e., success or failure to sort), either exit or revert the state to the **original** (indicating inability to sort).


Based on this, the code has been written using two classes, as follows:

```java
// The Bottle class corresponds to each bottle.
public static boolean sort(ArrayList<String> history, Bottle[] bottles, ArrayList<String> Answer) {
    // The sorting function receives the list of bottles, history of actions, and the answer record as parameters. It also returns a boolean value.

    Bottle[] origin = copy(bottles);
    // Save the current state of the bottles array as 'origin' to enable rollback.

    for (int i = 0; i < bottles.length; i++) {
        for (int j = 0; j < bottles.length; j++) {
            // Iterate through all possible combinations of bottles.
            if (i != j && bottles[i].checkInsert(bottles[j])) {
                // Check if it's possible to move contents from bottle-i to bottle-j.
                history.add(toString(bottles));
                // Record the state of the bottles.
                bottles[i].insert(bottles[j]);
                // Move contents from bottle-i to bottle-j.

                if (history.contains(toString(bottles))) {
                    // If the current state is already recorded in history, prevent infinite loops by returning false.
                    return false;
                }

                Answer.add(i + "->" + j);
                // Record the move in the answer log.

                if (isSorted(bottles)) {
                    // Check if the bottles are sorted after the move.
                    return true;
                    // Return true if sorted.
                } else { 
                    if (sort(history, bottles, Answer)) {
                        // If not sorted, recursively call the sorting function.
                        return true;
                        // Return true indicating successful sorting.
                    } else {
                        bottles = copy(origin);
                        // Return false indicating failure due to scenarios like infinite loops or invalid moves. Revert to the original state.
                        history.remove(history.size() - 1);
                        Answer.remove(Answer.size() - 1);
                        // Remove entries from history and answer logs.
                    }
                }
            }
        }
    }
    return false;
    // Return false indicating no further moves are possible.
}
```
Based on this, the code has been written using two classes, as follows:

### Bottle.java
- **Variables**: String[] bottle: Stores the contents of the bottle as a string array.
- **Constructor**: Bottle(String... waters): Stores the contents of the bottle and determines its length.
- **Methods**: 
  - void print(): Prints the contents of the bottle.
  - String toString(): Prints the contents of the bottle as a single line of string (for easy storage in the execution history).
  - boolean checkInsert(Bottle target): Checks if contents from this bottle (this) can be moved to the target (target).
  - void insert(Bottle target): Moves contents from this bottle (this) to the target (target).
  - void sorted(): Checks if the bottle is in a sorted state.

### Main.java
- **Methods**: 
  - void main(String[] args): Main execution function.
  - String toString(Bottle[] bottles): Converts the array of bottles into a single long array (for easy storage in the execution history).
  - boolean sort(ArrayList<String> history, Bottle[] bottles, ArrayList<String> Answer): Sorting function that takes bottles array (bottles), action history (history), and answer log (Answer) as parameters.
  - void print(Bottle[] bottles): Prints the array of bottles.
  - 
## While the content may seem complex, it aims to solve the Water Sort Puzzle effectively!
