public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int currSize;
//
    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index) {
        if (index < currSize) {
            return arr[index];
        }
        return null;
    }

    @Override
    public Integer search(int x) {
        int low = 0;
        int high = currSize - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == x)
                return mid;
            else if (x > arr[mid])
                low = mid + 1;
            else
                high = mid - 1;

        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        if(currSize<arr.length) {
            int i;
            for (i = currSize - 1; (i >= 0 && arr[i] > x); i--) {
                arr[i + 1] = arr[i];
            }
            arr[i + 1] = x;
            Integer index = i + 1;
            stack.push(index);
            stack.push("i");
            currSize++;
        }
    }


    @Override
    public void delete(Integer index) {
        if (index < currSize) {
            Integer toKeep = arr[index];
            for (int i = index; i < currSize-1; i++) {
                arr[i]= arr[i+1];
            }
            stack.push(toKeep);
            stack.push("d");
            currSize--;
        }
    }

    @Override
    public Integer minimum() {
        if (currSize==0){
            return -1;
        }
        return 0;
    }

    @Override
    public Integer maximum() {
        return currSize - 1;
    }

    @Override
    public Integer successor(Integer index) {
        if (index < currSize - 1) {
            if(arr[index + 1]>arr[index]){
                return index + 1;
            }
        }
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index < currSize && index > 0) {
            if(arr[index - 1]<arr[index]) {
                return index - 1;
            }
        }
        return -1;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            String action = (String) stack.pop();
            if (action.equals("i")) {
                delete((Integer) stack.pop());
                stack.pop();
                stack.pop();
            } else {
                Integer value = (Integer) stack.pop();
                insert(value);
                stack.pop();
                stack.pop();
            }
            System.out.println("backtracking performed");
        }
    }

    @Override
    public void retrack
            () {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        int i = 0;
        while (i < currSize) {
            System.out.print(arr[i]);
            System.out.print(" ");
            i++;
        }
    }


}
