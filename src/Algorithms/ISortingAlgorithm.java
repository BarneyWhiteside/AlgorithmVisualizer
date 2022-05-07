package Algorithms;

/*
Base interface for sorting algorithms
 */

public interface ISortingAlgorithm {
    void runSort(BaseSort array);
    long getDelay();
    String getName();
    void setDelay(long delay);
}
