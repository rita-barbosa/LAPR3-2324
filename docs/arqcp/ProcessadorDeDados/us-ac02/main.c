#include <stdio.h>
#include "asm.h"

int array1[10]={0,0,2,3,6,0,5,9,0,0};
int array2[10]={1,2,3,4,5,6,7,8,9,10};
int array3[10]={0,0,0,0,0,0,0,0,0,0};



int main(void){
	int pos1=8,pos2=5,pos3=0,pos4=2,pos5=5,pos6=9;

	int* read1 =  &pos1;
	int* read2 =  &pos2;
	int* read3 =  &pos3;
	
	int* write1 =  &pos4;
	int* write2 =  &pos5;
	int* write3 =  &pos6;
	
	int* ptr1 = array1;
	int* ptr2 = array2;
	int* ptr3 = array3;
	
	int length = 10;
	
	enqueue_value(ptr1, length, read1, write1, 22);
	enqueue_value(ptr2, length, read2, write2, 22);
	enqueue_value(ptr3, length, read3, write3, 22);
	
	enqueue_value(ptr1, length, read1, write1, 23);
	enqueue_value(ptr2, length, read2, write2, 23);
	enqueue_value(ptr3, length, read3, write3, 23);
	
	enqueue_value(ptr1, length, read1, write1, 24);
	enqueue_value(ptr2, length, read2, write2, 24);
	enqueue_value(ptr3, length, read3, write3, 24);
	
	
	
	
	printf("Array1:\n");
	for(int i = 0; i < length; i++){
		printf("%d ", array1[i]);
	}
	printf("\n");
	printf("Read: %d | Write: %d\n", *read1, *write1);
	
	printf("Array2:\n");
	for(int i = 0; i < length; i++){
		printf("%d ", array2[i]);
	}
	printf("\n");
	printf("Read: %d | Write: %d\n", *read2, *write2);
	
	printf("Array3:\n");
	for(int i = 0; i < length; i++){
		printf("%d ", array3[i]);
	}
	printf("\n");
	printf("Read: %d | Write: %d\n", *read3, *write3);
   
   return 0;
}

