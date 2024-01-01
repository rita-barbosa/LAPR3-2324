#include "functions.h"
#include <string.h>
#include <stdio.h>
#include <sys/stat.h>
#include <dirent.h>
#include <errno.h>
#include <stdlib.h>


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

    buffer->array = (int *)calloc(NUM, sizeof(int));
    buffer->read = BUFFER_VARIABLES;
    buffer->write = BUFFER_VARIABLES;
    buffer->size = NUM;

    if (buffer->array == NULL) {
        free(buffer->array);
        free(buffer);
        return NULL;
    }

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
        free(buffer);
    }
}


//------------------------------ARRAY MEDIAS-----------------------------------

int *allocArrayMedias(){
	int *array = (int *) calloc(NUM, sizeof(int *));
	
	if (array==NULL){
		 return NULL;
	 }
	 
	 return array;
}

int *reallocArrayMedias(int *arrayMedias, int newSize){
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
	
    return sensor;
}

void freeSensor(Sensor *sensor) {
    if (sensor != NULL) {
        freeBufferCircular(sensor->buffer);
        freeArrayMedias(sensor->arrayMedias);
        free(sensor);
    }
}


//------------------------------VECTOR SENSORES-----------------------------------

Sensor **allocVetorSensores(){
	Sensor **vetorSensores = (Sensor **)calloc(VECTOR_SIZE, sizeof(Sensor *));
	if (vetorSensores == NULL) {
		exit(-1);
	}

	return vetorSensores;
}


Sensor **reallocVectorSensores(Sensor ***vetorSensores, int newSize, int size) {
    Sensor **novoVetor = (Sensor **)realloc(*vetorSensores, newSize * sizeof(Sensor *));
    if (novoVetor != NULL) {
		*vetorSensores = novoVetor;
    } else {
        exit(-1);
    } 
    
     if (newSize > size) {
		for (int i = size; i < newSize; i++) {
			(*vetorSensores)[i] = 0;
		}
	}
        
    return *vetorSensores;
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



