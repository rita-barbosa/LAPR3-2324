processadordedados: main.o function.o
		gcc main.o function.o -z noexecstack -o processadordedados
		
main.o: main.c
		gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o
		
function.o: function.c
		gcc -g -Wall -Wextra -fanalyzer -c function.c -o function.o
			
run: processadordedados
		./processadordedados /etc/ttyS0 config intermedio 50
		
clean:
		rm  -f *.o processadordedados
