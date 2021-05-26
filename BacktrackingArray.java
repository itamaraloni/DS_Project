

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int currSize = 0;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index){
        if (index < currSize){
            return arr[index];
        }
        return null;
    }

    @Override
    public Integer search(int x) {
        Integer index = 0;
        while(index < currSize){
            if (arr[index] == x){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        if (currSize < arr.length){
            arr[currSize] = x;
            currSize++;
            stack.push("i");
        }
    }

    @Override
    public void delete(Integer index) {
        if (index < currSize) {
            Integer toKeep = arr[index];
            if (index + 1 == currSize)
                arr[index] = 0;
            else
                arr[index] = arr[currSize - 1];
            currSize--;

            stack.push(index);
            stack.push(toKeep);
            stack.push("d");
        }
    }

    @Override
    public Integer minimum() {
        Integer min = null;
        int index = 0;
        int ans = -1;
        while (index < currSize){
            if (min == null || arr[index] <= min){
                min = arr[index];
                ans = index;
            }
            index++;
        }
        return ans;
    }

    @Override
    public Integer maximum() {
        Integer max = null;
        int index = 0;
        int ans = -1;
        while (index < currSize){
            if (max == null || arr[index] >= max){
                max = arr[index];
                ans = index;
            }
            index++;
        }
        return ans;
    }

    @Override
    public Integer successor(Integer index) {
        if(index<currSize) {
            int i = arr[index];
            Integer curr = null;
            Integer ans = -1;
            for (int k = 0; k < currSize; k++) {
                int temp = arr[k];
                if (temp > i && (curr == null || temp < curr)) {
                    curr = temp;
                    ans = k;
                }
            }
            return ans;
        }
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if(index<currSize) {

            int i = arr[index];
            Integer curr = null;
            Integer ans = -1;
            for (int j = 0; j < currSize; j++) {
                int temp = arr[j];
                if (temp < i && (curr == null || temp > curr)) {
                    curr = temp;
                    ans = j;
                }
            }
            return ans;
        }
        return -1;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()){
            String action = (String)stack.pop();
            if (action.equals("i")){
                delete(currSize - 1);
                stack.pop(); stack.pop(); stack.pop();
                }
            else {
                Integer value = (Integer)stack.pop();
                Integer index = (Integer)stack.pop();
                insert(arr[index]);
                stack.pop();
                arr[index] = value;
            }
            System.out.println("backtracking performed");
        }

    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        int i = 0;
        while(i < currSize){
            System.out.print(arr[i]);
            System.out.print(" ");
            i++;
        }
    }

}



