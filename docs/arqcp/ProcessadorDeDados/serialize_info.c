#include "functions.h"
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

/*
* Avalia se o sensor em questão está numa situação de erro ou não
*/
int isSensorInError(Sensor *sensorPtr, int offset_time){
	//Tempo (em clock ticks) do instante do tempo de execucao do programa
	clock_t instantClock = clock();
	//conversao para milisegundos
	int elapsedMilliseconds = (int) ((((long double) instantClock / CLOCKS_PER_SEC) * 1000.0) * 1000000);
	
	elapsedMilliseconds -= offset_time;
	
	//Se a diferença entre o instante da ultima leitura do sensor e o instante atual for superior ao timeout
	//entao retorna 1, caso contrario 0
	 if((sensorPtr->instanteTemporal - elapsedMilliseconds) > (int) sensorPtr->timeOut){
		 return 1;
	 }
	return 0;
}


/*
 * Serializa a informação contida num sensor com a adição da mediana para um ficheiro
*/
void serialize_info(Sensor *sensorPtr, int mediana, FILE *outputFile, int offset_time){
	char *line = malloc(50); // Alocar memória para a nova linha a escrever no ficheiro

    if (line == NULL) {
        //Caso não seja possivel alocar memória - informar
        printf("Não foi possível alocar memória para a nova linha do ficheiro.\n");
        return;
    }
    
    //---> Detetar possível situação de erro
    //Obtenção do instante atual (timestamp)
    int isError = isSensorInError(sensorPtr, offset_time);
    
    //sensor_id,write_counter,type,unit,mediana#
    if(isError == 1){
		sprintf(line, "%d,%hd,%s,%s,error#", sensorPtr->sensor_id, sensorPtr->write_counter, sensorPtr->type, sensorPtr->unit);	
	}else{
		sprintf(line, "%d,%hd,%s,%s,%d#", sensorPtr->sensor_id, sensorPtr->write_counter, sensorPtr->type, sensorPtr->unit, mediana);
	}
	
	//Escrever no ficheiro output a linha construida
	fprintf(outputFile,"%s\n", line);
	
	//Libertar os recursos da memória, ocupados pela linha
	free(line);
}
