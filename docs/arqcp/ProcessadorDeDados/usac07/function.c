#include "function.h"
#include <string.h>
#include <stdio.h>
#include <sys/stat.h>
#include <dirent.h>
#include <errno.h>
#include <stdlib.h>

//#DEFINE NUM 10
//int v[NUM]

void alocateProcessadorDeDados(char c[]){	
	DIR* dir = opendir(c);
	
	if(dir){
		printf("O diretório já existe existe!!\n");
		closedir(dir);
	} else if (ENOENT == errno){
		printf("O diretório não existe!\nA proceder à criação do diretório!\n");
		
		if (mkdir(c, 0700) == 0) {
            printf("Diretório criado com sucesso!\n");
        } else {
            printf("Erro ao criar o diretório.\n");
        }
	} else{
		printf("Ocorreu um erro na tentativa de abrir o diretório desejado.\n");
	}
}


//------------------------------BUFFER CIRCULAR-----------------------------------

BufferCircular *allocBufferCircular() {
    BufferCircular *buffer = (BufferCircular *)malloc(sizeof(BufferCircular));

    if (buffer == NULL) {
        return NULL;
    }

    buffer->array = (int *)malloc(NUM * sizeof(int));
    buffer->read = (int *)malloc(sizeof(int));
    buffer->write = (int *)malloc(sizeof(int));
    buffer->size = NUM;

    if (buffer->array == NULL || buffer->read == NULL || buffer->write == NULL) {
        free(buffer->array);
        free(buffer->read);
        free(buffer->write);
        free(buffer);
        return NULL;
    }

	//Inicializar os apontadores para o read e para o write
	for (int i = 0; i < NUM; i++) {
        buffer->array[i] = 0;
    }
    *(buffer->read) = 0;
    *(buffer->write) = 0;

    return buffer;
}


BufferCircular *reallocBufferCircular(BufferCircular *buffer, int newSize){
	int *newArray = (int *)realloc(buffer->array, newSize * sizeof(int));
    if (newArray == NULL) {
        return NULL;
    }

    buffer->array = newArray;
    buffer->size = newSize;

    return buffer;
}


void freeBufferCircular(BufferCircular *buffer) {
    if (buffer != NULL) {
        free(buffer->array);
        free(buffer->read);
        free(buffer->write);
        free(buffer);
    }
}


//------------------------------ARRAY MEDIAS-----------------------------------

int *allocArrayMedias(){
	int *array = (int *) malloc(NUM * sizeof(int *));
	
	if (array==NULL){
		 return NULL;
	 }
	 
	 for (int i = 0; i < NUM; i++) {
        array[i] = 0;
    }
	 
	 return array;
}

void *reallocArrayMedias(int *arrayMedias, int newSize){
	int *newArray = (int *)realloc(arrayMedias, newSize * sizeof(int));
	
	if(newArray != NULL){
		arrayMedias = newArray;
		newArray = NULL;
	} else {
		exit (-1);
	}
	
	return arrayMedias;
}

void freeArrayMedias(int *arrayMedias) {
    if (arrayMedias != NULL) {
        free(arrayMedias);
    }
}


//------------------------------SENSOR-----------------------------------


Sensor *allocSensor(int bufferSize, int arrayMediasSize) {
    Sensor *sensor = (Sensor *)malloc(sizeof(Sensor));

    if (sensor == NULL) {
        return NULL;
    }
    
    sensor->buffer = allocBufferCircular(bufferSize);
   

    sensor->arrayMedias = allocArrayMedias(arrayMediasSize);
    if (sensor->arrayMedias == NULL || sensor->buffer == NULL || sensor->buffer->array == NULL) {
		freeBufferCircular(sensor->buffer);
        freeArrayMedias(sensor->arrayMedias);
        free(sensor);
        return NULL;
    }
    
    sensor->instanteTemporal = 0;
	sensor->timeOut = 0;
	sensor->write_counter = 0;
	sensor->sensor_id = 0;
	strcpy(sensor->type, "");
    strcpy(sensor->unit, ""); 

    return sensor;
}

//NÃO ESTÁ PRESENTE UM MÉTODO ESPECÍFICO PARA FAZER O FREE DO SENSOR, PORQUE 

//------------------------------VECTOR SENSORES-----------------------------------

Sensor **allocVetorSensores(){
	Sensor **vetorSensores = (Sensor **)malloc(VECTOR_SIZE * sizeof(Sensor *));
	if (vetorSensores == NULL) {
		exit(-1);
	}
	
	 for (int i = 0; i < VECTOR_SIZE; ++i) {
            vetorSensores[i] = NULL;
        }
	
	return vetorSensores;
}


void reallocVectorSensores(Sensor ***vetorSensores, int newSize) {
    Sensor **novoVetor = (Sensor **)realloc(*vetorSensores, newSize * sizeof(Sensor *));
    if (novoVetor != NULL) {
		*vetorSensores = novoVetor;
        *novoVetor = NULL;
    } else {
        exit(-1);
    }
}

void freeVectorSensores(Sensor **vetorSensores, int size) {
	for (int i = 0; i < size; i++) {
		if (vetorSensores[i] != NULL) {
			freeBufferCircular(vetorSensores[i]->buffer);
			freeArrayMedias(vetorSensores[i]->arrayMedias);
			free(vetorSensores[i]);
		}
	}
	free(vetorSensores);
}



