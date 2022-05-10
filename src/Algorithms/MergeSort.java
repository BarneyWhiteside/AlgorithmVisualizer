package Algorithms;

public class MergeSort implements ISortingAlgorithm {
    private long stepDelay = 10;
    @Override
    public void runSort(BaseSort array) {
        mergeSort(array, 0, array.getArraySize() - 1);
    }

    private void mergeSort(BaseSort array, int left, int right){
        if (left < right){
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid+1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(BaseSort array, int left, int mid, int right){
        int len1 = mid - left + 1;
        int len2 = right - mid;

        int[] leftArray = getSubArray(array, len1, left);
        int[] rightArray = getSubArray(array, len2, mid + 1);

        int i, j, k;
        i = 0;
        j = 0;
        k = left;

        while(i < len1 && j < len2){
            if(leftArray[i] <= rightArray[j]){
                array.updateSingle(k, leftArray[i], getDelay(), true);
                i++;
            } else {
                array.updateSingle(k, rightArray[j], getDelay(), true);
                j++;
            }
            k++;
        }

        while (i < len1){
            array.updateSingle(k, leftArray[i], getDelay(), true);
            i++;
            k++;
        }

        while(j < len2){
            array.updateSingle(k, rightArray[j], getDelay(), true);
            j++;
            k++;
        }
    }

    private int[] getSubArray(BaseSort array, int len, int pivot){
        int[] subArray = new int[len];

        for(int i = 0; i < len; i++){
            subArray[i] = array.getValue(i + pivot);
        }

        return subArray;
    }

    @Override
    public long getDelay() {
        return stepDelay;
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void setDelay(long delay) {
        this.stepDelay = delay;
    }
}
