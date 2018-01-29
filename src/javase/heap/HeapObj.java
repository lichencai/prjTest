package javase.heap;
/**
 * 构建最大堆和堆排序的算法实现
 */
public class HeapObj {
	private int headSize = 0;
	private int length = 0;
	private int[] heap = null;
	public int getHeadSize() {
		return headSize;
	}
	public void setHeadSize(int headSize) {
		this.headSize = headSize;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int[] getHeap() {
		return heap;
	}
	public void setHeap(int[] heap) {
		this.heap = heap;
	}
	
	@SuppressWarnings("unused")
	private int parent(int i){
		return i / 2;
	}
	
	private int left_child(int i){
		return 2 * i;
	}
	
	private int right_child(int i){
		return 2*i + 1;
	}
	
	public void max_heapify(HeapObj heap, int i){
		int l = left_child(i);
		int r = right_child(i);
		int largest = i;
		if(l <= heap.headSize && heap.heap[l] > heap.heap[i]){
			largest = l;
		}else{
			largest = i;
		}
		if(r <= heap.headSize && heap.heap[r] > heap.heap[largest]){
			largest = r;
		}
		if(largest != i){
			int temp = heap.heap[i];
			heap.heap[i] = heap.heap[largest];
			heap.heap[largest] = temp;
			max_heapify(heap, largest);
		}
	}
	
	public void build_max_heap(HeapObj heap){
		for(int i = heap.headSize / 2; i >= 1; i--){
			max_heapify(heap, i);
		}
	}
	
	public void heapSort(HeapObj heap){
		for(int i = heap.headSize; i >= 2; i--){
			int temp = heap.heap[1];
			heap.heap[1] = heap.heap[i];
			heap.heap[i] = temp;
			heap.headSize = heap.headSize - 1;
			max_heapify(heap, 1);
		}
	}
	
	public static void main(String[] args) {
		HeapObj heap = new HeapObj();
		heap.headSize = 10;
		heap.heap = new int[]{0,12,23,11,43,55,42,52,65,33,22};
		heap.build_max_heap(heap);
		System.out.println("构建最大堆");
		for(int i = 1; i <= heap.headSize; i++){
			System.out.print(heap.heap[i] + " ");
		}
		System.out.println();
		System.out.println("最大堆实现堆排序");
		heap.heapSort(heap);
		heap.headSize = 10;
		for(int i = 1; i <= heap.headSize; i++){
			System.out.print(heap.heap[i] + " ");
		}
	}
	
	
	
}
