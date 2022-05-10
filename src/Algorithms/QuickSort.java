package Algorithms;

public class QuickSort implements ISortingAlgorithm{
    private long stepDelay = 10;
    @Override
    public void runSort(BaseSort array) {
        quickSort(array, array.getArraySize() - 1, 0);
    }

    private void quickSort(BaseSort array, int highIndex, int lowIndex){
        if(lowIndex < highIndex){
            int pivotPoint = findPivot(array, highIndex, lowIndex);
            quickSort(array, pivotPoint - 1, lowIndex);
            quickSort(array, highIndex, pivotPoint + 1);
        }
    }

    private int findPivot(BaseSort array, int highIndex, int lowIndex){
        int pivotPoint = array.getValue(highIndex);
        int i = lowIndex - 1;

        for(int j = lowIndex; j < highIndex; j++){
            if(array.getValue(j) <= pivotPoint){
                i++;
                array.swap(i, j, getDelay(), true);
            }
        }
        array.swap(i+1, highIndex, getDelay(), true);
        return pivotPoint;
    }

    @Override
    public long getDelay() {
        return stepDelay;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public void setDelay(long delay) {
        this.stepDelay = delay;
    }
}
