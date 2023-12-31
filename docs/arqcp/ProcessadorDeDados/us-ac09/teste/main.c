#include <string.h>  
#include "../../unity.h"
#include "fnc.h" 


void call_func(void (*f)(char *ptr_line, Sensor ***sensor_array, int *ptr_size,int *ptr_counter), char *ptr_line, Sensor ***sensor_array, int *ptr_size,int *ptr_counter);  

void setUp(void) {
    // set stuff up here
}

void tearDown(void) {
    // clean stuff up here
}

void run_test(char *ptr_line, Sensor **expected)
{
	Sensor **sensor_array = allocVetorSensores();
	Sensor ***ptr_sensor;
	ptr_sensor = &sensor_array;

	int array_size = ARRAY_SENSOR_SIZE;
	int *ptr_arr_size = &array_size;
	
	int counter = 0;
	int *ptr_counter = &counter;
	
	call_func(insert_data_line, ptr_line, ptr_sensor, ptr_arr_size, ptr_counter);
	
    TEST_ASSERT_EQUAL_INT(expected[0]->sensor_id, sensor_array[0]->sensor_id);
    TEST_ASSERT_EQUAL_INT(expected[0]->instanteTemporal, sensor_array[0]->instanteTemporal);
    TEST_ASSERT_EQUAL_INT(expected[0]->write_counter, sensor_array[0]->write_counter);
    TEST_ASSERT_EQUAL_INT(expected[0]->timeOut, sensor_array[0]->timeOut);
 	TEST_ASSERT_EQUAL_STRING(expected[0]->unit, sensor_array[0]->unit);
	TEST_ASSERT_EQUAL_STRING(expected[0]->type, sensor_array[0]->type);
	for(int i = 0; i < NUM; i++){
		TEST_ASSERT_EQUAL_INT(expected[0]->buffer->array[i], sensor_array[0]->buffer->array[i]);
	}
	freeVectorSensores(sensor_array,ARRAY_SENSOR_SIZE);
}


void test_insert_info()
{ 
	char* line = "sensor_id:5#type:atmospheric_humidity#value:43.00#unit:percentage#time:1034027";

	Sensor** sensores = allocVetorSensores();
	Sensor* sensor = allocSensor(NUM,NUM);
	sensor->sensor_id = 5;	
	sensor->write_counter = 0;
	sensor->timeOut = TIME_OUT;
	strcpy(sensor->type,"atmospheric_humidity");
	strcpy(sensor->unit,"percentage");
	
	char* token_ptr;
	token_ptr =  "time:";
	extract_token(line, token_ptr, &sensor->instanteTemporal);
	
	int value;
	int* ptr_value = &value;
	token_ptr = "value:";
	extract_token(line, token_ptr, ptr_value);
	
	enqueue_value(sensor->buffer->array , sensor->buffer->size, &(sensor->buffer->read), &(sensor->buffer->write), value);			
	
	
	sensores[0] = sensor;
	
	run_test(line,sensores);
	freeVectorSensores(sensores,ARRAY_SENSOR_SIZE);
}


int main()
  { 

    UNITY_BEGIN();
    RUN_TEST(test_insert_info);
      
    return UNITY_END();  

  } 
