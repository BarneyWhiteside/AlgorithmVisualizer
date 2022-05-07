package Algorithms;

public class BubbleSort implements ISortingAlgorithm {

    private long stepDelay = 2;

    public void runSort(BaseSort array){
        int len = array.getArraySize() - 1;
        for(int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                if (array.getValue(j) > array.getValue(j + 1)){
                    array.swap(j, j+1, stepDelay, true);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public long getDelay() {
        return stepDelay;
    }

    @Override
    public void setDelay(long delay) {
        this.stepDelay = delay;
    }
}
