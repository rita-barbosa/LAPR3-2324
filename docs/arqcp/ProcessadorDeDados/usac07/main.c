#include <stdio.h>
#include "functions.h"


int main(int argc,char *argv[]) {
	printf("**Função para a alocação de dados e configuração da componente do Processador de Dados**\n\n");	
	
	char a[20];
	char b[20];
	char c[20];
	int d = -1;
	
	sscanf(argv[1], "%s", a);
	sscanf(argv[2], "%s", b);
	sscanf(argv[3], "%s", c);
	sscanf(argv[4], "%d", &d);
	
	printf("Separação dos conteúdos passados pelo main:\n");
	for(int i = 1; i < argc; i++){
		printf("\nargv[%d]: %s", i, argv[i]);
	}
	printf("\n");
	
	alocateProcessadorDeDados(c);
	
//-------------TESTAR A ALOCAÇÃO DE UM SENSOR-------------
    Sensor *sensor1 = allocSensor(10, 5);
	if (sensor1 != NULL) {
	printf("Sensor alocado com sucesso!\n");

		Sensor **vetorSensores = allocVetorSensores();
		if (vetorSensores != NULL) {
			printf("Vetor de Sensores alocado com sucesso!\n");

			vetorSensores[0] = sensor1;

			freeVectorSensores(vetorSensores, 1);
		} else {
			printf("Falha na alocação do vetor de Sensores!\n");
		}
	} else {
		printf("Falha na alocação do Sensor!\n");
	}

    
	
	return 0;
}
