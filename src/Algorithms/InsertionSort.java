package Algorithms;

public class InsertionSort implements ISortingAlgorithm {

    private long stepDelay = 2;
    @Override
    public void runSort(BaseSort array) {
        int len = array.getArraySize();
        for(int i = 1; i < len; i++){
            int key = array.getValue(i);
            int j = i - 1;

            while (j >= 0 && array.getValue(j) > key){
                array.swap(j+1, j, stepDelay,true);
                j--;
            }
            array.updateSingle(j+1, key, stepDelay, true);
        }
    }

    @Override
    public long getDelay() {
        return stepDelay;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void setDelay(long delay) {
        this.stepDelay = delay;
    }
}
