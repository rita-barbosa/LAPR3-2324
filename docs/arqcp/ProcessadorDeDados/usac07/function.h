#ifndef FUNCTION_H
#define FUNCTION_H

#define NUM 10 //usar para o buffer e para o array
#define VECTOR_SIZE 20
#define TYPE_SIZE 23
#define UNIT_SIZE 10

// Definição da estrutura para o Buffer Circular
typedef struct {
    int *array;     
    int *read;      
    int *write;      
    int size;      
} BufferCircular;
	//VAI TER TAMANHO 32 BYTES PORQUE K=8 (APONTADOR) (tem um GAP de 4 bytes no final)

typedef struct {
	BufferCircular *buffer;
	int *arrayMedias;
	int instanteTemporal;
	short timeOut;
    short write_counter;
    char sensor_id;
    char type[TYPE_SIZE];
    char unit[UNIT_SIZE];
} Sensor;		//o tamanho será 69 bytes, que é multiplo de k=23 (tem um GAP de 11 bytes no final)


void alocateProcessadorDeDados(char input[]);

BufferCircular *allocBufferCircular();
BufferCircular *reallocBufferCircular(BufferCircular *buffer, int newSize);
void freeBufferCircular(BufferCircular *buffer);
int *allocArrayMedias();
void *reallocArrayMedias(int *arrayMedias, int newSize);
void freeArrayMedias(int *arrayMedias);
Sensor *allocSensor(int bufferSize, int arrayMediasSize);
Sensor **allocVetorSensores();
void reallocVectorSensores(Sensor ***vetorSensores, int newSize);
void freeVectorSensores(Sensor **vetorSensores, int size) ;

#endif
