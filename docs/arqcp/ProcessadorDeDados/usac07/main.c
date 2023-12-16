#include <stdio.h>
#include "function.h"


int main(int argc,char *argv[]) {
	printf("**Função para a alocação de dados e configuração da componente do Processador de Dados**\n\n");	
	
	char a[20];
	char b[20];
	char c[20];
	int d = -1;
	
	//ver onde usar o int, porque ele tem de estar na função mas não o estou a usar, então dá erro!!
	
	sscanf(argv[1], "%s", a);
	sscanf(argv[2], "%s", b);
	sscanf(argv[3], "%s", c);
	sscanf(argv[4], "%d", &d);
	
	printf("Separação dos conteúdos passados pelo main:\n");
	for(int i = 1; i < argc; i++){
		printf("\nargv[%d]: %s", i, argv[i]);
	}
	printf("\n");
	
	/*
	printf("a: %s\n", a);
	printf("b: %s\n", b);
	printf("c: %s\n", c);
	printf("valor do short d: %d\n\n", d);
	*/
	
	alocateProcessadorDeDados(c);
	
	
	
	// Teste de alocação e liberação para um Sensor
    Sensor *sensor1 = allocSensor(10, 5);
if (sensor1 != NULL) {
    printf("Sensor alocado com sucesso!\n");

    Sensor **vetorSensores = allocVetorSensores();
    if (vetorSensores != NULL) {
        printf("Vetor de Sensores alocado com sucesso!\n");

        // Adiciona o sensor1 ao vetor de sensores na posição 0
        vetorSensores[0] = sensor1;

        // Agora você pode trabalhar com o vetor de sensores com um sensor alocado
        // Lembre-se de liberar a memória apropriada posteriormente
        freeVectorSensores(vetorSensores, 1);
    } else {
        printf("Falha na alocação do vetor de Sensores!\n");
    }
} else {
    printf("Falha na alocação do Sensor!\n");
}

    
	
	return 0;
}
